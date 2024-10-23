package dev.aulait.jeg.core.domain.jdbc;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class TableModel {
  @EqualsAndHashCode.Include private String TABLE_CAT;
  @EqualsAndHashCode.Include private String TABLE_SCHEM;
  @EqualsAndHashCode.Include private String TABLE_NAME;

  private String TABLE_TYPE;
  private List<ColumnModel> columns = new ArrayList<>();

  @Getter(lazy = true)
  private final Map<String, ColumnModel> columnMap =
      columns.stream().collect(Collectors.toMap(ColumnModel::getCOLUMN_NAME, v -> v));

  @Getter(lazy = true)
  private final boolean compositeKey = columns.stream().filter(ColumnModel::isPk).count() > 1;

  /** The foreign key to which this table will refer. */
  private List<KeyModel> referencedKeys = new ArrayList<>();

  /** The foreign key to which this table refers. */
  private List<KeyModel> referencingKeys = new ArrayList<>();

  private Set<ForeignKeyModel> foreignKeys = new HashSet<>();

  /** */
  @Getter(lazy = true)
  private final Map<TableModel, List<KeyModel>> referencedTableMap =
      referencedKeys.stream().collect(Collectors.groupingBy(KeyModel::getFkTable));

  @Getter(lazy = true)
  private final Map<TableModel, List<KeyModel>> referencingTableMap =
      referencingKeys.stream().collect(Collectors.groupingBy(KeyModel::getPkTable));

  @Getter(lazy = true)
  private final List<ColumnModel> pkColumns = columns.stream().filter(ColumnModel::isPk).toList();

  @Getter(lazy = true)
  private final boolean relationTable = getPkColumns().equals(columns);

  public ColumnModel findColumnByName(String columnName) {
    return getColumnMap().get(columnName);
  }
}
