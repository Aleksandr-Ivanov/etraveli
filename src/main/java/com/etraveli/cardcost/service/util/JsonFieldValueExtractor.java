package com.etraveli.cardcost.service.util;

public interface JsonFieldValueExtractor {
  String getValueByPath(String json, String path);
}
