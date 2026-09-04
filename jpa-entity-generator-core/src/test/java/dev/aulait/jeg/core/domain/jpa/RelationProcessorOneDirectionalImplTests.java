package dev.aulait.jeg.core.domain.jpa;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import dev.aulait.jeg.core.infra.config.Config;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

class RelationProcessorOneDirectionalImplTests {

  @Test
  void validateCascadeOneToManyAcceptsTableAndEntityFormats() {
    Config config = new Config();
    config.setCascadeOneToMany(
        new ArrayList<>(List.of("resume.career_history", "ResumeEntity.careerHistories")));
    RelationProcessorOneDirectionalImpl processor = new RelationProcessorOneDirectionalImpl(config);

    assertTrue(popCascadeOneToMany(config));
    assertTrue(config.getCascadeOneToMany().isEmpty());

    assertDoesNotThrow(processor::validateCascadeOneToMany);
  }

  @Test
  void validateCascadeOneToManyRejectsMixedTableAndFieldFormat() {
    Config config = new Config();
    config.setCascadeOneToMany(new ArrayList<>(List.of("resume.careerHistories")));
    RelationProcessorOneDirectionalImpl processor = new RelationProcessorOneDirectionalImpl(config);

    assertThrows(IllegalArgumentException.class, () -> validateCascadeOneToMany(processor));
  }

  private boolean popCascadeOneToMany(Config config) {
    return config.popCascadeOneToMany(
        "resume", "career_history", "ResumeEntity", "careerHistories");
  }

  private void validateCascadeOneToMany(RelationProcessorOneDirectionalImpl processor) {
    processor.validateCascadeOneToMany();
  }
}
