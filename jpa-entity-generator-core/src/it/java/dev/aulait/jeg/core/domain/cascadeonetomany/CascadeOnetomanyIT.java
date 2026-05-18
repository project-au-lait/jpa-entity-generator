package dev.aulait.jeg.core.domain.cascadeonetomany;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import dev.aulait.jeg.core.infra.JpaUtils;
import dev.aulait.jeg.core.infra.util.ModelUtils;
import jakarta.persistence.EntityManager;
import java.util.Optional;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Test;

class CascadeOnetomanyIT {

  EntityManager em = JpaUtils.em();

  @Test
  void orphanRemovalTest() {
    CascadeOnetomanyParentEntity parent = buildParent(3);

    em.getTransaction().begin();

    em.persist(parent);

    em.flush();
    em.clear();

    CascadeOnetomanyParentEntity savedParent =
        em.find(CascadeOnetomanyParentEntity.class, parent.getId());

    CascadeOnetomanyParentEntity updatingParent =
        ModelUtils.map(savedParent, CascadeOnetomanyParentEntity.class);

    updatingParent.getCascadeOnetomanyChildren().add(buildChild(parent.getId(), 4));
    findBySeqNo(updatingParent, 1).ifPresent(child -> child.setName("updatedName"));
    updatingParent.getCascadeOnetomanyChildren().removeIf(child -> child.getId().getSeqNo() == 2);

    em.merge(updatingParent);

    em.flush();
    em.clear();

    savedParent = em.find(CascadeOnetomanyParentEntity.class, parent.getId());

    assertEquals(3, savedParent.getCascadeOnetomanyChildren().size());
    assertEquals("updatedName", findBySeqNo(savedParent, 1).orElseThrow().getName());
    assertTrue(findBySeqNo(savedParent, 2).isEmpty());
    assertFalse(findBySeqNo(savedParent, 4).isEmpty());
  }

  CascadeOnetomanyParentEntity buildParent(int childCount) {
    CascadeOnetomanyParentEntity parent = new CascadeOnetomanyParentEntity();
    parent.setId(JpaUtils.generateId());

    for (int i = 0; i < childCount; i++) {
      CascadeOnetomanyChildEntity child = buildChild(parent.getId(), i + 1);
      parent.getCascadeOnetomanyChildren().add(child);
    }

    return parent;
  }

  CascadeOnetomanyChildEntity buildChild(String parentId, int seqNo) {
    CascadeOnetomanyChildEntityId childId = new CascadeOnetomanyChildEntityId();
    childId.setParentId(parentId);
    childId.setSeqNo(seqNo);

    CascadeOnetomanyChildEntity child = new CascadeOnetomanyChildEntity();
    child.setId(childId);
    child.setName(RandomStringUtils.randomAlphanumeric(5));
    return child;
  }

  Optional<CascadeOnetomanyChildEntity> findBySeqNo(
      CascadeOnetomanyParentEntity parent, int seqNo) {
    return parent.getCascadeOnetomanyChildren().stream()
        .filter(child -> child.getId().getSeqNo() == seqNo)
        .findFirst();
  }
}
