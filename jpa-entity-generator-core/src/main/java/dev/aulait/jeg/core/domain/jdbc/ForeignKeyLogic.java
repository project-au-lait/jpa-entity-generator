package dev.aulait.jeg.core.domain.jdbc;

import java.util.List;
import org.apache.commons.collections4.CollectionUtils;

public class ForeignKeyLogic {

  /**
   * Determines that oneTable and anotherTable are in a 1:1 relationship. True if all of the
   * following conditions are met.
   *
   * <ul>
   *   <li>anotherTable has a foreign key that refers to oneTable.
   *   <li>The columns that make up the primary key of anotherTable match the columns that make up
   *       the foreign key to which anotherTable refers to oneTable.
   * </ul>
   *
   * Ex.)
   *
   * <pre>{@code
   * fk.pkTable (
   *   id PK,
   *   :
   * );
   *
   *
   * fk.fkTable (
   *   id PK REFERENCES fk.pkTable,
   *   :
   * );
   * }</pre>
   *
   * @param fk Foreign key to be determined
   * @return True if oneTable and anotherTable are in a 1:1 relationship
   */
  public boolean isOneToOne(ForeignKeyModel fk) {
    List<ColumnModel> fkColumns = fk.getKeys().stream().map(KeyModel::getFkColumn).toList();
    return CollectionUtils.isEqualCollection(fk.getFkTable().getPkColumns(), fkColumns);
  }

  /**
   * Determines that fk.pkTable and fk.fkTable are in a N:1 relationship. True if all of the
   * following conditions are met.
   *
   * <ul>
   *   <li>The columns that make up the primary key of fk.fkTable match the columns that make up the
   *       foreign key to which anotherTable refers to oneTable.
   * </ul>
   *
   * Ex.)
   *
   * <pre>{@code
   * fk.pkTable (
   *   id PK,
   *   :
   * );
   *
   *
   * fk.fkTable (
   *   id PK,
   *   col REFERENCES fk.pkTable
   *   :
   * );
   * }</pre>
   *
   * @param fk Foreign key to be determined
   * @return True if oneTable and anotherTable are in a 1:1 relationship
   */
  public boolean isManyToOne(ForeignKeyModel fk) {
    if (isInRelationTable(fk)) {
      return false;
    }

    List<ColumnModel> fkColumns = fk.getKeys().stream().map(KeyModel::getFkColumn).toList();
    return !CollectionUtils.isEqualCollection(fkColumns, fk.getFkTable().getPkColumns());
  }

  /**
   * Determines that pk.pkTable and fk.fkTable are in a parent-child relationship. True if all of
   * the following conditions are met.
   *
   * <ul>
   *   <li>The columns that make up the primary key of the parentTable match the columns that make
   *       up the foreign key to which the otherTable refers to the oneTable.
   * </ul>
   *
   * Ex.)
   *
   * <pre>{@code
   * fk.pkTable (
   *   id PK,
   *   :
   * );
   *
   *
   * fk.fkTable (
   *   id PK REFERENCES fk.pkTable,
   *   seq_no INT,
   *   :
   *   CONSTRAINT childTablePk PRIMARY KEY (id, seq_no)
   * );
   * }</pre>
   *
   * @param fk
   * @return
   */
  public boolean isParentChild(ForeignKeyModel fk) {
    if (isInRelationTable(fk)) {
      return false;
    }

    List<ColumnModel> childFkCols = fk.getKeys().stream().map(KeyModel::getFkColumn).toList();

    return fk.getFkTable().getPkColumns().size() > childFkCols.size()
        && fk.getFkTable().getPkColumns().containsAll(childFkCols);
  }

  public boolean isFirstInPk(ForeignKeyModel fk) {
    return fk.getKeys().stream()
        .map(KeyModel::getFkColumn)
        .anyMatch(col -> Integer.valueOf(1).equals(col.getKEY_SEQ()));
  }

  public boolean isInRelationTable(ForeignKeyModel fk) {
    return isRelationTable(fk.getFkTable());
  }

  /**
   * Ex.)
   *
   * <pre>{@code
   * fk.pkTable (
   *   id PK,
   *   :
   * );
   *
   *
   * fk.fkTable (
   *   id_1 REFERENCES fk.pkTable,
   *   id_2 REFERENCES fk.pkTable,
   *   CONSTRAINT  PRIMARY KEY (id_1, id_2)
   * );
   *
   *
   * anotherTable (
   *   id PK,
   *   :
   * );
   *
   * }</pre>
   *
   * @param table
   * @return
   */
  public boolean isRelationTable(TableModel table) {
    List<String> fkColNames =
        table.getForeignKeys().stream()
            .map(ForeignKeyModel::getKeys)
            .flatMap(List::stream)
            .map(KeyModel::getFKCOLUMN_NAME)
            .toList();

    List<String> pkColNames =
        table.getPkColumns().stream().map(ColumnModel::getCOLUMN_NAME).toList();

    return CollectionUtils.isEqualCollection(fkColNames, pkColNames)
        && pkColNames.size() == table.getColumns().size();
  }
}
