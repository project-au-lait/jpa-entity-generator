package dev.aulait.jeg.core.domain.jpa;

import dev.aulait.jeg.core.infra.config.AnnotationDef;
import dev.aulait.jeg.core.infra.config.Config;
import dev.aulait.jeg.core.infra.util.ModelUtils;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.modelmapper.TypeToken;

@RequiredArgsConstructor
public class AnnotationLogic {

  private final Config config;

  public List<AnnotationModel> find(String tableName, String columnName) {
    List<AnnotationDef> defs = config.findAnnotations(tableName, columnName);
    java.lang.reflect.Type listType = new TypeToken<List<AnnotationModel>>() {}.getType();
    return ModelUtils.map(defs, listType);
  }
}
