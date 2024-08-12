package dev.aulait.jeg.plugin.maven;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import java.nio.file.Path;
import java.nio.file.Paths;
import org.apache.maven.plugin.testing.AbstractMojoTestCase;
import org.junit.jupiter.api.Test;

class ReverseMojoTest extends AbstractMojoTestCase {

  @Test
  void execute() throws Exception {
    super.setUp();
    Path path = Paths.get("target/test-classes/pom.xml");

    ReverseMojo mojo = (ReverseMojo) super.lookupMojo("reverse", path.toAbsolutePath().toFile());
    assertNotNull(mojo);
    assertDoesNotThrow(() -> mojo.execute());
  }
}
