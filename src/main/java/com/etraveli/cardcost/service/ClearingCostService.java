package com.etraveli.cardcost.service;

import com.etraveli.cardcost.entity.ClearingCost;

public interface ClearingCostService {
  Iterable<ClearingCost> findAll();

  ClearingCost findByCountry(String countryCode);

  ClearingCost save(ClearingCost cost);

  ClearingCost update(ClearingCost cost);

  void delete(String country);

  ClearingCost getCostByCardNumber(String cardNumber);

}
