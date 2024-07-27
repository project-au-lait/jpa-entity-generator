package dev.aulait.jeg.core.infra.formatter;

import com.google.googlejavaformat.java.Formatter;
import com.google.googlejavaformat.java.FormatterException;
import org.apache.commons.lang3.exception.UncheckedException;

public class GoogleFormatter implements Formattable {

  private Formatter formatter = new Formatter();

  @Override
  public String format(String input) {
    try {
      return formatter.formatSource(input);
    } catch (FormatterException e) {
      throw new UncheckedException(e);
    }
  }
}
