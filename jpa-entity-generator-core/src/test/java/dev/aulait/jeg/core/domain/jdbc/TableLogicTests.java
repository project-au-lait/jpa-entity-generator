package dev.aulait.jeg.core.domain.jdbc;

import static org.junit.Assert.assertEquals;

import dev.aulait.jeg.core.infra.config.ConfigLoader;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

public class TableLogicTests {

  DatabaseMetaDataProcessor processor = new DatabaseMetaDataProcessor();
  TableLogic logic = new TableLogic();

  @ParameterizedTest
  @CsvFileSource(resources = "TableLogicTests.csv", numLinesToSkip = 1)
  void testRelation(
      String oneTableName,
      String anotherTableName,
      boolean oneToManyExpected,
      boolean manyToOneExpected,
      boolean oneToOneExpected,
      boolean relationTableExpected,
      boolean parentChildExpected,
      boolean firstTableInRelationExpected) {

    DatabaseMetaDataReader reader = new DatabaseMetaDataReader(ConfigLoader.load());
    DatabaseMetaDataModel meta = reader.read();
    processor.process(meta);

    TableModel oneTable = meta.getTable(oneTableName);
    TableModel anotherTable = meta.getTable(anotherTableName);

    boolean oneToManyActual = logic.isOneToMany(oneTable, anotherTable);
    assertEquals(
        "isOneToMany(" + oneTableName + ", " + anotherTableName + ")",
        oneToManyExpected,
        oneToManyActual);

    boolean manyToOneActual = logic.isManyToOne(oneTable, anotherTable);
    assertEquals(
        "isManyToOne(" + oneTableName + ", " + anotherTableName + ")",
        manyToOneExpected,
        manyToOneActual);

    boolean oneToOneActual = logic.isOneToOne(oneTable, anotherTable);
    assertEquals(
        "isOneToOne(" + oneTableName + ", " + anotherTableName + ")",
        oneToOneExpected,
        oneToOneActual);

    boolean relationTableActual = logic.isRelationTable(oneTable, anotherTable);
    assertEquals(
        "isRelationTable(" + oneTableName + ", " + anotherTableName + ")",
        relationTableExpected,
        relationTableActual);

    boolean parentChildActual = logic.isParentChild(oneTable, anotherTable);
    assertEquals(
        "isParentChild(" + oneTableName + ", " + anotherTableName + ")",
        parentChildExpected,
        parentChildActual);

    boolean firstTableInRelationActual = logic.isFirstTableInRelation(oneTable, anotherTable);

    assertEquals(
        "isFirstTableInRelation(" + oneTableName + ", " + anotherTableName + ")",
        firstTableInRelationExpected,
        firstTableInRelationActual);
  }
}
