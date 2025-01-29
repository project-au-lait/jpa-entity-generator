package dev.aulait.jeg.core.application;

import dev.aulait.jeg.core.domain.jdbc.DatabaseMetaDataModel;
import dev.aulait.jeg.core.domain.jdbc.DatabaseMetaDataProcessor;
import dev.aulait.jeg.core.domain.jdbc.DatabaseMetaDataReader;
import dev.aulait.jeg.core.domain.jdbc.TableModel;
import dev.aulait.jeg.core.domain.jpa.EmbeddedIdModel;
import dev.aulait.jeg.core.domain.jpa.EntityAdjustProcessor;
import dev.aulait.jeg.core.domain.jpa.EntityImportProcessor;
import dev.aulait.jeg.core.domain.jpa.EntityModel;
import dev.aulait.jeg.core.domain.jpa.RelationProcessor;
import dev.aulait.jeg.core.domain.jpa.RelationProcessorOneDirectionalImpl;
import dev.aulait.jeg.core.domain.jpa.TableToEntityProcessor;
import dev.aulait.jeg.core.infra.config.Config;
import dev.aulait.jeg.core.infra.formatter.FormatterFactory;
import dev.aulait.jeg.core.infra.template.TemplateProcessor;
import dev.aulait.jeg.core.infra.textfile.TextFileModel;
import dev.aulait.jeg.core.infra.textfile.TextFileWriter;
import java.nio.file.Path;
import java.util.List;
import java.util.Objects;

public class EntityGenerator {

  DatabaseMetaDataReader reader;
  TableToEntityProcessor tableToEntityProcessor;
  DatabaseMetaDataProcessor databaseMetaDataProcessor = new DatabaseMetaDataProcessor();
  RelationProcessor relationProcessor;
  EntityImportProcessor entityImportProcessor = new EntityImportProcessor();
  EntityAdjustProcessor entityAdjustProcessor = new EntityAdjustProcessor();
  TemplateProcessor templateProcessor = new TemplateProcessor();
  TextFileWriter writer = new TextFileWriter();
  EntityMetadataGenerator entityMetadataGenerator;

  public EntityGenerator(Config config) {
    reader = new DatabaseMetaDataReader(config);
    tableToEntityProcessor = new TableToEntityProcessor(config);
    relationProcessor = new RelationProcessorOneDirectionalImpl(config);
    templateProcessor.setFormatter(FormatterFactory.create(config.getFormatter()));
    entityMetadataGenerator = new EntityMetadataGenerator(config);
  }

  public List<Path> execute() {
    List<EntityModel> entities = generateEntities();

    List<TextFileModel> files = templateProcessor.process(entities);

    List<EmbeddedIdModel> embeddedIds =
        entities.stream().map(EntityModel::getEmbeddedId).filter(Objects::nonNull).toList();

    files.addAll(templateProcessor.process(embeddedIds));

    List<Path> outputFiles = writer.write(files);

    entityMetadataGenerator.generate(entities).ifPresent(outputFiles::add);

    return outputFiles;
  }

  List<EntityModel> generateEntities() {
    DatabaseMetaDataModel meta = reader.read();
    List<TableModel> tables = databaseMetaDataProcessor.process(meta);

    List<EntityModel> entities = tableToEntityProcessor.process(tables);

    relationProcessor.process(tables, entities);

    entityAdjustProcessor.process(entities);

    entityImportProcessor.process(entities);

    return entities;
  }
}
