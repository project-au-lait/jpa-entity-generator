package dev.aulait.jeg.core.domain.jpa;

import java.util.ArrayList;
import java.util.List;
import lombok.Data;

@Data
public class OneToOneModel {
  private String fieldName;
  private EntityModel entity;
  private String mappedBy;
  private List<CascadeType> cascades = new ArrayList<>();
  private List<AnnotationModel> annotations = new ArrayList<>();

  public String getType() {
    return entity.getName();
  }

  public List<Type> getImports() {
    return List.of(Types.OneToOne, Types.FetchType, Types.PrimaryKeyJoinColumn, entity);
  }
}
