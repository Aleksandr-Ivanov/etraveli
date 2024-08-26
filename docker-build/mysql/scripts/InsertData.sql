INSERT INTO clearing_cost(country, cost) VALUES('US', 5.00);

INSERT INTO clearing_cost(country, cost) VALUES('GR', 15.00);

INSERT INTO clearing_cost(country, cost) VALUES('Others', 10.00);

INSERT INTO binlist_lookup(
  bin,
  lookup_json,
  created_at,
  modified_at
)
VALUES(
  457173,
  '{"number":{},"scheme":"visa","type":"debit","brand":"Visa Classic/Dankort","country":{"numeric":"208","alpha2":"DK","name":"Denmark","emoji":"🇩🇰","currency":"DKK","latitude":56,"longitude":10},"bank":{"name":"Jyske Bank A/S"}}',
  '2024-08-27 20:01',
  '2024-08-27 20:04'
);