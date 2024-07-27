package dev.aulait.jeg.core.infra.textfile;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TextFileWriter {

  public List<Path> write(List<TextFileModel> textFiles) {
    List<Path> files = new ArrayList<>();

    for (TextFileModel textFile : textFiles) {

      Path outdir = textFile.getPath().getParent().toAbsolutePath();
      if (!outdir.toFile().exists()) {
        log.info("Create directory: {}", outdir);
        outdir.toFile().mkdirs();
      }

      try {
        log.info("Write file: {}", textFile.getPath().toAbsolutePath());
        Files.writeString(textFile.getPath(), textFile.getText());
        files.add(textFile.getPath().toAbsolutePath());
      } catch (IOException e) {
        throw new UncheckedIOException(e);
      }
    }

    return files;
  }
}
