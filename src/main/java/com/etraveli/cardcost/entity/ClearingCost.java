package com.etraveli.cardcost.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;

@Entity
@Table(name = "clearing_cost")
public class ClearingCost {

  @Id
  @NotEmpty
  @Size(min = 2, max = 6)
  private String country;

  @DecimalMin(value = "0.0")
  @Digits(integer = 3, fraction = 2)
  @DecimalMax("999.99")
  private BigDecimal cost;

  public ClearingCost() {}

  public String getCountry() {
    return country;
  }

  public void setCountry(String country) {
    this.country = country;
  }

  public BigDecimal getCost() {
    return cost;
  }

  public void setCost(BigDecimal cost) {
    this.cost = cost;
  }

  @Override
  public String toString() {
    return "ClearingCost{" +
        "countryCode='" + country + '\'' +
        ", cost=" + cost +
        '}';
  }
}

