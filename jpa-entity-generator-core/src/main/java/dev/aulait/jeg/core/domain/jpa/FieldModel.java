package dev.aulait.jeg.core.domain.jpa;

import dev.aulait.jeg.core.domain.jdbc.ColumnModel;
import java.util.ArrayList;
import java.util.List;
import lombok.Data;
import lombok.Getter;

@Data
public class FieldModel {
  private String name;
  private String type;
  private ColumnModel column;

  @Getter(lazy = true)
  private final String columnName = column.getCOLUMN_NAME();

  private boolean id;
  private boolean embeddedId;
  private List<AnnotationModel> annotations = new ArrayList<>();
}
