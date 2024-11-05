package dev.aulait.jeg.core.domain.jpa;

import dev.aulait.jeg.core.domain.jdbc.ForeignKeyLogic;
import dev.aulait.jeg.core.domain.jdbc.ForeignKeyModel;
import dev.aulait.jeg.core.domain.jdbc.KeyModel;
import dev.aulait.jeg.core.domain.jdbc.TableModel;
import dev.aulait.jeg.core.infra.config.Config;
import dev.aulait.jeg.core.infra.util.WordUtils;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;

/**
 * Implementation of the RelationProcessor interface for handling one-directional relationships
 * between JPA entities.
 *
 * <p>This class processes foreign key relationships between tables and builds the appropriate JPA
 * entity relationships (OneToOne, OneToMany, ManyToOne, ManyToMany) based on the foreign key
 * constraints.
 *
 * <p>It uses the provided configuration and logic classes to determine the type of relationship and
 * to generate the necessary annotations and join columns for the entities.
 */
@Slf4j
public class RelationProcessorOneDirectionalImpl implements RelationProcessor {

  Config config;

  ForeignKeyLogic logic = new ForeignKeyLogic();

  AnnotationLogic annotationLogic;

  // key: TABLE_NAME
  Map<String, EntityModel> entityMap;

  public RelationProcessorOneDirectionalImpl(Config config) {
    this.config = config;
    annotationLogic = new AnnotationLogic(config);
  }

  @Override
  public void process(List<TableModel> tables, List<EntityModel> entities) {

    entityMap =
        entities.stream()
            .collect(Collectors.toMap(entity -> entity.getTableName(), entity -> entity));

    for (TableModel table : tables) {
      for (ForeignKeyModel fk : table.getForeignKeys()) {
        process(fk);
      }
    }
  }

  void process(ForeignKeyModel fk) {
    log.trace("Processing FK: {}", fk.getName());

    if (isOneToOneRequired(fk)) {
      log.trace("FK: {} requires OneToOne", fk.getName());
      buildOneToOne(fk);

    } else if (isOneToManyRequired(fk)) {
      log.trace("FK: {} requires OneToMany", fk.getName());
      buildOneToMany(fk);

    } else if (isManyToOneRequired(fk)) {
      log.trace("FK: {} requires ManyToOne", fk.getName());
      buildManyToOne(fk);

    } else if (isManyToManyRequired(fk)) {
      log.trace("FK: {} requires ManyToMany", fk.getName());
      buildManyToMany(fk);
    }
  }

  protected boolean isOneToOneRequired(ForeignKeyModel fk) {
    return logic.isOneToOne(fk);
  }

  protected boolean isOneToManyRequired(ForeignKeyModel fk) {
    return logic.isParentChild(fk);
  }

  protected boolean isManyToOneRequired(ForeignKeyModel fk) {
    return logic.isManyToOne(fk);
  }

  protected boolean isManyToManyRequired(ForeignKeyModel fk) {
    return logic.isInRelationTable(fk) && logic.isFirstInPk(fk);
  }

  /**
   * Builds a one-to-one relationship between two entities based on the provided foreign key model.
   *
   * @param fk the foreign key model containing information about the foreign key relationship
   */
  protected void buildOneToOne(ForeignKeyModel fk) {
    EntityModel fkEntity = entityMap.get(fk.getFkTable().getTABLE_NAME());
    EntityModel pkEntity = entityMap.get(fk.getPkTable().getTABLE_NAME());

    OneToOneModel oneToOne = new OneToOneModel();
    oneToOne.setFieldName(
        WordUtils.entityNameToFieldName(fkEntity.getName(), config.getEntitySuffix()));
    oneToOne.setEntity(fkEntity);

    oneToOne
        .getAnnotations()
        .addAll(annotationLogic.find(pkEntity.getName(), oneToOne.getFieldName()));

    pkEntity.getOneToOnes().add(oneToOne);
  }

