package com.etraveli.cardcost.service.binlist.expiry;

import com.etraveli.cardcost.entity.BinlistLookup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Component
public class BinlistLookupExpirationCheckerByDays implements BinlistLookupExpirationChecker {
  private static final Logger LOGGER = LoggerFactory.getLogger(BinlistLookupExpirationCheckerByDays.class);

  @Value("${binlist.lookup.expirationDays}")
  private String binlistLookupExpirationDays;

  public boolean isExpired(BinlistLookup binlistLookup) {
    LocalDateTime modifiedAt = binlistLookup.getModifiedAt();
    LOGGER.debug("Comparing binlist lookup modification date: {} to today with expiration policy: {} days.", modifiedAt, binlistLookupExpirationDays);
    int expirationDays = Integer.parseInt(binlistLookupExpirationDays);
    return ChronoUnit.DAYS.between(modifiedAt, LocalDateTime.now()) >= expirationDays;
  }

  public void setBinlistLookupExpirationDays(String binlistLookupExpirationDays) {
    this.binlistLookupExpirationDays = binlistLookupExpirationDays;
  }
}
