package com.etraveli.cardcost.service.binlist;

import com.etraveli.cardcost.entity.BinlistLookup;
import com.etraveli.cardcost.repo.BinlistLookupRepository;
import com.etraveli.cardcost.service.binlist.expiry.BinlistLookupExpirationChecker;
import com.etraveli.cardcost.service.binlist.remote.CardInfoJsonStringProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service("binlistLookupService")
public class BinlistLookupServiceImpl implements BinlistLookupService {

  private static final Logger LOGGER = LoggerFactory.getLogger(BinlistLookupServiceImpl.class);

  @Autowired
  private BinlistLookupExpirationChecker expirationChecker;

  @Autowired
  private BinlistLookupRepository binlistLookupRepo;

  @Autowired
  private CardInfoJsonStringProvider remoteLookupPayloadFetcher;

  @Autowired
  private BinlistLookupDataSynchronizer binlistLookupDataSaver;

  @Override
  public BinlistLookup getBinlistLookup(int bin) {
    LOGGER.info("Received service layer call to find binlist lookup by bin: {}", bin);
    LOGGER.debug("Calling for optional binlist lookup search on the persistence layer by bin: {}", bin);
    Optional<BinlistLookup> optionalPersistedBinlistLookup = binlistLookupRepo.findById(bin);
    LOGGER.debug("Received optional binlist lookup result: {}", optionalPersistedBinlistLookup);

    boolean isMissedLookup = optionalPersistedBinlistLookup.isEmpty()
        || expirationChecker.isExpired(optionalPersistedBinlistLookup.get());
    LOGGER.info("Existing binlist lookup is missed (empty or outdated)?: {}", isMissedLookup);

    if (isMissedLookup) {
      String newLookupPayload = remoteLookupPayloadFetcher.getCardInfoJsonString(bin);

      return binlistLookupDataSaver.saveOrUpdate(
          bin,
          optionalPersistedBinlistLookup.orElse(null),
          newLookupPayload
      );
    }

    LOGGER.info("Bringing persisted value for BIN: {}", bin);
    BinlistLookup persistedLookup = optionalPersistedBinlistLookup.get();
    LOGGER.debug("Entry: {} for BIN: {}", persistedLookup, bin);
    return persistedLookup;
  }
}