package dev.aulait.jeg.core.domain.jdbc;

import dev.aulait.jeg.core.infra.config.Config;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class DatabaseMetaDataReader {

  private final Config config;

  public DatabaseMetaDataModel read() {
    DatabaseMetaDataModel databaseMetaData = new DatabaseMetaDataModel();

    log.info(
        "Extracting dtabase metadata using jdbc url: {}, username: {}",
        config.getRuntime().getJdbcUrl(),
        config.getRuntime().getJdbcUsername());

    try (Connection conn =
        DriverManager.getConnection(
            config.getRuntime().getJdbcUrl(),
            config.getRuntime().getJdbcUsername(),
            config.getRuntime().getJdbcPassword())) {

      DatabaseMetaData meta = conn.getMetaData();

      List<TableModel> tables = extractTables(meta);

      databaseMetaData.setColumnMap(toLowerCase(extractColumns(meta)));

      databaseMetaData.setPrimaryKeyMap(toLowerCase(extractPrimaryKeys(meta, tables)));

      databaseMetaData.setExportedKeys(
          extractExportedKeys(meta, tables).stream().peek(this::toLowerCase).toList());

      databaseMetaData.setTables(tables.stream().peek(this::toLowerCase).toList());

    } catch (SQLException e) {
      throw new IllegalArgumentException(e);
    }

    return databaseMetaData;
  }

  List<TableModel> extractTables(DatabaseMetaData meta) throws SQLException {
    log.info(
        "Extracting tables from database metadata using catalog: {}, schemaPattern:{},"
            + " tableNamePattern: {}",
        config.getCatalog(),
        config.getSchemaPattern(),
        config.getTableNamePattern());

    try (ResultSet rs =
        meta.getTables(
            config.getCatalog(),
            config.getSchemaPattern(),
            config.getTableNamePattern(),
            new String[] {"TABLE"})) {
      List<TableModel> tables =
          MetaDataUtils.extract(rs, TableModel.class).stream()
              .filter(tbs -> !config.getExcludedTables().contains(tbs.getTABLE_NAME()))
              .toList();

      log.info("Extracted {} tables", tables.size());

      return tables;
    }
  }

  Map<String, List<ColumnModel>> extractColumns(DatabaseMetaData meta) throws SQLException {
    try (ResultSet rs =
        meta.getColumns(config.getCatalog(), config.getSchemaPattern(), null, null)) {
      List<ColumnModel> columns = MetaDataUtils.extract(rs, ColumnModel.class).stream().toList();

      log.info("Extracted {} columns", columns.size());

      return columns.stream().collect(Collectors.groupingBy(ColumnModel::getTABLE_NAME));
    }
  }

  Map<String, List<ColumnModel>> extractPrimaryKeys(DatabaseMetaData meta, List<TableModel> tables)
      throws SQLException {

    // key: table name, value: list of primary keys
    Map<String, List<ColumnModel>> primaryKeyMap = new HashMap<>();

    for (TableModel table : tables) {
      try (ResultSet rs =
          meta.getPrimaryKeys(
              config.getCatalog(), config.getSchemaPattern(), table.getTABLE_NAME())) {
        List<ColumnModel> primaryKeys =
            MetaDataUtils.extract(rs, ColumnModel.class).stream().toList();

        log.info(
            "Extracted {} primary keys for table {}", primaryKeys.size(), table.getTABLE_NAME());

        primaryKeyMap.put(table.getTABLE_NAME(), primaryKeys);
      }
    }

    return primaryKeyMap;
  }

  List<KeyModel> extractExportedKeys(DatabaseMetaData meta, List<TableModel> tables)
      throws SQLException {

    List<KeyModel> keys = new ArrayList<>();

    for (TableModel table : tables) {
      try (ResultSet rs =
          meta.getExportedKeys(
              config.getCatalog(), config.getSchemaPattern(), table.getTABLE_NAME())) {

        List<KeyModel> extractedKeys = MetaDataUtils.extract(rs, KeyModel.class).stream().toList();

        log.info(
            "Extracted exported {} keys for table {}", extractedKeys.size(), table.getTABLE_NAME());

        keys.addAll(extractedKeys);
      }
    }
    return keys;
  }

  Map<String, List<ColumnModel>> toLowerCase(Map<String, List<ColumnModel>> columnMap) {
    return columnMap.entrySet().stream()
        .collect(
            Collectors.toMap(
                e -> e.getKey().toLowerCase(),
                e -> e.getValue().stream().map(this::toLowerCase).toList()));
  }

  TableModel toLowerCase(TableModel table) {
    table.setTABLE_NAME(table.getTABLE_NAME().toLowerCase());
    return table;
  }

  ColumnModel toLowerCase(ColumnModel column) {
    column.setTABLE_NAME(column.getTABLE_NAME().toLowerCase());
    column.setCOLUMN_NAME(column.getCOLUMN_NAME().toLowerCase());
    return column;
  }

  KeyModel toLowerCase(KeyModel key) {
    key.setFK_NAME(key.getFK_NAME().toLowerCase());
    key.setPKTABLE_NAME(key.getPKTABLE_NAME().toLowerCase());
    key.setPKCOLUMN_NAME(key.getPKCOLUMN_NAME().toLowerCase());
    key.setFKTABLE_NAME(key.getFKTABLE_NAME().toLowerCase());
    key.setFKCOLUMN_NAME(key.getFKCOLUMN_NAME().toLowerCase());
    return key;
  }
}
