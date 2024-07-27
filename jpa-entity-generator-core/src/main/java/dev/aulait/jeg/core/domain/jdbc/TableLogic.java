package dev.aulait.jeg.core.domain.jdbc;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import org.apache.commons.collections4.CollectionUtils;

public class TableLogic {

  /**
   * Determines that relTable is a related table of oneTable. True if all of the following
   * conditions are met.
   *
   * <ul>
   *   <li>All columns in relTable are primary keys.
   *   <li>There are two tables with relTable foreign key references.
   *   <li>The primary key of relTable consists only of columns that refer to the primary keys of
   *       those two tables.
   * </ul>
   *
   * Example: if oneTable and relTable are defined in the following DDL, then relTable is a related
   * table to oneTable.
   *
   * <pre>{@code
   * oneTable (
   *   id PK,
   *   :
   * );
   *
   *
   * relTable (
   *   oneId REFERENCES oneTable,
   *   anotherId REFERENCES anotherTable,
   *   :
   *   CONSTRAINT relTablePk PRIMARY KEY (oneId, anotherId)
   * );
   * }</pre>
   *
   * @param table Tables to be determined
   * @param relTable Tables to be determined
   * @return True if relTable is a related table to table
   */
  public boolean isRelationTable(TableModel table, TableModel relTable) {
    if (!relTable.isRelationTable()) {
      return false;
    }

    if (relTable.getReferencingTableMap().size() != 2) {
      return false;
    }

    TableModel rightTable =
        relTable.getReferencingTableMap().keySet().stream()
            .filter(Predicate.not(table::equals))
            .findFirst()
            .get();

    List<ColumnModel> referencedColumns = new ArrayList<>();
    referencedColumns.addAll(table.getPkColumns());
    referencedColumns.addAll(rightTable.getPkColumns());

    List<ColumnModel> referencingColumns =
        relTable.getReferencingKeys().stream().map(KeyModel::getPkColumn).toList();

    return CollectionUtils.isEqualCollection(referencedColumns, referencingColumns);
  }

  /**
   * Determines that oneTable and manyTable are in a 1:N relationship. True if all of the following
   * conditions are met.
   *
   * <ul>
   *   <li>manyTable is not a related table.
   *   <li>manyTable has a foreign key that refers to oneTable.
   *   <li>The columns that make up the primary key of manyTable and the columns that make up the
   *       foreign key to which manyTable refers to oneTable are different.
   * </ul>
   *
   * Example: If oneTable and manyTable are defined in the following DDL, then oneTable and
   * manyTable have a 1:N relationship.
   *
   * <pre>{@code
   * oneTable (
   *   id PK,
   *   :
   * );
   *
   *
   * manyTable (
   *   id PK,
   *   oneTableId REFERENCES oneTable,
   *   :
   * );
   * }</pre>
   *
   * @param oneTable Tables to be determined
   * @param manyTable Tables to be determined
   * @return True if oneTable and manyTable are in a 1:N relationship
   */
  public boolean isOneToMany(TableModel oneTable, TableModel manyTable) {
    if (manyTable.isRelationTable()) {
      return false;
    }

    List<KeyModel> referencingKeys = manyTable.getReferencingTableMap().get(oneTable);

    if (referencingKeys == null) {
      return false;
    }

    List<ColumnModel> fkColumns = referencingKeys.stream().map(KeyModel::getFkColumn).toList();
    return !CollectionUtils.isEqualCollection(fkColumns, manyTable.getPkColumns());
  }

