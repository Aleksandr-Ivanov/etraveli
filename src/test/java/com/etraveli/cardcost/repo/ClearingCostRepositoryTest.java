package com.etraveli.cardcost.repo;

import com.etraveli.cardcost.entity.ClearingCost;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class ClearingCostRepositoryTest {
  @Autowired
  ClearingCostRepository repository;

  @Test
  public void couldFindUs() {
    ClearingCost clearingCost = repository.findById("US").orElse(null);
    assertNotNull(clearingCost);
  }

  @Test
  public void couldFindAll() {
    List<ClearingCost> clearingCost = repository.findAll();
    assertFalse(clearingCost.isEmpty());
  }


}