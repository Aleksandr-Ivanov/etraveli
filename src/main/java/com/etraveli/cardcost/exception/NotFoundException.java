package com.etraveli.cardcost.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Not found requested item(s)")
public class NotFoundException extends RuntimeException {

  private Object id;

  public NotFoundException(Class<?> cls) {
    super("table for " + cls.getSimpleName() + " is empty");
  }

  public NotFoundException(Class<?> cls, Object id) {
    super(cls.getSimpleName() + " with id: " + id + " does not exist.");
  }

  public Object getId() {
    return id;
  }
}
