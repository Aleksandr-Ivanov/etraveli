package com.etraveli.cardcost;

import com.etraveli.cardcost.entity.ClearingCost;
import com.etraveli.cardcost.repo.ClearingCostRepository;
import com.etraveli.cardcost.service.ClearingCostServiceImpl;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PersistenceContextTest {

  public static final String NUMBER_FROM_DK_COUNTRY_CODE = "4571732134132431";
  public static final String COUNTRY_CODE_DK = "DK";

  private static final Logger LOGGER = LoggerFactory.getLogger(PersistenceContextTest.class);

  @Value(value = "${local.server.port}")
  private int port;

  @Autowired
  EntityManager entityManager;

  @Autowired
  ClearingCostServiceImpl clearingCostService;

  @Autowired
  ClearingCostRepository clearingCostRepository;


  @Test
  void madeUpClearingCostEntityDoesNotDirtyPersistenceContext() {
    ClearingCost dkMadeUpEntity = clearingCostService.getCostByCardNumber(NUMBER_FROM_DK_COUNTRY_CODE);
    LOGGER.info("Made up entity: {}", dkMadeUpEntity);

    ClearingCost originalClearingCost = clearingCostService.findByCountry(COUNTRY_CODE_DK);

    assertNotEquals(dkMadeUpEntity, originalClearingCost);
    assertEquals(COUNTRY_CODE_DK, dkMadeUpEntity.getCountry());
    assertEquals(ClearingCostServiceImpl.OTHERS_COUNTRY_CODE,
        originalClearingCost.getCountry());

    assertNotNull(dkMadeUpEntity.getCountry()); // must not be transient
    assertFalse(entityManager.contains(dkMadeUpEntity)); // must not be managed now
    assertNull(entityManager.find(ClearingCost.class, dkMadeUpEntity.getCountry())); //must not be in persistence context
    assertNull(clearingCostRepository.findById(COUNTRY_CODE_DK).orElse(null));
  }
}
