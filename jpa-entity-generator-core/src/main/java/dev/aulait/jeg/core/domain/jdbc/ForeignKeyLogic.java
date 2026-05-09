package dev.aulait.jeg.core.domain.jdbc;

import java.util.List;
import org.apache.commons.collections4.CollectionUtils;

/**
 * The ForeignKeyLogic class provides methods to determine the relationship between tables based on
 * foreign key constraints. It includes methods to check if the relationship is one-to-one,
 * many-to-one, parent-child, and if the foreign key is part of a relation table.
 */
public class ForeignKeyLogic {

  /**
   * Determines if the given foreign key represents a one-to-one relationship. Returns {@code true}
   * if all of the following condition is met.
   *
   * <ul>
   *   <li>The columns that make up the given foreign key also form the primary key of the table to
   *       which they belong.
   * </ul>
   *
   * Ex.)
   *
   * <pre>{@code
   * CREATE TABLE to_one (
   *   id PRIMARY KEY
   * );
   *
   *
   * CREATE TABLE one (
   *   to_one_id PRIMARY KEY,
   *   CONSTRAINT fk FOREIGN KEY (to_one_id) REFERENCES to_one(id)
   * );
   * }</pre>
   *
   * @param fk the foreign key model to check
   * @return {@code true} if the foreign key represents a one-to-one relationship, false otherwise
   */
  public boolean isOneToOne(ForeignKeyModel fk) {
    List<ColumnModel> fkColumns = fk.getKeys().stream().map(KeyModel::getFkColumn).toList();
    return CollectionUtils.isEqualCollection(fk.getFkTable().getPkColumns(), fkColumns);
  }

  /**
   * Determines if the given foreign key represents a many-to-one relationship.Returns {@code true}
   * if all of the following condition is met.
   *
   * <ul>
   *   <li>The given foreign key is not in the relation table.
   *   <li>The columns that make up the given foreign key do not match all the primary key columns
   *       of the table to which they belong.
   * </ul>
   *
   * Ex.)
   *
   * <pre>{@code
   * CREATE TABLE to_one (
   *   id PRIMARY KEY
   * );
   *
   *
   * CREATE TABLE many (
   *   id PRIMARY KEY,
   *   to_one_id,
   *   CONSTRAINT fk FOREIGN KEY (to_one_id) REFERENCES to_one(id)
   * );
   * }</pre>
   *
   * @param fk the foreign key model to check
   * @return {@code true} if the foreign key represents a many-to-one relationship, {@code false}
   *     otherwise
   * @see #isInRelationTable(ForeignKeyModel)
   */
  public boolean isManyToOne(ForeignKeyModel fk) {
    if (isInRelationTable(fk)) {
      return false;
    }

    List<ColumnModel> fkColumns = fk.getKeys().stream().map(KeyModel::getFkColumn).toList();
    return !CollectionUtils.isEqualCollection(fkColumns, fk.getFkTable().getPkColumns());
  }

