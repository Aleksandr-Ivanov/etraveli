package com.etraveli.cardcost.repo;

import com.etraveli.cardcost.entity.ClearingCost;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClearingCostRepository extends JpaRepository<ClearingCost, String> {

  Optional<ClearingCost> findOneByCountry(String countryCode);
}
