package dev.aulait.jeg.core.domain.jpa;

import java.util.HashMap;
import java.util.Map;
import lombok.Data;

@Data
public class AnnotationModel {
  private String type;

  /** key: annotationName, value: annotationValue */
  private Map<String, String> attributes = new HashMap<>();
}
