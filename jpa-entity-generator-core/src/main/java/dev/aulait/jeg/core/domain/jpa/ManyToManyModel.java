package dev.aulait.jeg.core.domain.jpa;

import java.util.ArrayList;
import java.util.List;
import lombok.Data;

@Data
public class ManyToManyModel {
  private String fieldName;
  private EntityModel entity;
  private String joinTable;
  private List<JoinColumnModel> joinColumns = new ArrayList<>();
  private List<JoinColumnModel> inverseJoinColumns = new ArrayList<>();
  private List<AnnotationModel> annotations = new ArrayList<>();

  public String getType() {
    return entity.getName();
  }

  public List<Type> getImports() {
    return List.of(
        Types.ManyToMany,
        Types.FetchType,
        Types.JoinTable,
        Types.JoinColumn,
        entity,
        Types.Set,
        Types.HashSet);
  }
}
