package dev.aulait.jeg.core.infra.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Path;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.UncheckedException;

@Slf4j
public class ConfigLoader {

  private static final String CONFIG_FILE = "jeg-config.yml";

  static ObjectMapper mapper = new ObjectMapper(new YAMLFactory());

  public static Config load() {
    return load("");
  }

  /**
   * Reads a configuration file and returns an object with its contents. The priority order of the
   * configuration files to be read is as follows.
   *
   * <ol>
   *   <li>Path specified in the method argument.
   *   <li>jeg-config.yml directly under the current directory.
   *   <li>jeg-config.yml directly under classpath.
   * </ol>
   *
   * @param configFilePath Path of the configuration file to be loaded.
   * @return
   */
  public static Config load(String configFilePath) {
    URL configFileUrl = findConfigFileUrl(configFilePath, CONFIG_FILE);

    if (configFileUrl == null) {
      log.info("No config file found from path: {}", configFilePath);
      return new Config();
    }
    log.info("Read config: {}", configFileUrl);

    try {
      return mapper.readValue(configFileUrl, Config.class);
    } catch (IOException e) {
      throw new UncheckedException(e);
    }
  }

  static URL findConfigFileUrl(String configFilePath, String defaultConfigFileName) {
    Path configFile;

    if (StringUtils.isNoneEmpty(configFilePath)) {
      configFile = Path.of(configFilePath).toAbsolutePath();
      if (configFile.toFile().exists()) {
        return path2url(configFile);
      } else {
        log.warn("Config file path is specified but does not exist. {}", configFile);
      }
    }

    configFile = Path.of(defaultConfigFileName);
    if (configFile.toFile().exists()) {
      return path2url(configFile);
    }

    return ConfigLoader.class.getResource("/" + CONFIG_FILE);
  }

  private static URL path2url(Path path) {
    try {
      return path.toUri().toURL();
    } catch (MalformedURLException e) {
      throw new UncheckedIOException(e);
    }
  }
}
