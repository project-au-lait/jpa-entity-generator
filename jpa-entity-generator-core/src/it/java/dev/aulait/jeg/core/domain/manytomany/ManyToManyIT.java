package dev.aulait.jeg.core.domain.manytomany;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import dev.aulait.jeg.core.infra.JpaUtils;
import jakarta.persistence.EntityManager;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Test;

class ManyToManyIT {

  @Test
  void test() {
    LeftEntity left = new LeftEntity();
    left.setId(JpaUtils.generateId());

    RightEntity right = new RightEntity();
    right.setId(JpaUtils.generateId());
    right.setName(RandomStringUtils.randomAlphabetic(10));

    left.getRights().add(right);

    EntityManager em = JpaUtils.em();
    em.getTransaction().begin();

    em.persist(left);
    em.persist(right);

    em.flush();
    em.clear();

    LeftEntity savedLeft = em.find(LeftEntity.class, left.getId());

    assertEquals(right.getName(), savedLeft.getRights().iterator().next().getName());

    savedLeft.getRights().clear();
    em.flush();

    savedLeft = em.find(LeftEntity.class, left.getId());
    assertTrue(savedLeft.getRights().isEmpty());
  }
}
