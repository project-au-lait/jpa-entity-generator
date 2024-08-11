package dev.aulait.jeg.core.interfaces;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import dev.aulait.jeg.core.infra.config.Config;
import dev.aulait.jeg.core.infra.config.ConfigLoader;
import org.junit.jupiter.api.Test;
import picocli.CommandLine;

class MainTests {

  static class NopMain extends Main {

    @Override
    public Integer call() throws Exception {
      // NOP
      return 0;
    }
  }

  @Test
  void allArgsTest() {

    String configFilePath = "file-path";
    String jdbcUrl = "jdbc_url";
    String jdbcUsername = "jdbc_usernmae";
    String jdbcPassword = "jdbc_password";
    String outputDir = "output_dir";

    String[] args = {
      "-c=" + configFilePath,
      "--jdbc-url=" + jdbcUrl,
      "--jdbc-username",
      jdbcUsername,
      "--jdbc-password",
      jdbcPassword,
      "--output-dir",
      outputDir
    };
    Main main = new NopMain();

    new CommandLine(main).execute(args);
    assertEquals(configFilePath, main.getConfigFilePath());
    assertEquals(jdbcUrl, main.getRuntimeConfig().getJdbcUrl());
    assertEquals(jdbcUsername, main.getRuntimeConfig().getJdbcUsername());
    assertEquals(jdbcPassword, main.getRuntimeConfig().getJdbcPassword());
    assertEquals(outputDir, main.getRuntimeConfig().getOutputDir());

    Config config = main.initConfig();

    assertEquals(jdbcUrl, config.getRuntime().getJdbcUrl());
    assertEquals(jdbcUsername, config.getRuntime().getJdbcUsername());
    assertEquals(jdbcPassword, config.getRuntime().getJdbcPassword());
    assertEquals(outputDir, config.getRuntime().getOutputDir());
  }

  @Test
  void noRuntimeConfitArgsTest() {
    Config defaultConfig = ConfigLoader.load();

    Main main = new NopMain();

    new CommandLine(main).execute();
    Config config = main.initConfig();

    assertNotNull(config.getRuntime().getJdbcUrl());
    assertNotNull(config.getRuntime().getJdbcUsername());
    assertNotNull(config.getRuntime().getJdbcPassword());
    assertNotNull(config.getRuntime().getOutputDir());

    assertEquals(defaultConfig.getRuntime(), config.getRuntime());
  }
}
