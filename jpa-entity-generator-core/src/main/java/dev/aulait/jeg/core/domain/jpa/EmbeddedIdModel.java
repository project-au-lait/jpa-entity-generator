package dev.aulait.jeg.core.domain.jpa;

import dev.aulait.jeg.core.infra.template.TemplateModel;
import java.util.List;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class EmbeddedIdModel extends TemplateModel {
  private String name;
  private List<FieldModel> fields;
  private String pkg;

  @Override
  public String getTemplateName() {
    return "EmbeddedId.java";
  }
}