  /**
   * Determine that manyTable and oneTable are in an N:1 relationship.
   *
   * @param manyTable Tables to be determined
   * @param oneTable Tables to be determined
   * @return True if manyTable and oneTable have an N:1 relationship
   */
  public boolean isManyToOne(TableModel manyTable, TableModel oneTable) {
    if (oneTable.isRelationTable()) {
      return false;
    }

    List<KeyModel> referencedKeys = oneTable.getReferencedTableMap().get(manyTable);

    if (referencedKeys == null) {
      return false;
    }

    List<ColumnModel> fkColumns = referencedKeys.stream().map(KeyModel::getFkColumn).toList();
    return !CollectionUtils.isEqualCollection(fkColumns, manyTable.getPkColumns());
  }

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
   * 例)
   *
   * <pre>{@code
   * oneTable (
   *   id PK,
   *   :
   * );
   *
   *
   * anotherTable (
   *   id PK REFERENCES oneTable,
   *   :
   * );
   * }</pre>
   *
   * @param oneTable Tables to be determined
   * @param anotherTable Tables to be determined
   * @return True if oneTable and anotherTable are in a 1:1 relationship
   */
  public boolean isOneToOne(TableModel oneTable, TableModel anotherTable) {
    List<KeyModel> referencingKeys = anotherTable.getReferencingTableMap().get(oneTable);

    if (referencingKeys == null) {
      return false;
    }

    List<ColumnModel> fkColumns = referencingKeys.stream().map(KeyModel::getFkColumn).toList();
    return CollectionUtils.isEqualCollection(fkColumns, anotherTable.getPkColumns());
  }

  /**
   * Determines that parentTable and childTable are in a parent-child relationship. True if all of
   * the following conditions are met.
   *
   * <ul>
   *   <li>The chlidTable has a foreign key that refers to the parentTable.
   *   <li>The columns that make up the primary key of the parentTable match the columns that make
   *       up the foreign key to which the otherTable refers to the oneTable.
   * </ul>
   *
   * 例)
   *
   * <pre>{@code
   * parentTable (
   *   id PK,
   *   :
   * );
   *
   *
   * childTable (
   *   id PK REFERENCES parentTable,
   *   seq_no INT,
   *   :
   *   CONSTRAINT childTablePk PRIMARY KEY (id, seq_no)
   * );
   * }</pre>
   *
   * @param parentTable Tables to be determined
   * @param childTable Tables to be determined
   * @return True if oneTable and anotherTable are in a 1:1 relationship
   */
  public boolean isParentChild(TableModel parentTable, TableModel childTable) {
    if (childTable.isRelationTable()) {
      return false;
    }

    List<KeyModel> childRefKeys = childTable.getReferencingTableMap().get(parentTable);

    if (childRefKeys == null) {
      return false;
    }

    List<ColumnModel> childFkCols = childRefKeys.stream().map(KeyModel::getFkColumn).toList();

    return childTable.getPkColumns().size() > childFkCols.size()
        && childTable.getPkColumns().containsAll(childFkCols);
  }

  /**
   * Determine that relTable is a related table of oneTable and that oneTable is the first table in
   * the relationship.
   *
   * <p>True if all of the following conditions are met.
   *
   * <ul>
   *   <li>{@link #isRelationTable(TableModel, TableModel)} is true.
   *   <li>Of the columns that make up the primary key of a relTable, those defined first on the DDL
   *       refer to the oneTable.
   * </ul>
   *
   * Example: if oneTable and relTable are defined in the following DDL, oneTable is the first table
   * in relation to relTable.
   *
   * <pre>{@code
   * oneTable (
   *   id PK,
   *   :
   * );
   *
   *
   * relTable (
   *   oneId REFERENCES oneTable,
   *   anotherId REFERENCES anotherTable,
   *   :
   *   CONSTRAINT relTablePk PRIMARY KEY (oneId, anotherId)
   * );
   * }</pre>
   *
   * @param oneTable Tables to be determined
   * @param relTable Tables to be determined
   * @return true if relTable is a related table to table 
   *         and table is the first table in the relationship
   * @see #isRelationTable(TableModel, TableModel)
   */
  public boolean isFirstTableInRelation(TableModel oneTable, TableModel relTable) {

    if (!isRelationTable(oneTable, relTable)) {
      return false;
    }

    ColumnModel firstPkColumn = relTable.getPkColumns().get(0);

    for (KeyModel key : relTable.getReferencingTableMap().get(oneTable)) {
      if (key.getPKTABLE_NAME().equals(oneTable.getTABLE_NAME())
          && key.getFKCOLUMN_NAME().equals(firstPkColumn.getCOLUMN_NAME())) {
        return true;
      }
    }

    return false;
  }
}