  /**
   * Builds a one-to-many relationship between two entities based on the provided foreign key model.
   *
   * @param fk the foreign key model containing information about the relationship
   */
  protected void buildOneToMany(ForeignKeyModel fk) {
    TableModel oneTable = fk.getPkTable();
    TableModel manyTable = fk.getFkTable();
    EntityModel oneEntity = entityMap.get(oneTable.getTABLE_NAME());
    EntityModel manyEntity = entityMap.get(manyTable.getTABLE_NAME());

    OneToManyModel oneToMany = new OneToManyModel();

    oneToMany.setEntity(manyEntity);
    oneToMany.setFieldName(
        WordUtils.entityNameToPluralFieldName(manyEntity.getName(), config.getEntitySuffix()));

    for (KeyModel key : fk.getKeys()) {
      JoinColumnModel joinColumn = new JoinColumnModel();
      joinColumn.setName(key.getFKCOLUMN_NAME());
      oneToMany.getJoinColumns().add(joinColumn);
    }

    oneToMany
        .getAnnotations()
        .addAll(annotationLogic.find(oneEntity.getName(), oneToMany.getFieldName()));

    oneEntity.getOneToManies().add(oneToMany);
  }

  /**
   * Builds a Many-to-One relationship between two entities based on the provided ForeignKeyModel.
   *
   * @param fk the ForeignKeyModel containing information about the foreign key relationship
   */
  protected void buildManyToOne(ForeignKeyModel fk) {
    TableModel manyTable = fk.getFkTable();
    TableModel oneTable = fk.getPkTable();
    EntityModel manyEntity = entityMap.get(manyTable.getTABLE_NAME());
    EntityModel oneEntity = entityMap.get(oneTable.getTABLE_NAME());
    ManyToOneModel manyToOne = new ManyToOneModel();

    manyToOne.setFieldName(
        WordUtils.entityNameToFieldName(oneEntity.getName(), config.getEntitySuffix()));
    manyToOne.setEntity(oneEntity);

    for (KeyModel key : fk.getKeys()) {
      JoinColumnModel joinColumn = new JoinColumnModel();
      joinColumn.setName(key.getFKCOLUMN_NAME());
      manyToOne.getJoinColumns().add(joinColumn);
    }

    manyToOne
        .getAnnotations()
        .addAll(annotationLogic.find(manyEntity.getName(), manyToOne.getFieldName()));

    manyEntity.getManyToOnes().add(manyToOne);

    for (JoinColumnModel joinColumn : manyToOne.getJoinColumns()) {
      FieldModel field = manyEntity.findFieldByColumnName(joinColumn.getName());
      manyEntity.getFields().remove(field);
    }
  }

  /**
   * Builds a ManyToMany relationship between entities based on the provided ForeignKeyModel.
   *
   * @param fk the ForeignKeyModel representing the foreign key relationship between tables
   */
  protected void buildManyToMany(ForeignKeyModel fk) {
    TableModel leftTable = fk.getPkTable();
    TableModel relTable = fk.getFkTable();
    TableModel rightTable =
        relTable.getForeignKeys().stream()
            .filter(relFk -> !relFk.equals(fk))
            .map(ForeignKeyModel::getPkTable)
            .findFirst()
            .get();

    EntityModel leftEntity = entityMap.get(leftTable.getTABLE_NAME());
    ManyToManyModel manyToMany = new ManyToManyModel();

    // Entities that are the right-hand side of ManyToMany
    EntityModel rightEntity = entityMap.get(rightTable.getTABLE_NAME());
    manyToMany.setEntity(rightEntity);
    manyToMany.setFieldName(
        WordUtils.entityNameToPluralFieldName(rightEntity.getName(), config.getEntitySuffix()));
    manyToMany.setJoinTable(relTable.getTABLE_NAME());

    for (ForeignKeyModel fkInRel : relTable.getForeignKeys()) {
      for (KeyModel key : fkInRel.getKeys()) {
        JoinColumnModel joinColumn = new JoinColumnModel();
        joinColumn.setName(key.getFKCOLUMN_NAME());
        if (fkInRel.getPkTable().equals(leftTable)) {
          manyToMany.getJoinColumns().add(joinColumn);
        } else {
          manyToMany.getInverseJoinColumns().add(joinColumn);
        }
      }
    }

    manyToMany
        .getAnnotations()
        .addAll(annotationLogic.find(leftEntity.getName(), manyToMany.getFieldName()));

    leftEntity.getManyToManies().add(manyToMany);
  }
}
