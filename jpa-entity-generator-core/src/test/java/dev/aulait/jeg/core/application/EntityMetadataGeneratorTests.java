package dev.aulait.jeg.core.application;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.fasterxml.jackson.core.type.TypeReference;
import dev.aulait.jeg.core.domain.jpa.EntityMetadataModel;
import dev.aulait.jeg.core.domain.jpa.EntityModel;
import dev.aulait.jeg.core.infra.config.Config;
import dev.aulait.jeg.core.infra.config.ConfigLoader;
import dev.aulait.jeg.core.infra.json.JsonUtils;
import dev.aulait.jeg.core.infra.util.ResourceUtils;
import java.nio.file.Path;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class EntityMetadataGeneratorTests {

  static List<EntityMetadataModel> entityMetadataModels;

  @BeforeAll
  static void execute() {
    Config config = ConfigLoader.load();
    EntityGenerator entityGenerator = new EntityGenerator(config);
    List<EntityModel> entities = entityGenerator.generateEntities();

    EntityMetadataGenerator entityMetadataGenerator = new EntityMetadataGenerator(config);
    Path outputFile = entityMetadataGenerator.generate(entities).get();

    entityMetadataModels =
        JsonUtils.readValue(outputFile, new TypeReference<List<EntityMetadataModel>>() {});
  }

  @ParameterizedTest
  @ValueSource(strings = {"MainEntity", "ParentEntity", "ChildEntity", "TransactionEntity"})
  void testExecute(String entityName) {

    EntityMetadataModel entity =
        entityMetadataModels.stream()
            .filter(e -> e.getClassName().equals(entityName))
            .findFirst()
            .get();

    String actual = JsonUtils.writeValueAsString(entity);

    String expected = ResourceUtils.res2text(this, entityName + "Metadata.json");
    expected = JsonUtils.format(expected);

    assertEquals(expected, actual);
  }
}
