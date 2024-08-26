package com.etraveli.cardcost.service.util;

import com.etraveli.cardcost.exception.JsonPayloadParsingException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

@Component
public class JsonFieldValueExtractorImpl implements JsonFieldValueExtractor {

  private final ObjectMapper objectMapper;

  public JsonFieldValueExtractorImpl(ObjectMapper objectMapper) {
    this.objectMapper = objectMapper;
  }

  @Override
  public String getValueByPath(String json, String path) {
    String val = "";
    try {
      JsonNode countryNode = objectMapper.readTree(json)
          .at(path);

      if (countryNode != null && !countryNode.isNull() && !countryNode.isMissingNode()) {
        val = countryNode.asText();
      } else {
        throw new JsonPayloadParsingException(path, json);
      }
    } catch (JsonProcessingException e) {
      throw new JsonPayloadParsingException(json);
    }
    return val;
  }
}
