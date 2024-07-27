package dev.aulait.jeg.core.infra.util;

import java.lang.reflect.Type;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ModelUtils {

  private static ModelMapper mapper = new ModelMapper();

  public static <T> T map(Object source, Class<T> type) {
    return mapper.map(source, type);
  }

  public static <T> T map(Object source, Type type) {
    return mapper.map(source, type);
  }
}
