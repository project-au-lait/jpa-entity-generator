package dev.aulait.jeg.core.infra.template;

import java.nio.file.Path;
import lombok.Data;

@Data
public abstract class TemplateModel {
  private Path filePath;

  private String templateKey;

  public abstract String getTemplateName();
}
