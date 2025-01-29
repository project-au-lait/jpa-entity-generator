package dev.aulait.jeg.core.domain.jpa;

import dev.aulait.jeg.core.domain.jdbc.ForeignKeyModel;
import java.util.ArrayList;
import java.util.List;
import lombok.Data;
import lombok.Getter;

@Data
public class ManyToOneModel {
  private String fieldName;
  private EntityModel entity;
  private List<JoinColumnModel> joinColumns = new ArrayList<>();
  private List<AnnotationModel> annotations = new ArrayList<>();
  private ForeignKeyModel foreignKey;

  @Getter(lazy = true)
  private final List<Type> imports = buildImports();

  public String getType() {
    return entity.getName();
  }

  private List<Type> buildImports() {
    List<Type> imps = new ArrayList<>();
    imps.add(Types.ManyToOne);
    imps.add(Types.FetchType);
    imps.add(Types.JoinColumn);
    if (joinColumns.size() > 1) {
      imps.add(Types.JoinColumns);
    }
    imps.add(entity);

    return imps;
  }
}
