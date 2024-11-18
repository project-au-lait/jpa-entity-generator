package dev.aulait.jeg.core.infra.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Path;

public class JsonUtils {

  private static final ObjectMapper mapper = new ObjectMapper();

  private static final ObjectWriter writer = mapper.writerWithDefaultPrettyPrinter();

  public static void writeValue(Path file, Object value) {
    try {
      writer.writeValue(file.toFile(), value);
    } catch (IOException e) {
      throw new UncheckedIOException(e);
    }
  }

  public static String writeValueAsString(Object value) {
    try {
      return writer.writeValueAsString(value);
    } catch (IOException e) {
      throw new UncheckedIOException(e);
    }
  }

  public static <T> T readValue(Path file, TypeReference<T> valueType) {
    try {
      return mapper.readValue(file.toFile(), valueType);
    } catch (IOException e) {
      throw new UncheckedIOException(e);
    }
  }

  public static <T> T readValue(String jsonStr, Class<T> type) {
    try {
      return mapper.readValue(jsonStr, type);
    } catch (JsonProcessingException e) {
      throw new UncheckedIOException(e);
    }
  }

  public static String format(String jsonStr) {
    try {
      return writer.writeValueAsString(mapper.readValue(jsonStr, Object.class));
    } catch (IOException e) {
      throw new UncheckedIOException(e);
    }
  }
}
