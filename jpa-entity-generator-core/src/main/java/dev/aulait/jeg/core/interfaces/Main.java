package dev.aulait.jeg.core.interfaces;

import dev.aulait.jeg.core.application.EntityGenerator;
import dev.aulait.jeg.core.infra.config.Config;
import dev.aulait.jeg.core.infra.config.ConfigLoader;
import dev.aulait.jeg.core.infra.config.RuntimeConfig;
import dev.aulait.jeg.core.infra.util.ModelUtils;
import java.util.concurrent.Callable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.slf4j.bridge.SLF4JBridgeHandler;
import picocli.CommandLine;
import picocli.CommandLine.ArgGroup;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

/** Main */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Command(name = "", mixinStandardHelpOptions = true)
public class Main implements Callable<Integer> {

  @Option(names = {"-c", "--config-file"})
  private String configFilePath;

  @ArgGroup(exclusive = false)
  private RuntimeConfig runtimeConfig = new RuntimeConfig();

  public static void main(String[] args) {
    SLF4JBridgeHandler.removeHandlersForRootLogger();
    SLF4JBridgeHandler.install();

    int exitCode = new CommandLine(new Main()).execute(args);
    System.exit(exitCode);
  }

  @Override
  public Integer call() throws Exception {
    return execute();
  }

  public int execute() {
    EntityGenerator generator = new EntityGenerator(initConfig());
    generator.execute();
    return 0;
  }

  Config initConfig() {
    Config config = ConfigLoader.load(configFilePath);
    ModelUtils.map(runtimeConfig, config);
    return config;
  }
}
