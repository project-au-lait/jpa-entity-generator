package dev.aulait.jeg.core.domain.parentchild;

import static org.junit.Assert.assertEquals;

import dev.aulait.jeg.core.infra.JpaUtils;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Test;

class ParentChildIT {

  EntityManager em = JpaUtils.em();

  @Test
  void test() {
    ParentEntity parent = new ParentEntity();
    parent.setId(JpaUtils.generateId());

    ChildEntityId childId = new ChildEntityId();
    childId.setParentId(parent.getId());
    childId.setSeqNo(parent.getChildren().size() + 1);

    ChildEntity child = new ChildEntity();
    child.setId(childId);
    child.setName(RandomStringUtils.randomAlphanumeric(5));

    EntityTransaction tran = em.getTransaction();
    tran.begin();

    em.persist(parent);
    em.persist(child);

    em.flush();
    em.clear();

    ParentEntity savedParent = em.find(ParentEntity.class, parent.getId());

    assertEquals(child.getName(), savedParent.getChildren().iterator().next().getName());
  }
}
