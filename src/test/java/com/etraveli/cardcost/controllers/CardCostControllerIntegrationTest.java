package com.etraveli.cardcost.controllers;

import com.etraveli.cardcost.entity.ClearingCost;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CardCostControllerIntegrationTest {

  private static final Logger LOGGER = LoggerFactory.getLogger(CardCostControllerIntegrationTest.class);
  @Value(value = "${local.server.port}")
  private int port;

  @Autowired
  private TestRestTemplate restTemplate;

  @Test
  public void testFindAll() {
    LOGGER.info("--> Testing retrieve all countries");
    ClearingCost[] countryCosts = restTemplate.getForObject("http://localhost:" + port + "/", ClearingCost[].class);
    LOGGER.info("Clearing Cost Array: {}", Arrays.toString(countryCosts));
    assertTrue(countryCosts.length >= 3);
    Arrays.stream(countryCosts).forEach(s -> LOGGER.info(s.toString()));
  }

  @Test
  public void testFindById() {
    LOGGER.info("--> Testing retrieve by country US ");
    ClearingCost countryCost = restTemplate.getForObject("http://localhost:" + port + "/US", ClearingCost.class);
    LOGGER.info("Clearing Cost: {}", countryCost);
    assertNotNull(countryCost);
  }

  @Test
  void testPositiveCardCost() throws URISyntaxException {
    HttpHeaders headers = new HttpHeaders();
    headers.setAccept(List.of(MediaType.APPLICATION_JSON));
    RequestEntity<String> req = new RequestEntity<>(
        "{\"card_number\": 4571736098747}",
        headers,
        HttpMethod.POST,
        new URI("http://localhost:" + port + "/payment-cards-cost")
    );
    ResponseEntity<ClearingCost> response = restTemplate.exchange(req, ClearingCost.class);
    LOGGER.info("Response Entity: {}", response);
    assertAll("testPositiveCardCost",
        () -> assertEquals(HttpStatus.OK, response.getStatusCode()),
        () -> assertTrue(Objects.requireNonNull(response.getHeaders().get(HttpHeaders.CONTENT_TYPE)).contains(MediaType.APPLICATION_JSON_VALUE)),
        () -> assertNotNull(response.getBody()),
        () -> assertEquals(ClearingCost.class, response.getBody().getClass())
    );

  }
}