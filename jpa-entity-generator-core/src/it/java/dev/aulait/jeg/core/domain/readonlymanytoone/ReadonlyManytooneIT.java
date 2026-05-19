package dev.aulait.jeg.core.domain.readonlymanytoone;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import dev.aulait.jeg.core.infra.JpaUtils;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;

class ReadonlyManytooneIT {

  EntityManager em = JpaUtils.em();

  @Test
  void testReadonlyManyToOne() {
    ReadonlyManytooneMasterEntity master = new ReadonlyManytooneMasterEntity();
    master.setId(JpaUtils.generateId());
    master.setName("master");

    ReadonlyManytooneTransactionEntity transaction = new ReadonlyManytooneTransactionEntity();
    transaction.setId(JpaUtils.generateId());
    transaction.setMasterId(master.getId());
    transaction.setName("transaction");

    em.getTransaction().begin();
    em.persist(master);
    em.persist(transaction);
    em.flush();
    em.clear();

    ReadonlyManytooneTransactionEntity saved =
        em.find(ReadonlyManytooneTransactionEntity.class, transaction.getId());

    assertEquals(master.getId(), saved.getMasterId());
    assertNotNull(saved.getMaster());
    assertEquals(master.getId(), saved.getMaster().getId());
    assertEquals(master.getName(), saved.getMaster().getName());
  }

  @Test
  void testMasterIdIsNullWhenOnlyMasterFieldIsSet() {
    ReadonlyManytooneMasterEntity master = new ReadonlyManytooneMasterEntity();
    master.setId(JpaUtils.generateId());
    master.setName("master");

    ReadonlyManytooneTransactionEntity transaction = new ReadonlyManytooneTransactionEntity();
    transaction.setId(JpaUtils.generateId());
    transaction.setMaster(master);
    transaction.setName("transaction");

    em.getTransaction().begin();
    em.persist(master);
    em.persist(transaction);
    em.flush();
    em.clear();

    ReadonlyManytooneTransactionEntity saved =
        em.find(ReadonlyManytooneTransactionEntity.class, transaction.getId());

    assertNull(saved.getMasterId());
  }
}
