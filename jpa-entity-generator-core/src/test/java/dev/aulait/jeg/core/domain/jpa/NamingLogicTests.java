package dev.aulait.jeg.core.domain.jpa;

import static org.junit.Assert.assertEquals;

import dev.aulait.jeg.core.domain.jdbc.ForeignKeyModel;
import dev.aulait.jeg.core.domain.jdbc.KeyModel;
import dev.aulait.jeg.core.infra.config.ConfigLoader;
import java.util.List;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class NamingLogicTests {
  NamingLogic logic = new NamingLogic(ConfigLoader.load());

  @ParameterizedTest
  @CsvSource({
    "master_id:id, MasterEntity, master",
    "master_id:master_id, MasterEntity, master",
    "comp_master_id_1:id_1&co_master_id_2:id_2, CompositeMaster, compositeMaster"
  })
  void testToManyToOneFieldName(
      String columnNamePairs, String oneEntityName, String expectedFieldName) {

    ForeignKeyModel fk = new ForeignKeyModel("test_fk");
    fk.getKeys().addAll(buildKeyModels(columnNamePairs));

    EntityModel oneEntity = new EntityModel();
    oneEntity.setName(oneEntityName);

    String actualFieldName = logic.toManyToOneFieldName(fk, oneEntity);

    assertEquals(expectedFieldName, actualFieldName);
  }

  List<KeyModel> buildKeyModels(String columnNamePairs) {
    return List.of(columnNamePairs.split("&")).stream()
        .map(
            pair -> {
              String[] parts = pair.split(":");
              return buildKeyModel(parts[0], parts[1]);
            })
        .toList();
  }

  KeyModel buildKeyModel(String columnName, String referencedColumnName) {
    return KeyModel.builder().FKCOLUMN_NAME(columnName).PKCOLUMN_NAME(referencedColumnName).build();
  }
}
