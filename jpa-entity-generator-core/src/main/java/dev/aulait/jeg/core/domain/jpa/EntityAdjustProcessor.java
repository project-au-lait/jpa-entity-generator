package dev.aulait.jeg.core.domain.jpa;

import dev.aulait.jeg.core.infra.util.WordUtils;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class EntityAdjustProcessor {

  public void process(List<EntityModel> entities) {
    for (EntityModel entity : entities) {
      adjustManyToOneFieldName(entity);
    }
  }

  void adjustManyToOneFieldName(EntityModel entity) {

    Map<String, List<ManyToOneModel>> manyToOnesMap =
        entity.getManyToOnes().stream()
            .collect(Collectors.groupingBy(ManyToOneModel::getFieldName));

    for (List<ManyToOneModel> manyToOnes : manyToOnesMap.values()) {
      if (manyToOnes.size() > 1) {
        for (ManyToOneModel manyToOne : manyToOnes) {
          String fkName = manyToOne.getForeignKey().getName();
          fkName = fkName.replaceAll("_fkey$", "");
          manyToOne.setFieldName(WordUtils.snakeToLowerCamel(fkName));
        }
      }
    }
  }
}
