package dev.aulait.jeg.core.infra.template;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Map;
import org.apache.commons.lang3.exception.UncheckedException;

public class TemplateUtils {

  private static Configuration cfg;

  static {
    cfg = new Configuration(Configuration.VERSION_2_3_34);
    cfg.setClassForTemplateLoading(TemplateUtils.class, "/");
    cfg.setDefaultEncoding("UTF-8");
  }

  public static String process(String template, Object param) {
    Writer out = new StringWriter();

    try {
      Template temp = cfg.getTemplate(resolve(template));
      temp.process(Map.of("root", param), out);
    } catch (TemplateException | IOException e) {
      throw new UncheckedException(e);
    }

    return out.toString();
  }

  private static String resolve(String template) {
    return "templates/" + template + ".ftl";
  }
}
