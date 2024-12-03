package dev.aulait.jeg.core.domain.jpa;

import java.util.List;

public class EntityImportProcessor {

  public void process(List<EntityModel> entities) {
    entities.stream().forEach(this::process);
  }

  void process(EntityModel entity) {
    entity.addImport(Types.Entity);
    entity.addImport(Types.Table);
    entity.addImport(Types.Column);
    entity.addImport(Types.Getter);
    entity.addImport(Types.Setter);
    entity.addImport(Types.Builder);
    entity.addImport(Types.AllArgsConstructor);
    entity.addImport(Types.NoArgsConstructor);

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
