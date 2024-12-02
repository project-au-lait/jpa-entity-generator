package dev.aulait.jeg.core.domain.jpa;

import java.util.ArrayList;
import java.util.List;
import lombok.Data;
import lombok.Getter;
import org.apache.commons.collections4.CollectionUtils;

@Data
public class OneToOneModel {
  private String fieldName;
  private EntityModel entity;
  private String mappedBy;
  private List<CascadeType> cascades = new ArrayList<>();
  private List<AnnotationModel> annotations = new ArrayList<>();

  @Getter(lazy = true)
  private final List<Type> imports = buildImports();

  public String getType() {
    return entity.getName();
  }

  private List<Type> buildImports() {
    List<Type> imports = new ArrayList<>();
    imports.add(Types.OneToOne);
    imports.add(Types.FetchType);
    imports.add(Types.PrimaryKeyJoinColumn);
    imports.add(entity);

    if (CollectionUtils.isNotEmpty(cascades)) {
      imports.add(Types.CascadeType);
    }

    return imports;
  }
}
