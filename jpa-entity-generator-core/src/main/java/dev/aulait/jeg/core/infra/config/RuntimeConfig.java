package dev.aulait.jeg.core.infra.config;

import lombok.Data;
import picocli.CommandLine.Option;

@Data
public class RuntimeConfig {

  @Option(names = {"-o", "--output-dir"})
  private String outputDir;

  @Option(
      names = {"-m", "--metadata-output-dir"},
      required = false)
  private String metadataOutputDir;

  @Option(names = "--jdbc-url")
  private String jdbcUrl;

  @Option(names = "--jdbc-username")
  private String jdbcUsername;

  @Option(names = "--jdbc-password")
  private String jdbcPassword;
}
