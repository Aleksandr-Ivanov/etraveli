package com.etraveli.cardcost.service;

import com.etraveli.cardcost.entity.BinlistLookup;
import com.etraveli.cardcost.entity.ClearingCost;
import com.etraveli.cardcost.exception.NotFoundException;
import com.etraveli.cardcost.repo.ClearingCostRepository;
import com.etraveli.cardcost.service.binlist.BinlistLookupService;
import com.etraveli.cardcost.service.util.JsonFieldValueExtractor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service("clearingCostService")
public class ClearingCostServiceImpl implements ClearingCostService {

  private static final Logger LOGGER = LoggerFactory.getLogger(ClearingCostServiceImpl.class);
  public static final String OTHERS_COUNTRY_CODE = "Others";

  @Autowired
  private ClearingCostRepository clearingCostRepo;

  @Autowired
  private BinlistLookupService binlistLookupService;

  @Autowired
  private JsonFieldValueExtractor jsonPayloadByPathExtractor;

  @Value("${binlist.lookup.countryCodeJsonPath}")
  private String binlistLookupCountryCodeJsonPath;

  @Override
  public Iterable<ClearingCost> findAll() {
    LOGGER.debug("Call to find all clearing costs received");
    return clearingCostRepo.findAll();
  }

  @Override
  public ClearingCost findByCountry(String countryCode) {
    LOGGER.debug("Call to find clearing cost by the country {} received", countryCode);

    ClearingCost clearingCost = clearingCostRepo.findById(countryCode)
        .or(() -> clearingCostRepo.findById(OTHERS_COUNTRY_CODE))
        .orElseThrow(() -> new NotFoundException(ClearingCost.class, countryCode));

    LOGGER.debug("By country: {} found clearing cost: {}", countryCode, clearingCost);

    return clearingCost;
  }

  @Override
  public ClearingCost save(ClearingCost cost) {
    LOGGER.debug("Call to save the new country: {} clearing cost: {} received", cost.getCountry(), cost.getCost());

    ClearingCost saveResult = clearingCostRepo.save(cost);

    LOGGER.debug("Returning successful saving result of the country cost: {}", saveResult);
    return saveResult;
  }

  @Override
  public ClearingCost update(ClearingCost newCost) {
    LOGGER.debug("Call to update the country: {} clearing cost to: {} received", newCost.getCountry(), newCost.getCost());

    ClearingCost updatedCost = clearingCostRepo.findById(newCost.getCountry())
        .map(clearingCost -> {
          clearingCost.setCost(newCost.getCost());
          return clearingCostRepo.save(clearingCost);
        })
        .orElseThrow(() -> new NotFoundException(ClearingCost.class, newCost.getCountry()));

    LOGGER.debug("Clearing cost of the country: {} successfully updated to: {}", updatedCost.getCountry(), updatedCost.getCost());
    return updatedCost;
  }

  @Override
  public void delete(String country) {
    LOGGER.debug("Call to delete the country: {} clearing cost received", country);

    clearingCostRepo.findOneByCountry(country)
        .orElseThrow(() -> new NotFoundException(ClearingCost.class, country));
    clearingCostRepo.deleteById(country);

    LOGGER.debug("Successfully deleted clearing cost for country: {}", country);
  }

  @Override
  public ClearingCost getCostByCardNumber(String cardNumber) {
    LOGGER.debug("Call to calculate clearing cost by card number: {}", cardNumber);

    int bin = Integer.parseInt(cardNumber.substring(0, 6));

    BinlistLookup binlistLookup = binlistLookupService.getBinlistLookup(bin);

    String cardCountryCode = jsonPayloadByPathExtractor.getValueByPath(
        binlistLookup.getLookup(),
        binlistLookupCountryCodeJsonPath);

    ClearingCost clearingCost = clearingCostRepo.findOneByCountry(cardCountryCode)
        .or(() -> clearingCostRepo.findOneByCountry(OTHERS_COUNTRY_CODE))
        .orElseThrow(() -> new NotFoundException(ClearingCostServiceImpl.class, cardCountryCode + ", " + OTHERS_COUNTRY_CODE));

    if (clearingCost.getCountry().equals(OTHERS_COUNTRY_CODE)) {
      clearingCost.setCountry(cardCountryCode);
    }
    return clearingCost;
  }
}
