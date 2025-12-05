package dev.aulait.jeg.core.domain.jdbc;

import static org.junit.jupiter.api.Assertions.assertEquals;

import dev.aulait.jeg.core.infra.config.ConfigLoader;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

public class ForeignKeyLogicTests {
  DatabaseMetaDataProcessor processor = new DatabaseMetaDataProcessor();
  ForeignKeyLogic logic = new ForeignKeyLogic();

  /**
   * Tests the relationship logic for foreign keys using parameterized inputs from a CSV file.
   *
   * @param foreignKeyName the name of the foreign key to test
   * @param oneToOneExpected expected result for one-to-one relationship check
   * @param manyToOneExpected expected result for many-to-one relationship check
   * @param parentChildExpected expected result for parent-child relationship check
   * @param firstInPkExpected expected result for first-in-primary-key check
   * @param inRelationTableExpected expected result for in-relation-table check
   */
  @ParameterizedTest
  @CsvFileSource(resources = "ForeignKeyLogicTests.csv", numLinesToSkip = 1)
  void testRelation(
      String foreignKeyName,
      boolean parentChildExpected,
      boolean firstInPkExpected,
      boolean inRelationTableExpected) {

    DatabaseMetaDataReader reader = new DatabaseMetaDataReader(ConfigLoader.load());
    DatabaseMetaDataModel meta = reader.read();
    processor.process(meta);

    ForeignKeyModel fk = findByName(meta.getTables(), foreignKeyName);

    assertEquals(
        parentChildExpected, logic.isParentChild(fk), "isParentChild(" + fk.getName() + ")");
    assertEquals(firstInPkExpected, logic.isFirstInPk(fk), "isFirstInPk(" + fk.getName() + ")");
    assertEquals(
        inRelationTableExpected,
        logic.isInRelationTable(fk),
        "isInRelationTable(" + fk.getName() + ")");
  }

  ForeignKeyModel findByName(List<TableModel> tables, String fkName) {
    return tables.stream()
        .map(TableModel::getForeignKeys)
        .flatMap(Set::stream)
        .filter(fk -> fkName.equals(fk.getName()))
        .findFirst()
        .orElseThrow(() -> new IllegalArgumentException(fkName));
  }
}
