package dev.aulait.jeg.core.interfaces;

import dev.aulait.jeg.core.application.EntityGenerator;
import dev.aulait.jeg.core.infra.config.ConfigLoader;
import org.slf4j.bridge.SLF4JBridgeHandler;

/** Main */
public class Main {

  public static void main(String[] args) {
    SLF4JBridgeHandler.removeHandlersForRootLogger();
    SLF4JBridgeHandler.install();

    String configFilePath = args.length > 0 ? args[0] : "";
    new Main().execute(configFilePath);
  }

  public void execute(String configFilePath) {
    EntityGenerator generator = new EntityGenerator(ConfigLoader.load(configFilePath));
    generator.execute();
  }
}
