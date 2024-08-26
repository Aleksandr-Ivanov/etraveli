package com.etraveli.cardcost.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "binlist_lookup")
public class BinlistLookup implements Serializable {
  @Id
  @Column(name = "bin")
  private int bin;

  @NotEmpty
  @Size(min = 2, max = 300)
  @Column(name = "lookup_json")
  private String lookup;

  @DateTimeFormat(pattern = "yyyy-MM-dd")
  @Column(name = "created_at")
  private LocalDateTime createdAt;

  @DateTimeFormat(pattern = "yyyy-MM-dd")
  @Column(name = "modified_at")
  private LocalDateTime modifiedAt;

  public BinlistLookup() {}

  public BinlistLookup(int bin, String lookup, LocalDateTime createdAt, LocalDateTime modifiedAt) {
    this.bin = bin;
    this.lookup = lookup;
    this.createdAt = createdAt;
    this.modifiedAt = modifiedAt;
  }

  public int getBin() {
    return bin;
  }

  public void setBin(int bin) {
    this.bin = bin;
  }

  public String getLookup() {
    return lookup;
  }

  public void setLookup(String lookup) {
    this.lookup = lookup;
  }

  public LocalDateTime getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(LocalDateTime createdAt) {
    this.createdAt = createdAt;
  }

  public LocalDateTime getModifiedAt() {
    return modifiedAt;
  }

  public void setModifiedAt(LocalDateTime modifiedAt) {
    this.modifiedAt = modifiedAt;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof BinlistLookup that)) return false;
    return bin == that.bin;
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(bin);
  }

  @Override
  public String toString() {
    return "BinlistLookup{" +
        "bin=" + bin +
        ", lookup='" + lookup + '\'' +
        ", createdAt=" + createdAt +
        ", modifiedAt=" + modifiedAt +
        '}';
  }
}
