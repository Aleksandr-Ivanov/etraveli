package com.etraveli.cardcost.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Not found specified field path in a JSON object.")
public class JsonPayloadParsingException extends RuntimeException {

  public JsonPayloadParsingException(String payload) {
    super("Error of handling JSON payload:\n" + payload + "\n");
  }

  public JsonPayloadParsingException(String jsonPath, String payload) {
    super("JSON field value is null, empty or not found by JSON path: " + jsonPath + " in the payload:\n" + payload + "\n");
  }
}