  /**
   * Determines if the given foreign key represents a parent-child relationship.Returns {@code true}
   * if all of the following condition is met.
   *
   * <ul>
   *   <li>The given foreign key is not in the relation table.
   *   <li>The number of columns that make up the given foreign key is less than the number of
   *       primary key columns in the table to which they belong.
   *   <li>The columns that make up the given foreign key are the part of the primary key columns in
   *       the table to which they belong.
   * </ul>
   *
   * Ex.)
   *
   * <pre>{@code
   * CREATE TABLE parent (
   *   id PRIMARY KEY
   * );
   *
   *
   * CREATE TABLE child (
   *   parent_id,
   *   seq_no,
   *   CONSTRAINT child_pk PRIMARY KEY (parent_id, seq_no),
   *   CONSTRAINT fk FOREIGN KEY (parent_id) REFERENCES parent(id)
   * );
   * }</pre>
   *
   * @param fk the foreign key model to check
   * @return {@code true} if the foreign key represents a parent-child relationship, {@code false}
   *     otherwise
   * @see #isInRelationTable(ForeignKeyModel)
   */
  public boolean isParentChild(ForeignKeyModel fk) {
    if (isInRelationTable(fk)) {
      return false;
    }

    List<ColumnModel> childFkCols = fk.getKeys().stream().map(KeyModel::getFkColumn).toList();
    List<ColumnModel> pkCols = fk.getFkTable().getPkColumns();

    if (!(pkCols.size() > childFkCols.size() && pkCols.containsAll(childFkCols))) {
      return false;
    }

    // When all PK columns of the table are FK columns (bridge-like table with extra non-PK
    // columns), only the FK containing the first PK column generates a OneToMany relationship.
    // The other FKs in such a table generate ManyToOne relationships instead.
    List<ColumnModel> allFkCols =
        fk.getFkTable().getForeignKeys().stream()
            .flatMap(f -> f.getKeys().stream().map(KeyModel::getFkColumn))
            .toList();
    List<ColumnModel> remainingPkCols =
        pkCols.stream().filter(c -> !childFkCols.contains(c)).toList();

    if (allFkCols.containsAll(remainingPkCols)) {
      return isFirstInPk(fk);
    }

    return true;
  }

  /**
   * Checks if the given foreign key is a bridge FK. A bridge FK is one where the FK table's entire
   * primary key is composed of foreign key columns (possibly with extra non-PK columns), and this
   * specific FK's columns are part of that PK. Such a FK should generate a ManyToOne relationship
   * on the bridge entity.
   *
   * @param fk the foreign key model to check
   * @return true if this is a bridge FK, false otherwise
   */
  public boolean isBridgeFk(ForeignKeyModel fk) {
    List<ColumnModel> thisFkCols = fk.getKeys().stream().map(KeyModel::getFkColumn).toList();
    List<ColumnModel> pkCols = fk.getFkTable().getPkColumns();

    if (!pkCols.containsAll(thisFkCols)) {
      return false;
    }

    List<ColumnModel> allFkCols =
        fk.getFkTable().getForeignKeys().stream()
            .flatMap(f -> f.getKeys().stream().map(KeyModel::getFkColumn))
            .toList();

    return allFkCols.containsAll(pkCols);
  }

  /**
   * Checks if the given foreign key model has a column that is the first in the primary key
   * sequence.
   *
   * @param fk the foreign key model to check
   * @return true if there is a column in the foreign key model that is the first in the primary key
   *     sequence, false otherwise
   */
  public boolean isFirstInPk(ForeignKeyModel fk) {
    return fk.getKeys().stream()
        .map(KeyModel::getFkColumn)
        .anyMatch(col -> Integer.valueOf(1).equals(col.getKEY_SEQ()));
  }

  /**
   * Checks if the given foreign key is in a relation table.
   *
   * @param fk the foreign key model to check
   * @return true if the foreign key is in a relation table, false otherwise
   * @see #isRelationTable(TableModel)
   */
  public boolean isInRelationTable(ForeignKeyModel fk) {
    return isRelationTable(fk.getFkTable());
  }

  /**
   * Determines if the given table is a relation table. A relation table is identified by having
   * foreign key columns that match the primary key columns, and the number of primary key columns
   * is equal to the total number of columns in the table.
   *
   * <p>Ex.)
   *
   * <pre>{@code
   * CREATE TABLE left (
   *   id PRIMARY KEY
   * );
   *
   *
   * CREATE TABLE right (
   *   id PRIMARY KEY
   * );
   *
   *
   * CREATE TABLE relation (
   *   left_id REFEERNCES left,
   *   right_id REFERENCES right,
   *   CONSTRAINT relation_pk PRIMARY KEY (left_id, right_id)
   * );
   * }</pre>
   *
   * @param table the table model to check
   * @return true if the table is a relation table, false otherwise
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
