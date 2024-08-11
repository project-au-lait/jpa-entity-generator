package dev.aulait.jeg.core.domain.jdbc;

import dev.aulait.jeg.core.infra.config.Config;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
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

      databaseMetaData.setTables(extractTables(meta));

      databaseMetaData.setColumnMap(extractColumns(meta));

      databaseMetaData.setPrimaryKeyMap(extractPrimaryKeys(meta));

      databaseMetaData.setExportedKeys(extractExportedKeys(meta));

      // databaseMetaData.setExportedKeyMap(extractExportedKeys(meta));

      // databaseMetaData.setImportedKeyMap(extractImportedKeys(meta));

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
      List<ColumnModel> columns = MetaDataUtils.extract(rs, ColumnModel.class);

      log.info("Extracted {} columns", columns.size());

      return columns.stream().collect(Collectors.groupingBy(ColumnModel::getTABLE_NAME));
    }
  }

  Map<String, List<ColumnModel>> extractPrimaryKeys(DatabaseMetaData meta) throws SQLException {
    try (ResultSet rs = meta.getPrimaryKeys(config.getCatalog(), config.getSchemaPattern(), null)) {
      return MetaDataUtils.extract(rs, ColumnModel.class).stream()
          .collect(Collectors.groupingBy(ColumnModel::getTABLE_NAME));
    }
  }

  // Map<String, List<KeyModel>> extractExportedKeys(DatabaseMetaData meta) throws SQLException {
  //   try (ResultSet rs = meta.getExportedKeys(null, null, null)) {

  //     List<KeyModel> keys = MetaDataUtils.extract(rs, KeyModel.class);

  //     log.info("Extracted exported {} keys", keys.size());

  //     return keys.stream().collect(Collectors.groupingBy(KeyModel::getPKTABLE_NAME));
  //   }
  // }

  List<KeyModel> extractExportedKeys(DatabaseMetaData meta) throws SQLException {
    try (ResultSet rs =
        meta.getExportedKeys(config.getCatalog(), config.getSchemaPattern(), null)) {

      List<KeyModel> keys = MetaDataUtils.extract(rs, KeyModel.class);

      log.info("Extracted exported {} keys", keys.size());

      return keys;
    }
  }

  // Map<String, List<KeyModel>> extractImportedKeys(DatabaseMetaData meta) throws SQLException {
  //   try (ResultSet rs = meta.getImportedKeys(null, null, null)) {

  //     List<KeyModel> keys = MetaDataUtils.extract(rs, KeyModel.class);

  //     log.info("Extracted imported {} keys", keys.size());

  //     return keys.stream().collect(Collectors.groupingBy(KeyModel::getPKTABLE_NAME));
  //   }
  // }
}
