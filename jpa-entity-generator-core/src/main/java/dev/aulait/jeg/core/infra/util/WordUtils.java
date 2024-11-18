package dev.aulait.jeg.core.infra.util;

import com.google.common.base.CaseFormat;
import org.apache.commons.lang3.StringUtils;
import org.atteo.evo.inflector.English;

public class WordUtils {

  public static String snakeToUpperCamel(String str) {
    return CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, str);
  }

  public static String snakeToLowerCamel(String str) {
    return CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, str);
  }

  public static String entityNameToFieldName(String entityName, String entitySuffix) {
    return StringUtils.uncapitalize(entityName.replace(entitySuffix, ""));
  }

  public static String entityNameToPluralFieldName(String entityName, String entitySuffix) {
    return English.plural(entityNameToFieldName(entityName, entitySuffix));
  }
}
