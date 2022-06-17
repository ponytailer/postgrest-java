// Copyright 2022 Leyantech Ltd. All Rights Reserved.

package io.github.ponytailer.postgrest;

import io.github.ponytailer.postgrest.builder.PostgrestQueryBuilder;

import java.util.Collections;
import java.util.Map;

/**
 * @author ponytailer, {@literal <huangxiaohen2738@gmail.com>}
 * @date 2022-06-15.
 */
public class PostgrestClient {

  private final String uri;
  private final Map<String, String> headers = Collections.emptyMap();
  // private String schema;

  public PostgrestClient(String dbURI) {
    this.uri = dbURI;
  }

  public PostgrestQueryBuilder from(String tableName) {
    String tableURI = uri + "/" + tableName;
    return new PostgrestQueryBuilder(tableURI, headers);
  }

}
