package dev.aulait.jeg.core.domain.jpa;

import dev.aulait.jeg.core.domain.jdbc.ColumnModel;
import dev.aulait.jeg.core.domain.jdbc.TableModel;
import dev.aulait.jeg.core.infra.config.Config;
import dev.aulait.jeg.core.infra.util.ModelUtils;
import dev.aulait.jeg.core.infra.util.WordUtils;
import java.nio.file.Path;
import java.sql.DatabaseMetaData;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ClassUtils;
import org.apache.commons.lang3.StringUtils;

@Slf4j
@RequiredArgsConstructor
public class TableToEntityProcessor {

  private final Config config;

  public List<EntityModel> process(List<TableModel> tables) {
    List<EntityModel> entities =
        tables.stream()
            .filter(Predicate.not(TableModel::isRelationTable))
            .map(this::table2entity)
            .toList();

    log.info("Processed {} tables to {} entities", tables.size(), entities.size());

    return entities;
  }

  EntityModel table2entity(TableModel table) {
    EntityModel entity = new EntityModel();

    entity.setTable(table);
    entity.setTableName(table.getTABLE_NAME());
    entity.setName(WordUtils.snakeToUpperCamel(table.getTABLE_NAME()) + config.getEntitySuffix());

    Optional<String> baseClass = config.findBaseClass(table.getTABLE_NAME());
    if (baseClass.isPresent()) {
      entity.setBaseClass(baseClass.get());
    }

    Optional<String> pkgOpt = config.findPackage(table.getTABLE_NAME());
    if (pkgOpt.isPresent()) {
      entity.setPkg(pkgOpt.get());
    } else {
      log.warn("Define package of {}", table.getTABLE_NAME());
    }

    String fileName = entity.getName() + ".java";
    String pkgDir = entity.getPkg() == null ? "" : entity.getPkg().replace(".", "/");
    Path filePath = Path.of(config.getRuntime().getOutputDir(), pkgDir, fileName).toAbsolutePath();
    entity.setFilePath(filePath);

    processColumns(table.getColumns(), entity);

    return entity;
  }

  void processColumns(List<ColumnModel> columus, EntityModel entity) {
    List<FieldModel> embeddedIdFields = new ArrayList<>();

    for (ColumnModel column : columus) {
      FieldModel field = column2field(column);

      if (field == null) {
        continue;
      }

      if (field.isEmbeddedId()) {
        embeddedIdFields.add(field);
      } else {
        entity.getFields().add(field);
      }
    }

    setEmbeddedId(entity, embeddedIdFields);
  }

  FieldModel column2field(ColumnModel column) {
    if (config.getExcludedColmuns().contains(column.getCOLUMN_NAME())) {
      return null;
    }

    FieldModel field = new FieldModel();

    if (column.isPk()) {
      if (column.getTable().isCompositeKey()) {
        field.setEmbeddedId(column.isPk());
      } else {
        field.setId(column.isPk());
      }
    }
    field.setName(WordUtils.snakeToLowerCamel(column.getCOLUMN_NAME()));
    field.setColumn(column);

    String type = dbtype2javatype(column.getDATA_TYPE(), column.getTYPE_NAME());
    if (column.getNULLABLE() == DatabaseMetaData.columnNoNulls) {
      type = wrap2primitive(type);
    }
    field.setType(type);

    List<AnnotationModel> annotations =
        config.findAnnotations(column.getTABLE_NAME(), column.getCOLUMN_NAME()).stream()
            .map(ad -> ModelUtils.map(ad, AnnotationModel.class))
            .toList();
    field.setAnnotations(annotations);

    return field;
  }

  void setEmbeddedId(EntityModel entity, List<FieldModel> embeddedIdFields) {
    if (embeddedIdFields.isEmpty()) {
      return;
    }

    EmbeddedIdModel embeddedId = new EmbeddedIdModel();

    embeddedId.setName(entity.getName() + "Id");
    embeddedId.setFields(embeddedIdFields);
    embeddedId.setPkg(entity.getPkg());
    embeddedId.setFilePath(
        entity.getFilePath().getParent().resolve(embeddedId.getName() + ".java"));

    entity.setEmbeddedId(embeddedId);
  }

  String dbtype2javatype(int dataType, String typeName) {

    if (StringUtils.equalsAnyIgnoreCase(typeName, "uuid", "uniqueidentifier")) {
      return "java.util.UUID";
    }

    switch (dataType) {
      // boolean
      case Types.BOOLEAN, Types.BIT:
        return "Boolean";

      // numeric
      case Types.INTEGER, Types.TINYINT, Types.SMALLINT:
        return "Integer";

      case Types.BIGINT:
        return "Long";

      case Types.REAL:
        return "Float";

      case Types.FLOAT, Types.DOUBLE:
        return "Double";

      case Types.DECIMAL, Types.NUMERIC:
        return "java.math.BigDecimal";

      // date
      case Types.DATE:
        return "java.time.LocalDate";
      case Types.TIME:
        return "java.time.LocalTime";
      case Types.TIMESTAMP:
        return "java.time.LocalDateTime";
      case Types.TIME_WITH_TIMEZONE:
        return "java.time.OffsetTime";
      case Types.TIMESTAMP_WITH_TIMEZONE:
        return "java.time.OffsetDateTime";

      // binary
      case Types.BLOB, Types.BINARY, Types.VARBINARY, Types.LONGVARBINARY:
        return "byte[]";

      // string
      default:
        return "String";
    }
  }

  String wrap2primitive(String className) {
    try {
      Class<?> clazz = Class.forName("java.lang." + className);

      if (ClassUtils.isPrimitiveWrapper(clazz)) {
        return ClassUtils.wrapperToPrimitive(clazz).getName();
      }
    } catch (ClassNotFoundException e) {
      // ignore
    }
    return className;
  }
}
