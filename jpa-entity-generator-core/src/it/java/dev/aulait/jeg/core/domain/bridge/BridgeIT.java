package dev.aulait.jeg.core.domain.bridge;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import dev.aulait.jeg.core.infra.JpaUtils;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Test;

class BridgeIT {

  EntityManager em = JpaUtils.em();

  @Test
  void singleTest() {
    EastEntity east = new EastEntity();
    east.setId(JpaUtils.generateId());
    east.setName(RandomStringUtils.randomAlphanumeric(5));

    WestEntity west = new WestEntity();
    west.setId(JpaUtils.generateId());
    west.setName(RandomStringUtils.randomAlphanumeric(5));

    WestEastBridgeEntityId bridgeId = new WestEastBridgeEntityId();
    bridgeId.setEastId(east.getId());
    bridgeId.setWestId(west.getId());

    WestEastBridgeEntity bridge = new WestEastBridgeEntity();
    bridge.setId(bridgeId);
    bridge.setEast(east);
    bridge.setExtra(RandomStringUtils.randomAlphanumeric(5));

    EntityTransaction tran = em.getTransaction();
    tran.begin();

    em.persist(east);
    em.persist(west);
    em.persist(bridge);

    em.flush();
    em.clear();

    WestEastBridgeEntity savedBridge = em.find(WestEastBridgeEntity.class, bridgeId);
    assertEquals(east.getId(), savedBridge.getEast().getId());
    assertEquals(west.getId(), savedBridge.getId().getWestId());
    assertEquals(bridge.getExtra(), savedBridge.getExtra());

    em.remove(savedBridge);
    em.flush();
    em.clear();

    assertNull(em.find(WestEastBridgeEntity.class, bridgeId));

    tran.commit();
  }

  @Test
  void compositeTest() {
    CompositeEastEntity east = new CompositeEastEntity();
    CompositeEastEntityId eastId = new CompositeEastEntityId();
    eastId.setId1(RandomStringUtils.randomAlphanumeric(5));
    eastId.setId2(RandomStringUtils.randomAlphanumeric(5));
    east.setId(eastId);
    east.setName(RandomStringUtils.randomAlphanumeric(5));

    CompositeWestEntity west = new CompositeWestEntity();
    CompositeWestEntityId westId = new CompositeWestEntityId();
    westId.setId1(RandomStringUtils.randomAlphanumeric(5));
    westId.setId2(RandomStringUtils.randomAlphanumeric(5));
    west.setId(westId);
    west.setName(RandomStringUtils.randomAlphanumeric(5));

    CompositeWestEastBridgeEntityId bridgeId = new CompositeWestEastBridgeEntityId();
    bridgeId.setEastId1(east.getId().getId1());
    bridgeId.setEastId2(east.getId().getId2());
    bridgeId.setWestId1(west.getId().getId1());
    bridgeId.setWestId2(west.getId().getId2());
    CompositeWestEastBridgeEntity bridge = new CompositeWestEastBridgeEntity();
    bridge.setId(bridgeId);
    bridge.setEast(east);
    bridge.setExtra(RandomStringUtils.randomAlphanumeric(5));

    em.getTransaction().begin();

    em.persist(east);
    em.persist(west);
    em.persist(bridge);

    em.flush();
    em.clear();

    CompositeWestEastBridgeEntity savedBridge =
        em.find(CompositeWestEastBridgeEntity.class, bridgeId);
    CompositeEastEntityId expected = east.getId();
    CompositeEastEntityId actual = savedBridge.getEast().getId();
    assertEquals(expected.getId1().trim(), actual.getId1().trim());
    assertEquals(expected.getId2().trim(), actual.getId2().trim());
    assertEquals(bridge.getExtra(), savedBridge.getExtra());

    em.remove(savedBridge);
    em.flush();
    em.clear();

    assertNull(em.find(CompositeWestEastBridgeEntity.class, bridgeId));
  }
}
