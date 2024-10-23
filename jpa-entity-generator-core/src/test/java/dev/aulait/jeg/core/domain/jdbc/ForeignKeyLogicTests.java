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

  @ParameterizedTest
  @CsvFileSource(resources = "ForeignKeyLogicTests.csv", numLinesToSkip = 1)
  void testRelation(
      String foreignKeyName,
      boolean oneToOneExpected,
      boolean manyToOneExpected,
      boolean parentChildExpected,
      boolean firstInPkExpected,
      boolean inRelationTableExpected) {

    DatabaseMetaDataReader reader = new DatabaseMetaDataReader(ConfigLoader.load());
    DatabaseMetaDataModel meta = reader.read();
    processor.process(meta);

    ForeignKeyModel fk = findByName(meta.getTables(), foreignKeyName);

    assertEquals(oneToOneExpected, logic.isOneToOne(fk), "isOneToOne(" + fk.getName() + ")");
    assertEquals(manyToOneExpected, logic.isManyToOne(fk), "isManyToOne(" + fk.getName() + ")");
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