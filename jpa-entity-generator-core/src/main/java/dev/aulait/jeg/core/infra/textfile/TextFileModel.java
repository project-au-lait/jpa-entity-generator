package dev.aulait.jeg.core.infra.textfile;

import java.nio.file.Path;
import lombok.Data;

@Data
public class TextFileModel {
  private Path path;
  private String text;
}
