// Copyright 2022 Leyantech Ltd. All Rights Reserved.

package com.ponytailer.postgrest.builder;

import com.ponytailer.postgrest.enums.Count;
import com.ponytailer.postgrest.enums.Method;
import com.ponytailer.postgrest.enums.Returning;

import com.alibaba.fastjson.JSON;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
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
    addAllHeaders(headers);
  }

  /**
   * select table.
   */
  public PostgrestFilterBuilder select(String columns, Boolean head, Count count) {
    setMethod(head ? Method.HEAD : Method.GET);
    addParams(SELECT, columns);
    if (Objects.nonNull(count)) {
      addHeaders(HEADER_PREFER, "count=" + count.name().toLowerCase());
    }
    return new PostgrestFilterBuilder(this);
  }

  /**
   * select table by count.
   */
  public PostgrestFilterBuilder select(String columns) {
    return select(columns, false, Count.EXACT);
  }

  public PostgrestFilterBuilder select() {
    return select("*", false, Count.EXACT);
  }

  /**
   * insert into.
   */
  public <V> PostgrestFilterBuilder insert(List<Map<String, V>> values, Boolean upsert,
      String onConflict, Returning returning, Count count) {

    setMethod(Method.POST);

    List<String> preferHeaders = new ArrayList<>();
    preferHeaders.add("return=" + returning.name().toLowerCase());
    if (upsert) {
      preferHeaders.add("resolution=merge-duplicates");
    }
    if (upsert && Objects.nonNull(onConflict)) {
      addParams("on_conflict", onConflict);
    }
    if (Objects.nonNull(count)) {
      preferHeaders.add("count=" + count.name().toLowerCase());
    }
    addHeaders(HEADER_PREFER, String.join(",", preferHeaders));

    setBody(JSON.toJSONString(values));
    return new PostgrestFilterBuilder(this);
  }

  public <V> PostgrestFilterBuilder insert(Map<String, V> value, Boolean upsert, String onConflict,
      Returning returning, Count count) {
    return insert(Collections.singletonList(value), upsert, onConflict, returning, count);
  }

  public <V> PostgrestFilterBuilder insert(Map<String, V> value) {
    return insert(value, false, null, Returning.REPRESENTATION, null);
  }

  public <V> PostgrestFilterBuilder insert(List<Map<String, V>> valueList) {
    return insert(valueList, false, null, Returning.REPRESENTATION, null);
  }

}
