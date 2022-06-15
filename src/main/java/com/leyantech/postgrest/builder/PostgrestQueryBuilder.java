// Copyright 2022 Leyantech Ltd. All Rights Reserved.

package com.leyantech.postgrest.builder;

import com.leyantech.postgrest.enums.Count;
import com.leyantech.postgrest.enums.Method;

import java.net.URI;
import java.util.Map;
import java.util.Objects;

/**
 * @author ponytailer, {@literal <huangxiaohen2738@gmail.com>}
 * @date 2022-06-15.
 */
public class PostgrestQueryBuilder extends PostgrestBuilder {

  private static final String SELECT = "select";
  private static final String HEADER_PREFER = "Prefer";

  public PostgrestQueryBuilder(String uri, Map<String, String> headers) {
    this.uri = uri;
    this.headers.putAll(headers);
  }

  public PostgrestFilterBuilder select(String columns, Boolean head, Count count) {
    method = head ? Method.HEAD : Method.GET;
    params.put(SELECT, columns);
    if (Objects.nonNull(count)) {
      headers.put(HEADER_PREFER, "count=" + count.name().toLowerCase());
    }
    return new PostgrestFilterBuilder(this);
  }
}
