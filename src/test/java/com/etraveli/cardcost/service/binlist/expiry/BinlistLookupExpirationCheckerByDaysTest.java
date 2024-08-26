package com.etraveli.cardcost.service.binlist.expiry;

import com.etraveli.cardcost.entity.BinlistLookup;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;


class BinlistLookupExpirationCheckerByDaysTest {

  private static BinlistLookupExpirationCheckerByDays expiryCheckerByDays;
  private BinlistLookup binlistLookup;

  @BeforeAll
  public static void setUpChecker() {
    expiryCheckerByDays = new BinlistLookupExpirationCheckerByDays();
    expiryCheckerByDays.setBinlistLookupExpirationDays("30");
  }

  @BeforeEach
  public void setUpBinlistLookup() {
    binlistLookup = new BinlistLookup(
        123456,
        "{\"number\":{},\"scheme\":\"visa\",\"type\":\"debit\",\"brand\":\"Visa Classic/Dankort\",\"country\":{\"numeric\":\"208\",\"alpha2\":\"DK\",\"name\":\"Denmark\",\"emoji\":\"\uD83C\uDDE9\uD83C\uDDF0\",\"currency\":\"DKK\",\"latitude\":56,\"longitude\":10},\"bank\":{\"name\":\"Jyske Bank A/S\"}}",
        LocalDateTime.now(),
        LocalDateTime.now()
    );
  }

  @Test
  public void isExpiredReturnsTrue() {
    binlistLookup.setModifiedAt(LocalDateTime.now().minusDays(42));
    assertTrue(expiryCheckerByDays.isExpired(binlistLookup));
  }

  @Test
  public void isExpiredReturnsFalse() {
    binlistLookup.setModifiedAt(LocalDateTime.now().minusDays(28));
    assertFalse(expiryCheckerByDays.isExpired(binlistLookup));
  }
}