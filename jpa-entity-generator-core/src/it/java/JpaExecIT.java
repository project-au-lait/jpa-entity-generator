import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.Query;
import org.junit.jupiter.api.Test;

class JpaExecIT {

  @Test
  void test() {
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("PERSISTENCE");
    EntityManager em = emf.createEntityManager();

    Query query =
        em.createQuery("SELECT p FROM ParentEntity p JOIN FETCH p.children", Object.class);

    query.getResultList();
  }
}
