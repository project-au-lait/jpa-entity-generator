package dev.aulait.jeg.core.infra.util;

import java.lang.reflect.Type;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ModelUtils {

  private static ModelMapper mapper = new ModelMapper();

  static {
    mapper.getConfiguration().setPropertyCondition(Conditions.isNotNull());
  }

  public static <T> T map(Object source, Class<T> type) {
    return mapper.map(source, type);
  }

  public static <T> T map(Object source, Type type) {
    return mapper.map(source, type);
  }

  public static void map(Object source, Object destination) {
    mapper.map(source, destination);
  }
}
