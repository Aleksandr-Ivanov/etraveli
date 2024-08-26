package com.etraveli.cardcost.service.binlist;

import com.etraveli.cardcost.entity.BinlistLookup;
import com.etraveli.cardcost.repo.BinlistLookupRepository;
import jakarta.annotation.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class BinlistLookupDataSynchronizer {

  private static final Logger LOGGER = LoggerFactory.getLogger(BinlistLookupDataSynchronizer.class);

  private final BinlistLookupRepository binlistLookupRepository;

  public BinlistLookupDataSynchronizer(BinlistLookupRepository binlistLookupRepository) {
    this.binlistLookupRepository = binlistLookupRepository;
  }

  BinlistLookup saveOrUpdate(int bin, @Nullable BinlistLookup oldBinlistLookup, String newLookupPayload) {
    BinlistLookup refreshedBinlistLookup;
    if (oldBinlistLookup == null) {
      BinlistLookup newBinlistLookup = new BinlistLookup(
          bin,
          newLookupPayload,
          LocalDateTime.now(),
          LocalDateTime.now()
      );

      LOGGER.info("New BIN: {} in the System!", bin);
      LOGGER.debug("New binlist lookup entry: {}.", newBinlistLookup);

      refreshedBinlistLookup = newBinlistLookup;
    } else {
      LOGGER.info("Updating expired BIN: {}", bin);
      LOGGER.debug(
          "Updating old BIN entity: {} with the lookup: {}.",
          oldBinlistLookup,
          newLookupPayload
      );

      oldBinlistLookup.setLookup(newLookupPayload);
      oldBinlistLookup.setModifiedAt(LocalDateTime.now());
      refreshedBinlistLookup = oldBinlistLookup;
    }

    LOGGER.info("Saving refreshed binlist lookup");
    LOGGER.debug("Entry: {}, for BIN: {}", refreshedBinlistLookup, bin);

    BinlistLookup updatedLookup = binlistLookupRepository.save(refreshedBinlistLookup);

    LOGGER.debug("Returning saved result entry: {}, for BIN: {}", refreshedBinlistLookup, bin);

    return updatedLookup;
  }
}