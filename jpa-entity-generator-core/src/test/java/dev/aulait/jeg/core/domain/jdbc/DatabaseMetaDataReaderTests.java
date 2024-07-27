package dev.aulait.jeg.core.domain.jdbc;

import dev.aulait.jeg.core.infra.config.ConfigLoader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
public class DatabaseMetaDataReaderTests {

  static DatabaseMetaDataReader reader = new DatabaseMetaDataReader(ConfigLoader.load());

  @Test
  void testExportedKeys() throws IOException {
    DatabaseMetaDataModel meta = reader.read();

    String exportedKeys = keyMapToString(meta.getExportedKeyMap());

    Path file = Path.of("target", "exported-keys.txt").toAbsolutePath();
    log.info("Write {}", file);
    Files.writeString(file, exportedKeys);
  }

  // @Test
  // void testImportedKeys() throws IOException {
  //   DatabaseMetaDataModel meta = reader.read();

  //   String importedKeys = keyMapToString(meta.getImportedKeyMap());

  //   Files.writeString(Path.of("target", "imported-keys.txt"), importedKeys);
  // }

  String keyMapToString(Map<String, List<KeyModel>> map) {
    StringBuilder sb = new StringBuilder();
    for (Entry<String, List<KeyModel>> entry : map.entrySet()) {
      sb.append(entry.getKey());
      sb.append(System.lineSeparator());
      for (KeyModel key : entry.getValue()) {
        sb.append("\t");
        sb.append(key.toString());
        sb.append(System.lineSeparator());
      }
    }

    return sb.toString();
  }
}
