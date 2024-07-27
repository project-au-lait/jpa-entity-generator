package dev.aulait.jeg.core.domain.jdbc;

import java.util.List;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DatabaseMetaDataProcessor {

  public List<TableModel> process(DatabaseMetaDataModel meta) {
    for (TableModel table : meta.getTables()) {
      processTable(table, meta);
    }
    processKeys(meta);

    return meta.getTables().stream().toList();
  }

  /**
   * @param table
   * @param meta
   */
  void processTable(TableModel table, DatabaseMetaDataModel meta) {

    table.setColumns(meta.getColumns(table.getTABLE_NAME()));

    for (ColumnModel column : table.getColumns()) {
      column.setTable(table);
      column.setPk(meta.isPk(column));
    }
  }

  /**
   * @param meta
   */
  void processKeys(DatabaseMetaDataModel meta) {
    for (KeyModel key : meta.getExportedKeys()) {
      TableModel pkTable = meta.getTable(key.getPKTABLE_NAME());
      pkTable.getReferencedKeys().add(key);
      key.setPkTable(pkTable);
      key.setPkColumn(pkTable.findColumnByName(key.getPKCOLUMN_NAME()));

      TableModel fkTable = meta.getTable(key.getFKTABLE_NAME());
      fkTable.getReferencingKeys().add(key);
      key.setFkTable(fkTable);
      key.setFkColumn(fkTable.findColumnByName(key.getFKCOLUMN_NAME()));
    }
  }
}
