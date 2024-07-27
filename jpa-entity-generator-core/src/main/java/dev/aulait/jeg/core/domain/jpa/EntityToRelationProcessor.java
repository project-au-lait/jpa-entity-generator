package dev.aulait.jeg.core.domain.jpa;

import dev.aulait.jeg.core.domain.jdbc.KeyModel;
import dev.aulait.jeg.core.domain.jdbc.TableLogic;
import dev.aulait.jeg.core.domain.jdbc.TableModel;
import dev.aulait.jeg.core.infra.config.Config;
import dev.aulait.jeg.core.infra.util.WordUtils;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class EntityToRelationProcessor {
  Config config;

  TableLogic logic = new TableLogic();

  AnnotationLogic annotationLogic;

  public EntityToRelationProcessor(Config config) {
    this.config = config;
    annotationLogic = new AnnotationLogic(config);
  }

  public void process(List<EntityModel> entities) {

    // key: TABLE_NAME
    Map<String, EntityModel> entityMap =
        entities.stream()
            .collect(Collectors.toMap(entity -> entity.getTableName(), entity -> entity));

    for (EntityModel entity : entities) {

      for (TableModel referencedTable : entity.getTable().getReferencedTableMap().keySet()) {

        if (logic.isOneToOne(entity.getTable(), referencedTable)) {
          OneToOneModel oneToOne = buildReferencedOneToOne(entity, referencedTable, entityMap);
          entity.getOneToOnes().add(oneToOne);

        } else if (logic.isOneToMany(entity.getTable(), referencedTable)) {

          if (logic.isParentChild(entity.getTable(), referencedTable)) {
            OneToManyModel oneToMany = buildOneToMany(entity, referencedTable, entityMap);
            entity.getOneToManies().add(oneToMany);
          }

          // TODO Temporary non-support for ManyToMany bi-directional association generation
          //      Unidirectional only.
          // } else if (logic.isRelationTable(entity.getTable(), referencedTable)) {
        } else if (logic.isFirstTableInRelation(entity.getTable(), referencedTable)) {

          ManyToManyModel manyToMany =
              buildManyToMany(entity.getTable(), referencedTable, entityMap);
          entity.getManyToManies().add(manyToMany);
        }
      }

      for (TableModel referencingTable : entity.getTable().getReferencingTableMap().keySet()) {
        // TODO Temporary non-support for OneToOne bi-directional association generation
        //      Unidirectional only.
        // if (logic.isOneToOne(referencingTable, entity.getTable())) {
        // OneToOneModel oneToOne = buildReferencingOneToOne(referencingTable,
        // entityMap);
        // entity.getOneToOnes().add(oneToOne);

        // } else
        if (logic.isManyToOne(entity.getTable(), referencingTable)) {

          if (logic.isParentChild(referencingTable, entity.getTable())) {
            continue;
          }

          ManyToOneModel manyToOne = buildManyToOne(entity.getTable(), referencingTable, entityMap);
          entity.getManyToOnes().add(manyToOne);
        }
      }
    }

    for (EntityModel entity : entities) {
      for (ManyToOneModel manyToOne : entity.getManyToOnes()) {
        for (JoinColumnModel joinColumn : manyToOne.getJoinColumns()) {
          FieldModel field = entity.findFieldByColumnName(joinColumn.getName());
          entity.getFields().remove(field);
        }
      }
    }
  }

  /**
   * @param oneTable
   * @param toOneTable
   * @param entityMap
   * @return
   */
  OneToOneModel buildReferencedOneToOne(
      EntityModel oneEntity, TableModel toOneTable, Map<String, EntityModel> entityMap) {

    OneToOneModel oneToOne = buildReferencingOneToOne(toOneTable, entityMap);
    // TODO Temporary non-support for OneToOne bi-directional association generation
    //      Unidirectional only.
    // oneToOne.setMappedBy(
    // WordUtils.entityNameToFieldName(oneEntity.getName(),
    // config.getEntitySuffix()));

    for (KeyModel key : toOneTable.getReferencingTableMap().get(oneEntity.getTable())) {
      List<CascadeType> cascades =
          config.findCascades(
              oneEntity.getTableName(), toOneTable.getTABLE_NAME(), key.getFKCOLUMN_NAME());
      oneToOne.getCascades().addAll(cascades);
    }

    oneToOne
        .getAnnotations()
        .addAll(annotationLogic.find(oneEntity.getName(), oneToOne.getFieldName()));

    return oneToOne;
  }

  /**
   * @param toOneTable
   * @param entityMap
   * @return
   */
  OneToOneModel buildReferencingOneToOne(
      TableModel toOneTable, Map<String, EntityModel> entityMap) {
    OneToOneModel oneToOne = new OneToOneModel();

    EntityModel toOneEntity = entityMap.get(toOneTable.getTABLE_NAME());
    oneToOne.setFieldName(
        WordUtils.entityNameToFieldName(toOneEntity.getName(), config.getEntitySuffix()));
    oneToOne.setEntity(toOneEntity);

    return oneToOne;
  }

  /**
   * @param oneTable
   * @param manyTable
   * @param entityMap
   * @return
   */
  OneToManyModel buildOneToMany(
      EntityModel oneEntity, TableModel manyTable, Map<String, EntityModel> entityMap) {
    OneToManyModel oneToMany = new OneToManyModel();

    EntityModel refEntity = entityMap.get(manyTable.getTABLE_NAME());
    oneToMany.setEntity(refEntity);
    oneToMany.setFieldName(
        WordUtils.entityNameToPluralFieldName(refEntity.getName(), config.getEntitySuffix()));

    // Commented out as bi-directional associations are not generated once.
    // oneToMany.setMappedBy(
    // WordUtils.entityNameToFieldName(oneEntity.getName(),
    // config.getEntitySuffix()));

    for (KeyModel key : manyTable.getReferencingTableMap().get(oneEntity.getTable())) {
      List<CascadeType> cascades =
          config.findCascades(
              oneEntity.getTableName(), manyTable.getTABLE_NAME(), key.getFKCOLUMN_NAME());
      oneToMany.getCascades().addAll(cascades);

      JoinColumnModel joinColumn = new JoinColumnModel();
      joinColumn.setName(key.getFKCOLUMN_NAME());
      oneToMany.getJoinColumns().add(joinColumn);
    }

    oneToMany
        .getAnnotations()
        .addAll(annotationLogic.find(oneEntity.getName(), oneToMany.getFieldName()));

    return oneToMany;
  }

  /**
   * @param manyTable
   * @param oneTable
   * @param entityMap
   * @return
   */
  ManyToOneModel buildManyToOne(
      TableModel manyTable, TableModel oneTable, Map<String, EntityModel> entityMap) {
    ManyToOneModel manyToOne = new ManyToOneModel();

    EntityModel oneEntity = entityMap.get(oneTable.getTABLE_NAME());
    manyToOne.setFieldName(
        WordUtils.entityNameToFieldName(oneEntity.getName(), config.getEntitySuffix()));
    manyToOne.setEntity(oneEntity);

    // JoinColumnModel joinColumn = new JoinColumnModel();
    // // TODO ManyToOne with compound key.
    // joinColumn.setName(
    // manyTable.getReferencingTableMap().get(oneTable).iterator().next().getFKCOLUMN_NAME());
    // manyToOne.setJoinColumn(joinColumn);

    for (KeyModel key : manyTable.getReferencingTableMap().get(oneTable)) {
      JoinColumnModel joinColumn = new JoinColumnModel();
      joinColumn.setName(key.getFKCOLUMN_NAME());
      manyToOne.getJoinColumns().add(joinColumn);
    }

    manyToOne
        .getAnnotations()
        .addAll(
            annotationLogic.find(
                entityMap.get(manyTable.getTABLE_NAME()).getName(), manyToOne.getFieldName()));

    return manyToOne;
  }

  /**
   * @param leftTable The table that is the left-hand side of ManyToMany
   * @param relTable related table
   * @param entityMap
   * @return
   */
  ManyToManyModel buildManyToMany(
      TableModel leftTable, TableModel relTable, Map<String, EntityModel> entityMap) {
    ManyToManyModel manyToMany = new ManyToManyModel();

    // Tables that are the right-hand side of ManyToMany
    TableModel rightTable =
        relTable.getReferencingTableMap().keySet().stream()
            .filter(Predicate.not(leftTable::equals))
            .findFirst()
            .get();

    // Entities that are the right-hand side of ManyToMany
    EntityModel rightEntity = entityMap.get(rightTable.getTABLE_NAME());
    manyToMany.setEntity(rightEntity);
    manyToMany.setFieldName(
        WordUtils.entityNameToPluralFieldName(rightEntity.getName(), config.getEntitySuffix()));
    manyToMany.setJoinTable(relTable.getTABLE_NAME());

    for (KeyModel key : relTable.getReferencingKeys()) {

      if (leftTable.getPkColumns().contains(key.getPkColumn())) {
        JoinColumnModel joinColumn = new JoinColumnModel();
        joinColumn.setName(key.getFKCOLUMN_NAME());
        manyToMany.getJoinColumns().add(joinColumn);
      } else {
        JoinColumnModel inverseJoinColumn = new JoinColumnModel();
        inverseJoinColumn.setName(key.getFKCOLUMN_NAME());
        manyToMany.getInverseJoinColumns().add(inverseJoinColumn);
      }
    }

    manyToMany
        .getAnnotations()
        .addAll(
            annotationLogic.find(
                entityMap.get(leftTable.getTABLE_NAME()).getName(), manyToMany.getFieldName()));

    return manyToMany;
  }
}
