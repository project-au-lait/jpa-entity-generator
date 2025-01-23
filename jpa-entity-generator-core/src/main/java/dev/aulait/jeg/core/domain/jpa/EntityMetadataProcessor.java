package dev.aulait.jeg.core.domain.jpa;

import dev.aulait.jeg.core.domain.jdbc.ColumnModel;
import java.sql.DatabaseMetaData;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

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
    metadata.setJavaType(field.getType());

    ColumnModel column = field.getColumn();
    if (column.getDATA_TYPE() == Types.VARCHAR || column.getDATA_TYPE() == Types.CHAR) {
      metadata.setStringLength(column.getCOLUMN_SIZE());
    }
    metadata.setRequired(column.getNULLABLE() == DatabaseMetaData.columnNoNulls);
    metadata.setId(field.isId());

    return metadata;
  }

  FieldMetadataModel process(EmbeddedIdModel embeddedId) {
    FieldMetadataModel metadata = new FieldMetadataModel();
    metadata.setFieldName("id");
    metadata.setJavaType(embeddedId.getName());
    metadata.setId(true);

    return metadata;
  }

  FieldMetadataModel process(OneToOneModel oneToOne) {
    FieldMetadataModel metadata = new FieldMetadataModel();
    metadata.setFieldName(oneToOne.getFieldName());
    metadata.setJavaType(oneToOne.getEntity().getFqdn());

    return metadata;
  }

  FieldMetadataModel process(OneToManyModel oneToMany) {
    FieldMetadataModel metadata = new FieldMetadataModel();
    metadata.setFieldName(oneToMany.getFieldName());
    metadata.setJavaType(oneToMany.getEntity().getFqdn());
    metadata.setMultiple(true);

    return metadata;
  }

  FieldMetadataModel process(ManyToOneModel manyToOne) {
    FieldMetadataModel metadata = new FieldMetadataModel();
    metadata.setFieldName(manyToOne.getFieldName());
    metadata.setJavaType(manyToOne.getEntity().getFqdn());

    return metadata;
  }

  FieldMetadataModel process(ManyToManyModel manyToMany) {
    FieldMetadataModel metadata = new FieldMetadataModel();
    metadata.setFieldName(manyToMany.getFieldName());
    metadata.setJavaType(manyToMany.getEntity().getFqdn());
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
}
