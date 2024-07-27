package dev.aulait.jeg.core.infra;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import java.util.UUID;

public class JpaUtils {

  private static final EntityManagerFactory emf =
      Persistence.createEntityManagerFactory("PERSISTENCE");
  private static final EntityManager em = emf.createEntityManager();

  public static EntityManager em() {
    return em;
  }

  public static EntityManagerFactory emf() {
    return emf;
  }

  public static String generateId() {
    return UUID.randomUUID().toString();
  }
}
