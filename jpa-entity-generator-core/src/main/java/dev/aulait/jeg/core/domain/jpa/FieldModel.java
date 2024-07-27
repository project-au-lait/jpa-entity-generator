package dev.aulait.jeg.core.domain.jpa;

import java.util.ArrayList;
import java.util.List;
import lombok.Data;

@Data
public class FieldModel {
  private String name;
  private String type;
  private String columnName;
  private boolean id;
  private boolean embeddedId;
  private List<AnnotationModel> annotations = new ArrayList<>();
}
