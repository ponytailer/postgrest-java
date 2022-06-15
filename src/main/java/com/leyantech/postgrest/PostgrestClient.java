// Copyright 2022 Leyantech Ltd. All Rights Reserved.

package com.leyantech.postgrest;

import com.leyantech.postgrest.builder.PostgrestQueryBuilder;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collections;
import java.util.Map;

/**
 * @author ponytailer, {@literal <huangxiaohen2738@gmail.com>}
 * @date 2022-06-15.
 */
public class PostgrestClient {

  private final URI uri;
  private final Map<String, String> headers = Collections.emptyMap();
  // private String schema;

  public PostgrestClient(String dbURI) throws URISyntaxException {
    this.uri = new URI(dbURI);
  }

  public PostgrestQueryBuilder from(String tableName) throws URISyntaxException {
    URI tableURI = new URI(uri.getHost() + "/" + tableName);
    return new PostgrestQueryBuilder(tableURI, headers);
  }

}
