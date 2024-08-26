package com.etraveli.cardcost.service.binlist.expiry;

import com.etraveli.cardcost.entity.BinlistLookup;

public interface BinlistLookupExpirationChecker {
  boolean isExpired(BinlistLookup lookup);
}
