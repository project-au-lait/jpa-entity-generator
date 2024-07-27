package dev.aulait.jeg.core.domain.mastertran;

import static org.junit.Assert.assertEquals;

import dev.aulait.jeg.core.infra.JpaUtils;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import org.junit.jupiter.api.Test;

class MasterTransactionIT {

  EntityManager em = JpaUtils.em();

  @Test
  void test() {
    MasterEntity master = new MasterEntity();
    master.setId(JpaUtils.generateId());

    TransactionEntity transaction = new TransactionEntity();
    transaction.setId(JpaUtils.generateId());
    transaction.setMaster(master);

    EntityTransaction tran = em.getTransaction();
    tran.begin();

    em.persist(master);
    em.persist(transaction);

    em.flush();
    em.clear();

    TransactionEntity savedTransaction = em.find(TransactionEntity.class, transaction.getId());

    assertEquals(master.getId(), savedTransaction.getMaster().getId());
  }
}
