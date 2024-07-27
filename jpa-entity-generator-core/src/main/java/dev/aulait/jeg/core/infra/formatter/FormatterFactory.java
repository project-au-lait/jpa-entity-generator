package dev.aulait.jeg.core.infra.formatter;

public class FormatterFactory {

  public static Formattable create(String name) {
    if ("google".equalsIgnoreCase(name)) {
      return new GoogleFormatter();
    }

    return null;
  }
}
