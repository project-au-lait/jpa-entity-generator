package dev.aulait.jeg.core.domain.jpa;

import dev.aulait.jeg.core.domain.jdbc.ColumnModel;
import java.sql.DatabaseMetaData;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.StringUtils;

public class EntityMetadataProcessor {

  public List<EntityMetadataModel> process(List<EntityModel> entities) {
    return entities.stream().map(this::process).toList();
  }

  public List<EntityMetadataModel> processEmbeddedIds(List<EmbeddedIdModel> embeddedIds) {
    return embeddedIds.stream().map(this::processEmbeddedId).toList();
  }

  EntityMetadataModel process(EntityModel entity) {
    EntityMetadataModel metadata = new EntityMetadataModel();
    metadata.setPackageName(entity.getPkg());
    metadata.setClassName(entity.getName());
    metadata.setTableName(entity.getTableName());

    List<FieldMetadataModel> fields = new ArrayList<>();

    if (entity.getEmbeddedId() != null) {
      fields.add(process(entity.getEmbeddedId()));
    }

    fields.addAll(entity.getFields().stream().map(this::process).toList());
    fields.addAll(entity.getOneToOnes().stream().map(this::process).toList());
    fields.addAll(entity.getOneToManies().stream().map(this::process).toList());
    fields.addAll(entity.getManyToOnes().stream().map(this::process).toList());
    fields.addAll(entity.getManyToManies().stream().map(this::process).toList());

    metadata.setFields(fields);

    return metadata;
  }

  FieldMetadataModel process(FieldModel field) {
    FieldMetadataModel metadata = new FieldMetadataModel();
    metadata.setFieldName(field.getName());
    metadata.setColumnName(field.getColumnName());
    metadata.setJavaType(field.getType());
    metadata.setJavaWrapperType(toWrapperType(field.getType()));

    ColumnModel column = field.getColumn();
    if (column.getDATA_TYPE() == Types.VARCHAR || column.getDATA_TYPE() == Types.CHAR) {
      metadata.setStringLength(column.getCOLUMN_SIZE());
    }
    metadata.setRequired(column.getNULLABLE() == DatabaseMetaData.columnNoNulls);
    metadata.setId(field.isId());
    metadata.setAutoIncrement(StringUtils.equalsIgnoreCase(column.getIS_AUTOINCREMENT(), "YES"));

    return metadata;
  }

  FieldMetadataModel process(EmbeddedIdModel embeddedId) {
    FieldMetadataModel metadata = new FieldMetadataModel();
    metadata.setFieldName("id");
    metadata.setJavaType(embeddedId.getName());
    metadata.setJavaWrapperType(metadata.getJavaType());
    metadata.setId(true);

    return metadata;
  }

  FieldMetadataModel process(OneToOneModel oneToOne) {
    FieldMetadataModel metadata = new FieldMetadataModel();
    metadata.setFieldName(oneToOne.getFieldName());
    metadata.setJavaType(oneToOne.getEntity().getFqdn());
    metadata.setJavaWrapperType(metadata.getJavaType());

    return metadata;
  }

  FieldMetadataModel process(OneToManyModel oneToMany) {
    FieldMetadataModel metadata = new FieldMetadataModel();
    metadata.setFieldName(oneToMany.getFieldName());
    metadata.setJavaType(oneToMany.getEntity().getFqdn());
    metadata.setJavaWrapperType(metadata.getJavaType());
    metadata.setMultiple(true);

    return metadata;
  }

  FieldMetadataModel process(ManyToOneModel manyToOne) {
    FieldMetadataModel metadata = new FieldMetadataModel();
    metadata.setFieldName(manyToOne.getFieldName());
    metadata.setJavaType(manyToOne.getEntity().getFqdn());
    metadata.setJavaWrapperType(metadata.getJavaType());

    return metadata;
  }

  FieldMetadataModel process(ManyToManyModel manyToMany) {
    FieldMetadataModel metadata = new FieldMetadataModel();
    metadata.setFieldName(manyToMany.getFieldName());
    metadata.setJavaType(manyToMany.getEntity().getFqdn());
    metadata.setJavaWrapperType(metadata.getJavaType());
    metadata.setMultiple(true);

    return metadata;
  }

  EntityMetadataModel processEmbeddedId(EmbeddedIdModel embeddedId) {
    EntityMetadataModel metadata = new EntityMetadataModel();
    metadata.setPackageName(embeddedId.getPkg());
    metadata.setClassName(embeddedId.getName());

    List<FieldMetadataModel> fields = new ArrayList<>();
    fields.addAll(embeddedId.getFields().stream().map(this::process).toList());

    metadata.setFields(fields);

    return metadata;
  }

  private String toWrapperType(String javaType) {
    return switch (javaType) {
      case "int" -> "Integer";
      case "long" -> "Long";
      case "double" -> "Double";
      case "float" -> "Float";
      case "boolean" -> "Boolean";
      case "char" -> "Character";
      case "byte" -> "Byte";
      case "short" -> "Short";
      default -> javaType;
    };
  }
}
