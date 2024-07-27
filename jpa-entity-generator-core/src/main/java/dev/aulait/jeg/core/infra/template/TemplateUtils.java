package dev.aulait.jeg.core.infra.template;

import java.util.Locale;
import java.util.Map;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

public class TemplateUtils {

  private static TemplateEngine engine;

  static {
    engine = new TemplateEngine();
    ClassLoaderTemplateResolver resolver = new ClassLoaderTemplateResolver();
    resolver.setTemplateMode(TemplateMode.TEXT);
    engine.addTemplateResolver(resolver);
  }

  public static String process(String template, Object param) {
    Context ctx = new Context(Locale.getDefault(), Map.of("root", param));
    return engine.process(resolve(template), ctx);
  }

  private static String resolve(String template) {
    return "/templates/" + template + ".txt";
  }
}
