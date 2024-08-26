CREATE TABLE clearing_cost (
  country VARCHAR(6) NOT NULL, -- ISO 3166-1 alpha-2 country code
  cost DECIMAL(5, 2) NOT NULL,
  PRIMARY KEY (country)
);

CREATE TABLE binlist_lookup (
  bin INT UNSIGNED NOT NULL, -- Bank / Issuer Identification Number
  lookup_json VARCHAR(300) NOT NULL,
  created_at TIMESTAMP NOT NULL,
  modified_at TIMESTAMP NOT NULL,
  PRIMARY KEY (bin)
);
