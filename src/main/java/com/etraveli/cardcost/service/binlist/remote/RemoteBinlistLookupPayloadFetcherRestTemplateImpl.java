package com.etraveli.cardcost.service.binlist.remote;

import com.etraveli.cardcost.exception.ThirdPartyApiCallException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Component
public class RemoteBinlistLookupPayloadFetcherRestTemplateImpl implements CardInfoJsonStringProvider {
  public static final String HTTP_HEADER_ACCEPT_VERSION = "Accept-Version";

  private static final Logger LOGGER = LoggerFactory.getLogger(RemoteBinlistLookupPayloadFetcherRestTemplateImpl.class);

  @Autowired
  private RestTemplate restTemplate;

  @Value("${binlist.url}")
  private String binlistLookupUrl;

  @Value("${binlist.httpHeaderAcceptVersionNumber}")
  private String httpHeaderAcceptVersionNumber;

  @Override
  public String getCardInfoJsonString(int bin) {
    LOGGER.info("Preparing to call remote REST service: {} for BIN: {} lookup", binlistLookupUrl, bin);
    HttpHeaders httpHeaders = new HttpHeaders();
    httpHeaders.add(HTTP_HEADER_ACCEPT_VERSION, httpHeaderAcceptVersionNumber);
    httpHeaders.setAccept(List.of(MediaType.APPLICATION_JSON));

    HttpEntity<Void> requestEntity = new HttpEntity<>(httpHeaders);
    LOGGER.debug("Making GET call with headers: {}", httpHeaders);
    ResponseEntity<String> responseEntity = null;
    try {
      responseEntity = restTemplate.exchange(binlistLookupUrl + bin, HttpMethod.GET, requestEntity, String.class);
    } catch (RestClientException e) {
      throw new ThirdPartyApiCallException(e.getMessage());
    }
    LOGGER.info("Remote GET call has finished... Processing results");

    //TODO: Validation of response
    String lookupPayload = responseEntity.getBody();
    LOGGER.debug("Remote {} GET call for BIN lookup result: {}", binlistLookupUrl, lookupPayload);
    return lookupPayload;
  }
}
