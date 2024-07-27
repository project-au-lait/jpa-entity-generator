package dev.aulait.jeg.core.infra.util;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Collectors;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.exception.UncheckedException;

@NoArgsConstructor
@Slf4j
public class ResourceUtils {

  public static String res2text(Object owner, String resource) {
    URL resourceUrl = owner.getClass().getResource(resource);
    if (resourceUrl == null) {
      throw new IllegalArgumentException("Resource " + resource + " is not found in classpath");
    }
    log.info("Read resource: {}", resourceUrl);

    if ("file".equals(resourceUrl.getProtocol())) {
      try {
        Path resourcePath = Path.of(resourceUrl.toURI());
        return Files.readString(resourcePath);
      } catch (URISyntaxException | IOException e) {
        throw new UncheckedException(e);
      }
    }

    try {

      return IOUtils.readLines(resourceUrl.openStream(), StandardCharsets.UTF_8).stream()
          .collect(Collectors.joining(System.lineSeparator()));
    } catch (IOException e) {
      throw new UncheckedIOException(e);
    }
  }
}
