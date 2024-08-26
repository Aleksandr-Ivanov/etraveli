package com.etraveli.cardcost.repo;

import com.etraveli.cardcost.entity.BinlistLookup;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BinlistLookupRepository extends JpaRepository<BinlistLookup, Integer> {
}
