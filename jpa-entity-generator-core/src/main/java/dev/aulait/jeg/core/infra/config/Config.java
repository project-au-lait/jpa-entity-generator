package dev.aulait.jeg.core.infra.config;

import dev.aulait.jeg.core.domain.jpa.CascadeType;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Optional;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Data
@Slf4j
public class Config {

  private RuntimeConfig runtime = new RuntimeConfig();
  private String catalog;
  private String schemaPattern;
  private String tableNamePattern;
  private Map<String, List<String>> packages = new HashMap<>();
  private List<BaseClassDef> baseClassDefs = new ArrayList<>();
  private String entitySuffix = "Entity";
  private List<String> excludedTables = new ArrayList<>();
  private List<String> excludedColmuns = new ArrayList<>();
  private Map<String, AnnotationDef> annotationDefs = new HashMap<>();
  private String formatter;

  /** key: table_name, value: Map {key: table_name.column_name, value : [CascadeType]} */
  private Map<String, Map<String, List<String>>> cascades = new HashMap<>();

  /** key: table_name.column_name, value: [annotationName] */
  private Map<String, List<String>> annotatedCols = new HashMap<>();

  @Getter(lazy = true, value = AccessLevel.PRIVATE)
  private final Map<String, String> entityPackageMap = hoge();

  public Optional<String> findPackage(String table) {
    return Optional.ofNullable(getEntityPackageMap().get(table));
  }

  public Optional<String> findBaseClass(String table) {
    for (BaseClassDef def : baseClassDefs) {
      if (def.getTables().contains(table) || def.getTables().contains("*")) {
        return Optional.of(def.getBaseClass());
      }
    }

    return Optional.empty();
  }

  private Map<String, String> hoge() {
    Map<String, String> map = new HashMap<>();

    for (Entry<String, List<String>> pkg : packages.entrySet()) {
      for (String table : pkg.getValue()) {
        map.put(table, pkg.getKey());
      }
    }

    return map;
  }

  public List<AnnotationDef> findAnnotations(String tableOrEntityName, String columnOrFieldName) {
    List<String> annotationNames = annotatedCols.get(tableOrEntityName + "." + columnOrFieldName);

    if (annotationNames == null) {
      return Collections.emptyList();
    }

    return annotationNames.stream().map(annotationDefs::get).filter(Objects::nonNull).toList();
  }

  public List<CascadeType> findCascades(
      String cascadingTableName, String cascadedTableName, String columnName) {
    Map<String, List<String>> cascadeMap = cascades.get(cascadingTableName);

    if (cascadeMap == null) {
      log.trace("Cascading table : {} is not found in : {}", cascadingTableName, cascades.keySet());
      return Collections.emptyList();
    }

    String tableColumn = cascadedTableName + "." + columnName;
    List<String> cascades = cascadeMap.get(tableColumn);

    if (cascades == null) {
      log.trace("Cascaded table.column : {} is not found in : {}", tableColumn, cascadeMap);
      return Collections.emptyList();
    }

    return cascades.stream().map(CascadeType::valueOf).toList();
  }
}
