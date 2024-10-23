package dev.aulait.jeg.core.domain.jdbc;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DatabaseMetaDataProcessor {

  public List<TableModel> process(DatabaseMetaDataModel meta) {
    for (TableModel table : meta.getTables()) {
      processTable(table, meta);
    }
    // processKeys(meta);
    processFks(meta);

    return meta.getTables().stream().toList();
  }

  /**
   * @param table
   * @param meta
   */
  void processTable(TableModel table, DatabaseMetaDataModel meta) {

    for (ColumnModel column : meta.getColumns(table.getTABLE_NAME())) {
      column.setTable(table);
      table.getColumns().add(column);
    }

    for (ColumnModel pk : meta.getPrimaryKeyMap().get(table.getTABLE_NAME())) {
      table.findColumnByName(pk.getCOLUMN_NAME()).setKEY_SEQ(pk.getKEY_SEQ());
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

  void processFks(DatabaseMetaDataModel meta) {
    Map<String, ForeignKeyModel> fkMap = new HashMap<>();

    for (KeyModel key : meta.getExportedKeys()) {

      TableModel pkTable = meta.getTable(key.getPKTABLE_NAME());
      key.setPkTable(pkTable);
      key.setPkColumn(pkTable.findColumnByName(key.getPKCOLUMN_NAME()));

      TableModel fkTable = meta.getTable(key.getFKTABLE_NAME());
      key.setFkTable(fkTable);
      key.setFkColumn(fkTable.findColumnByName(key.getFKCOLUMN_NAME()));

      ForeignKeyModel fk = fkMap.computeIfAbsent(key.getFK_NAME(), ForeignKeyModel::new);
      fk.getKeys().add(key);
      fkTable.getForeignKeys().add(fk);
    }
  }
}
