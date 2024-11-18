package dev.aulait.jeg.core.application;

import dev.aulait.jeg.core.domain.jpa.EntityMetadataModel;
import dev.aulait.jeg.core.domain.jpa.EntityMetadataProcessor;
import dev.aulait.jeg.core.domain.jpa.EntityModel;
import dev.aulait.jeg.core.infra.config.Config;
import dev.aulait.jeg.core.infra.json.JsonWriter;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;

@RequiredArgsConstructor
public class EntityMetadataGenerator {

  static final String ENTITY_METADATA_FILE = "jeg-metadata.json";
  final Config config;

  EntityMetadataProcessor entityMetadataProcessor = new EntityMetadataProcessor();
  JsonWriter jsonWriter = new JsonWriter();

  public Optional<Path> generate(List<EntityModel> entities) {

    if (StringUtils.isEmpty(config.getRuntime().getMetadataOutputDir())) {
      return Optional.empty();
    }

    Path outputFile = Path.of(config.getRuntime().getMetadataOutputDir(), ENTITY_METADATA_FILE);

    List<EntityMetadataModel> metadatas = new ArrayList<>();

    metadatas.addAll(entityMetadataProcessor.process(entities));
    metadatas.addAll(
        entityMetadataProcessor.processEmbeddedIds(
            entities.stream().map(EntityModel::getEmbeddedId).filter(Objects::nonNull).toList()));

    jsonWriter.write(outputFile, metadatas);

    return Optional.of(outputFile);
  }
}
