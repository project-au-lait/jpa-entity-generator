package dev.aulait.jeg.core.domain.jpa;

import java.util.ArrayList;
import java.util.List;
import lombok.Data;

@Data
public class OneToManyModel {
  private String fieldName;
  private String mappedBy;
  private EntityModel entity;
  private List<CascadeType> cascades = new ArrayList<>();
  private List<JoinColumnModel> joinColumns = new ArrayList<>();
  private List<AnnotationModel> annotations = new ArrayList<>();

  public String getType() {
    return entity.getName();
  }

  public List<Type> getImports() {
    return List.of(
        Types.OneToMany,
        Types.CascadeType,
        Types.FetchType,
        Types.Set,
        entity,
        Types.HashSet,
        Types.JoinColumn);
  }
}
