package com.etraveli.cardcost.service.binlist.remote;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RemoteBinlistLookupPayloadFetcherRestTemplateImplTest {
  @Mock
  RestTemplate restTemplate;

  @InjectMocks
  RemoteBinlistLookupPayloadFetcherRestTemplateImpl payloadFetcher;

  private final String mockedResponseBody = "{\"country\":{\"alpha2\":\"GR\"}}";

  @Test
  void getCardInfoJsonStringPositive() {
    ResponseEntity<String> responseEntity = new ResponseEntity<>(mockedResponseBody, HttpStatus.OK);

    when(restTemplate.exchange(
        any(String.class),
        eq(HttpMethod.GET),
        any(HttpEntity.class),
        eq(String.class)
    )).thenReturn(responseEntity);

    String payload = payloadFetcher.getCardInfoJsonString(123456);

    verify(restTemplate, times(1)).exchange(
        any(String.class),
        eq(HttpMethod.GET),
        any(HttpEntity.class),
        eq(String.class)
    );
    assertAll(
        () -> assertNotNull(payload),
        () -> assertEquals(payload, mockedResponseBody)
    );
  }
}