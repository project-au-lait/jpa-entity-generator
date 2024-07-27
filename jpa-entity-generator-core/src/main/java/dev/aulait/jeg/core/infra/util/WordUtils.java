package dev.aulait.jeg.core.infra.util;

import com.google.common.base.CaseFormat;
import org.apache.commons.lang3.StringUtils;

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
    return simplePluralize(entityNameToFieldName(entityName, entitySuffix));
  }

  /**
   * Reference:
   * https://github.com/hibernate/hibernate-tools/blob/5.6/main/src/main/java/org/hibernate/cfg/reveng/ReverseEngineeringStrategyUtil.java#L129
   *
   * @param singular
   * @return
   */
  public static String simplePluralize(String singular) {
    char last = singular.charAt(singular.length() - 1);
    Character prev = singular.length() > 1 ? singular.charAt(singular.length() - 2) : null;
    String vowels = "aeiouy";
    switch (last) {
      case 'x':
      case 's':
        singular += "es";
        break;
      case 'y':
        if (prev != null && vowels.indexOf(prev) >= 0) {
          singular += "s";
        } else {
          singular = singular.substring(0, singular.length() - 1) + "ies";
        }
        break;
      case 'h':
        if (prev != null && (prev == 'c' || prev == 's')) {
          singular += "es";
          break;
        }
      default:
        singular += "s";
    }
    return singular;
  }
}
