package com.etraveli.cardcost.controllers;

import com.etraveli.cardcost.entity.ClearingCost;
import com.etraveli.cardcost.service.ClearingCostService;
import com.etraveli.cardcost.service.util.JsonFieldValueExtractor;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;


@RestController
public class CardCostController {

  private static final Logger LOGGER = LoggerFactory.getLogger(CardCostController.class);

  @Autowired
  private ClearingCostService clearingCostService;

  @Autowired
  private JsonFieldValueExtractor jsonPayloadByPathExtractor;

  @Value("${cardCostRequest.cardNumberJsonPath}")
  private String cardNumberJsonPath;

  @GetMapping(path = {"/", ""})
  public Iterable<ClearingCost> findAll() {
    return clearingCostService.findAll();
  }

  @GetMapping(path = "/{country}")
  public ClearingCost findClearingCostByCountry(@PathVariable String country) {
    return clearingCostService.findByCountry(country);
  }

  @PostMapping(path = "/")
  public ClearingCost create(@RequestBody @Valid ClearingCost cost) {
    LOGGER.info("Creating cost record: {}", cost);
    return clearingCostService.save(cost);
  }

  @PutMapping(value = "/{country}")
  public ClearingCost update(@RequestBody @Valid ClearingCost cost, @PathVariable String country) {
    LOGGER.info("Updating cost record: {}", cost);
    return clearingCostService.update(cost);
  }

  @DeleteMapping(path = "/{country}")
  public void delete(@PathVariable String country) {
    LOGGER.info("Deleting cost record with country: {}", country);
    clearingCostService.delete(country);
  }

  @PostMapping(path = "/payment-cards-cost")
  public ClearingCost cardCost(@RequestBody String panJson) {
    LOGGER.debug("Card number json path: {}", cardNumberJsonPath);
    String numString = jsonPayloadByPathExtractor.getValueByPath(panJson, cardNumberJsonPath);
    System.out.println("Card number json path to look: " + cardNumberJsonPath);
    StringBuilder maskedPanBuilder = new StringBuilder();
    for (int i = 0; i < numString.length(); i++) {
      char ch = numString.charAt(i);
      if (Character.isDigit(ch)) {
        char toAppend = maskedPanBuilder.length() < 6 ? ch : '*';
        maskedPanBuilder.append(toAppend);
      }
    }
    String maskedPan = maskedPanBuilder.toString();

    return clearingCostService.getCostByCardNumber(maskedPan);
  }
}
