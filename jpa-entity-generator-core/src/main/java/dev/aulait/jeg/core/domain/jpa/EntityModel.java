package dev.aulait.jeg.core.domain.jpa;

import dev.aulait.jeg.core.domain.jdbc.TableModel;
import dev.aulait.jeg.core.infra.template.TemplateModel;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.SortedSet;
import java.util.TreeSet;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

@Data
@EqualsAndHashCode(callSuper = true)
public class EntityModel extends TemplateModel implements Type {

  private String name;
  private String tableName;
  private String pkg;
  private SortedSet<String> imports = new TreeSet<>();
  private String baseClass;
  private EmbeddedIdModel embeddedId;
  private List<FieldModel> fields = new ArrayList<>();
  private TableModel table;
  private List<OneToOneModel> oneToOnes = new ArrayList<>();
  private List<OneToManyModel> oneToManies = new ArrayList<>();
  private List<ManyToOneModel> manyToOnes = new ArrayList<>();
  private List<ManyToManyModel> manyToManies = new ArrayList<>();
  private String parentType;

  @Getter(lazy = true)
  private final String fqdn = pkg + "." + name;

  public FieldModel findFieldByColumnName(String columnName) {
    return fields.stream()
        .filter(field -> field.getColumnName().equals(columnName))
        .findFirst()
        .orElse(null);
  }

  @Override
  public String getTemplateName() {
    return "Entity.java";
  }

  public void addImports(List<Type> types) {
    types.stream().forEach(this::addImport);
  }

  public void addImport(Type type) {
    if (StringUtils.equals(pkg, type.getPkg())) {
      return;
    }

    imports.add(type.getFqdn());
  }

  public boolean isEmbeddedIdOnly() {
    return fields.stream().allMatch(FieldModel::isEmbeddedId);
  }

  public Optional<ManyToOneModel> findManyToOneByFieldName(String fieldName) {
    return manyToOnes.stream().filter(m -> m.getFieldName().equals(fieldName)).findFirst();
  }
}
