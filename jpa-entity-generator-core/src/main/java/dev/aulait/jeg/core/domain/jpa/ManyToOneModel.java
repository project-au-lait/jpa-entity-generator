package dev.aulait.jeg.core.domain.jpa;

import java.util.ArrayList;
import java.util.List;
import lombok.Data;

@Data
public class ManyToOneModel {
  private String fieldName;
  private EntityModel entity;
  private List<JoinColumnModel> joinColumns = new ArrayList<>();
  private List<AnnotationModel> annotations = new ArrayList<>();

  public String getType() {
    return entity.getName();
  }

  public List<Type> getImports() {
    List<Type> imports = new ArrayList<>();
    imports.add(Types.ManyToOne);
    imports.add(Types.FetchType);
    imports.add(Types.JoinColumn);
    if (joinColumns.size() > 1) {
      imports.add(Types.JoinColumns);
    }
    imports.add(entity);

    return imports;
  }
}
