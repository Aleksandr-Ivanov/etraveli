package com.etraveli.cardcost;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.AbstractEnvironment;

@SpringBootApplication
public class CardCostApplication {
  private Logger LOGGER = LoggerFactory.getLogger(CardCostApplication.class);

  public static void main(String[] args) {
    SpringApplication.run(CardCostApplication.class, args);
  }

}
