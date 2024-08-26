package com.etraveli.cardcost.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.SERVICE_UNAVAILABLE, reason = "Third party remote API exception.")
public class ThirdPartyApiCallException extends RuntimeException {
  public ThirdPartyApiCallException(String providerMessage) {
    super("Third party remote API exception with message: " + providerMessage);
  }
}
