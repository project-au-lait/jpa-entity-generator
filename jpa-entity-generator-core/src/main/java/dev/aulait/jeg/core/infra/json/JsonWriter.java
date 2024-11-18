package dev.aulait.jeg.core.infra.json;

import java.nio.file.Path;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JsonWriter {

  public void write(Path file, Object obj) {
    Path outputDir = file.getParent();

    if (!outputDir.toFile().exists()) {
      log.info("Creating directory: {}", outputDir);
      outputDir.toFile().mkdirs();
    }

    log.info("Writing to file: {}", file);
    JsonUtils.writeValue(file, obj);
  }
}
