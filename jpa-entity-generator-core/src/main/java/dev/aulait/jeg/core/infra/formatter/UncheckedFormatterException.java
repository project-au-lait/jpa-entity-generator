package dev.aulait.jeg.core.infra.formatter;

public class UncheckedFormatterException extends RuntimeException {

  public UncheckedFormatterException(String formattingText, Throwable e) {
    super("Formatting has faild for the text:" + System.lineSeparator() + formattingText, e);
  }
}
