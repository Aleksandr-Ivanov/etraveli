package com.etraveli.cardcost.service.util;

import com.etraveli.cardcost.exception.JsonPayloadParsingException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class JsonFieldValueExtractorImplTest {

  public final String JSON_PAYLOAD_GR = "{\"country\":{\"alpha2\":\"GR\"}}";

  @Autowired
  JsonFieldValueExtractorImpl jsonFieldValueExtractor;

  @Test
  void getValueByPathPositive() {
    String existingPath = "/country/alpha2";

    String valueByPath = jsonFieldValueExtractor.getValueByPath(
        JSON_PAYLOAD_GR,
        existingPath
    );

    assertAll(
        () -> assertNotNull(valueByPath),
        () -> assertEquals("GR", valueByPath)
    );
  }

  @Test
  void getValueByPathThrowsException() {
    String nonExistingPath = "/wrong_node/wrong_field";

    assertThrowsExactly(
        JsonPayloadParsingException.class,
        () -> jsonFieldValueExtractor.getValueByPath(JSON_PAYLOAD_GR, nonExistingPath),
        "JsonPayloadParsingException has NOT been thrown for non-existing path: " + nonExistingPath
    );
  }
}