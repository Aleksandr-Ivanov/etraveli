package com.etraveli.cardcost.service.binlist;

import com.etraveli.cardcost.entity.BinlistLookup;
import com.etraveli.cardcost.repo.BinlistLookupRepository;
import com.etraveli.cardcost.service.binlist.expiry.BinlistLookupExpirationChecker;
import com.etraveli.cardcost.service.binlist.remote.CardInfoJsonStringProvider;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BinlistLookupServiceImplIntegrationTest {
  public static int EXISTING_BIN = 123456;
  public static int EXPIRED_BIN = 654321;
  public static int NOT_FOUND_BIN = 987654;
  public final String JSON_PAYLOAD_US = "{\"country\":{\"alpha2\":\"US\"}}";

  @Mock
  private BinlistLookupRepository repo;

  @Mock
  private BinlistLookupExpirationChecker expirationChecker;

  @Mock
  private CardInfoJsonStringProvider remoteLookupPayloadFetcher;

  @Mock
  private BinlistLookupDataSynchronizer binlistLookupDataSynchronizer;

  @InjectMocks
  BinlistLookupServiceImpl lookupService;

  @Test
  void whenExistingAndFreshBinThenReturnLookupFromCacheIT() {
    BinlistLookup existingLookup = new BinlistLookup(
        EXISTING_BIN,
        JSON_PAYLOAD_US,
        LocalDateTime.now().minusDays(12),
        LocalDateTime.now().minusDays(12)
    );

    when(repo.findById(EXISTING_BIN)).thenReturn(Optional.of(existingLookup));

    BinlistLookup binlistLookup = lookupService.getBinlistLookup(EXISTING_BIN);

    verify(repo, times(1)).findById(any(Integer.class));
    verify(expirationChecker).isExpired(binlistLookup);
    verifyNoInteractions(remoteLookupPayloadFetcher);
    verifyNoInteractions(binlistLookupDataSynchronizer);


    assertAll(
        () -> assertNotNull(binlistLookup),
        () -> assertEquals(existingLookup, binlistLookup)
    );
  }


  @Test
  void whenExpiredBinThenUpdateCacheAndReturnBinlistLookup() {

  }

  @Test
  void whenNotFoundBinThenSaveCacheAndReturnBinlistLookup() {

  }
}