package dev.aulait.jeg.core.domain.jdbc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.Data;
import lombok.Getter;

@Data
public class DatabaseMetaDataModel {

  private List<TableModel> tables = new ArrayList<>();

  /** key: TableModel.TABLE_NAME */
  @Getter(lazy = true)
  private final Map<String, TableModel> tableMap =
      tables.stream().collect(Collectors.toMap(TableModel::getTABLE_NAME, t -> t));

  /** key: ColumnModel.TABLE_NAME */
  private Map<String, List<ColumnModel>> columnMap = new HashMap<>();

  /** key: ColumnModel.TABLE_NAME */
  private Map<String, List<ColumnModel>> primaryKeyMap = new HashMap<>();

  /** key: KeyModel.PKTABLE_NAME */
  private Map<String, List<KeyModel>> exportedKeyMap = new HashMap<>();

  private List<KeyModel> exportedKeys = new ArrayList<>();

  /** key: KeyModel.PKTABLE_NAME */
  // private Map<String, List<KeyModel>> importedKeyMap = new HashMap<>();

  public List<ColumnModel> getColumns(String tableName) {
    return columnMap.get(tableName);
  }

  public boolean isPk(ColumnModel column) {
    List<ColumnModel> columns = primaryKeyMap.get(column.getTABLE_NAME());

    if (columns == null) {
      return false;
    }

    return columns.contains(column);
  }

  public List<KeyModel> getExportedKeys(String tableName) {
    return exportedKeyMap.get(tableName);
  }

  public TableModel getTable(String tableName) {
    return getTableMap().get(tableName);
  }
}
