package dev.aulait.jeg.core.application;

import static org.junit.Assert.assertEquals;

import dev.aulait.jeg.core.infra.config.ConfigLoader;
import dev.aulait.jeg.core.infra.util.ResourceUtils;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class EntityGeneratorTests {

  static List<Path> outputFiles;

  @BeforeAll
  static void execute() {
    EntityGenerator generator = new EntityGenerator(ConfigLoader.load());
    outputFiles = generator.execute();
  }

  /**
   * Connect to the DB and generate a JPA Entity java file, and check that the contents match the
   * java file prepared in advance.
   */
  @ParameterizedTest
  @ValueSource(
      strings = {
        "/dev/aulait/jeg/core/domain/mastertran/MasterEntity.java",
        "/dev/aulait/jeg/core/domain/mastertran/TransactionEntity.java",
        "/dev/aulait/jeg/core/domain/mastertran/CompositeMasterEntity.java",
        "/dev/aulait/jeg/core/domain/mastertran/CompositeMasterEntityId.java",
        "/dev/aulait/jeg/core/domain/mastertran/CompositeTransactionEntity.java",
        "/dev/aulait/jeg/core/domain/parentchild/ParentEntity.java",
        "/dev/aulait/jeg/core/domain/parentchild/ChildEntity.java",
        "/dev/aulait/jeg/core/domain/parentchild/ChildEntityId.java",
        "/dev/aulait/jeg/core/domain/parentchild/CompositeParentEntity.java",
        "/dev/aulait/jeg/core/domain/parentchild/CompositeParentEntityId.java",
        "/dev/aulait/jeg/core/domain/parentchild/CompositeChildEntity.java",
        "/dev/aulait/jeg/core/domain/parentchild/CompositeChildEntityId.java",
        "/dev/aulait/jeg/core/domain/multifk/MultifkChildEntity.java",
        "/dev/aulait/jeg/core/domain/multifk/MultifkChildEntityId.java",
        "/dev/aulait/jeg/core/domain/multifk/MultifkParentEntity.java",
        "/dev/aulait/jeg/core/domain/datatypes/DataTypesEntity.java",
        "/dev/aulait/jeg/core/domain/selfref/SelfReferenceEntity.java",
        "/dev/aulait/jeg/core/domain/selfref/CompositeSelfReferenceEntity.java",
        "/dev/aulait/jeg/core/domain/selfref/CompositeSelfReferenceEntityId.java",
      })
  void testExecute(String path) {
    Path fileName = Path.of(path).getFileName();
    Path targetFile =
        outputFiles.stream().filter(outputFile -> outputFile.endsWith(fileName)).findFirst().get();
    assertOutputFile(targetFile, path);
  }

  void assertOutputFile(Path outputFile, String fileName) {
    try {
      String actual = Files.readString(outputFile);

      String expected = ResourceUtils.res2text(this, fileName);

      assertEquals(expected, actual);
    } catch (IOException e) {
      throw new UncheckedIOException(e);
    }
  }
}
