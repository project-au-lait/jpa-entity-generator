package dev.aulait.jeg.core.domain.jpa;

import java.util.List;

public class EntityImportProcessor {

  public void process(List<EntityModel> entities) {
    entities.stream().forEach(this::process);
  }

  void process(EntityModel entity) {
    entity.addImport(Types.Entity);
    entity.addImport(Types.Table);
    entity.addImport(Types.Data);
    entity.addImport(Types.Builder);
    entity.addImport(Types.EqualsAndHashCode);
    entity.addImport(Types.AllArgsConstructor);
    entity.addImport(Types.NoArgsConstructor);
    entity.addImport(Types.Generated);

    if (!entity.isEmbeddedIdOnly()) {
      entity.addImport(Types.Column);
    }

    if (entity.getEmbeddedId() == null) {
      entity.addImport(Types.Id);
    } else {
      entity.addImport(Types.EmbeddedId);
    }

    entity.getOneToOnes().stream().map(OneToOneModel::getImports).forEach(entity::addImports);

    entity.getOneToManies().stream().map(OneToManyModel::getImports).forEach(entity::addImports);

    entity.getManyToOnes().stream().map(ManyToOneModel::getImports).forEach(entity::addImports);

    entity.getManyToManies().stream().map(ManyToManyModel::getImports).forEach(entity::addImports);
  }
}
