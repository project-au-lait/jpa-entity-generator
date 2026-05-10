package dev.aulait.jeg.core.domain.atomicaggregate;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import dev.aulait.jeg.core.infra.JpaUtils;
import dev.aulait.jeg.core.infra.util.ModelUtils;
import jakarta.persistence.EntityManager;
import java.util.Optional;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Test;

class AtomicAggregateIT {

  EntityManager em = JpaUtils.em();

  @Test
  void orphanRemovalTest() {
    AtomicParentEntity parent = buildParent(3);

    em.getTransaction().begin();

    em.persist(parent);

    em.flush();
    em.clear();

    AtomicParentEntity savedParent = em.find(AtomicParentEntity.class, parent.getId());

    AtomicParentEntity updatingParent = ModelUtils.map(savedParent, AtomicParentEntity.class);

    updatingParent.getAtomicChildren().add(buildChild(parent.getId(), 4));
    findBySeqNo(updatingParent, 1).ifPresent(child -> child.setName("updatedName"));
    updatingParent.getAtomicChildren().removeIf(child -> child.getId().getSeqNo() == 2);

    em.merge(updatingParent);

    em.flush();
    em.clear();

    savedParent = em.find(AtomicParentEntity.class, parent.getId());

    assertEquals(3, savedParent.getAtomicChildren().size());
    assertEquals("updatedName", findBySeqNo(savedParent, 1).orElseThrow().getName());
    assertTrue(findBySeqNo(savedParent, 2).isEmpty());
    assertFalse(findBySeqNo(savedParent, 4).isEmpty());
  }

  AtomicParentEntity buildParent(int childCount) {
    AtomicParentEntity parent = new AtomicParentEntity();
    parent.setId(JpaUtils.generateId());

    for (int i = 0; i < childCount; i++) {
      AtomicChildEntity child = buildChild(parent.getId(), i + 1);
      parent.getAtomicChildren().add(child);
    }

    return parent;
  }

  AtomicChildEntity buildChild(String parentId, int seqNo) {
    AtomicChildEntityId childId = new AtomicChildEntityId();
    childId.setParentId(parentId);
    childId.setSeqNo(seqNo);

    AtomicChildEntity child = new AtomicChildEntity();
    child.setId(childId);
    child.setName(RandomStringUtils.randomAlphanumeric(5));
    return child;
  }

  Optional<AtomicChildEntity> findBySeqNo(AtomicParentEntity parent, int seqNo) {
    return parent.getAtomicChildren().stream()
        .filter(child -> child.getId().getSeqNo() == seqNo)
        .findFirst();
  }
}
