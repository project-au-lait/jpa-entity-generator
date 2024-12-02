package dev.aulait.jeg.core.domain.jpa;

import java.util.ArrayList;
import java.util.List;
import lombok.Data;
import lombok.Getter;
import org.apache.commons.collections4.CollectionUtils;

@Data
public class OneToManyModel {
  private String fieldName;
  private String mappedBy;
  private EntityModel entity;
  private List<CascadeType> cascades = new ArrayList<>();
  private List<JoinColumnModel> joinColumns = new ArrayList<>();
  private List<AnnotationModel> annotations = new ArrayList<>();

  @Getter(lazy = true)
  private final List<Type> imports = buildImports();

  public String getType() {
    return entity.getName();
  }

  private List<Type> buildImports() {
    List<Type> imps = new ArrayList<>();
    imps.add(Types.OneToMany);
    imps.add(Types.FetchType);
    imps.add(Types.Set);
    imps.add(entity);
    imps.add(Types.HashSet);
    imps.add(Types.JoinColumn);

    if (CollectionUtils.isNotEmpty(cascades)) {
      imps.add(Types.CascadeType);
    }

    return imps;
  }
}
