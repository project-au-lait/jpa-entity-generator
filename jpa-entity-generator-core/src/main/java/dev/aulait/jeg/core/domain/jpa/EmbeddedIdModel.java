package dev.aulait.jeg.core.domain.jpa;

import dev.aulait.jeg.core.infra.template.TemplateModel;
import java.util.ArrayList;
import java.util.List;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class EmbeddedIdModel extends TemplateModel {
  private String name;
  private List<FieldModel> fields;
  private List<EmbeddedFieldModel> embeddedFields = new ArrayList<>();
  private String pkg;

  @Override
  public String getTemplateName() {
    return "EmbeddedId.java";
  }

  public List<String> getImportStrings() {
    List<String> imports = new ArrayList<>();
    if (!embeddedFields.isEmpty()) {
      imports.add("jakarta.persistence.AttributeOverride");
      imports.add("jakarta.persistence.AttributeOverrides");
    }
    imports.add("jakarta.persistence.Column");
    imports.add("jakarta.persistence.Embeddable");
    if (!embeddedFields.isEmpty()) {
      imports.add("jakarta.persistence.Embedded");
    }
    imports.add("javax.annotation.processing.Generated");
    imports.add("lombok.AllArgsConstructor");
    imports.add("lombok.Builder");
    imports.add("lombok.Data");
    imports.add("lombok.NoArgsConstructor");
    return imports;
  }
}
