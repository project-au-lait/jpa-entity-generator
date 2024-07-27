package dev.aulait.jeg.core.infra.config;

import java.util.HashMap;
import java.util.Map;
import lombok.Data;

@Data
public class AnnotationDef {
  private String type;

  /** key: annotationName, value: annotationValue */
  private Map<String, String> attributes = new HashMap<>();
}
