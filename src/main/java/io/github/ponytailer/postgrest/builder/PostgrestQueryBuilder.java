// Copyright 2022 Leyantech Ltd. All Rights Reserved.

package io.github.ponytailer.postgrest.builder;

import com.alibaba.fastjson.JSON;
import io.github.ponytailer.postgrest.enums.Count;
import io.github.ponytailer.postgrest.enums.Method;
import io.github.ponytailer.postgrest.enums.Returning;

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

  private void appendHeader(Count count, Returning returning, Boolean upsert) {
    List<String> preferHeaders = new ArrayList<>();
    if (Objects.nonNull(returning)) {
      preferHeaders.add("return=" + returning.name().toLowerCase());
    }
    if (upsert) {
      preferHeaders.add("resolution=merge-duplicates");
    }
    if (Objects.nonNull(count)) {
      preferHeaders.add("count=" + count.name().toLowerCase());
    }
    if (!preferHeaders.isEmpty()) {
      addHeaders(HEADER_PREFER, String.join(",", preferHeaders));
    }
  }

  /**
   * select table.
   */
  public PostgrestFilterBuilder select(String columns, Boolean head, Count count) {
    setMethod(head ? Method.HEAD : Method.GET);
    addParams(SELECT, columns);
    appendHeader(count, null, false);
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
    appendHeader(count, returning, upsert);

    if (upsert && Objects.nonNull(onConflict)) {
      addParams("on_conflict", onConflict);
    }

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

  /**
   * delete.
   */
  public PostgrestFilterBuilder delete(Returning returning, Count count) {
    setMethod(Method.DELETE);
    appendHeader(count, returning, false);
    return new PostgrestFilterBuilder(this);
  }

  public PostgrestFilterBuilder delete() {
    return delete(Returning.REPRESENTATION, null);
  }

  /**
   * update.
   */
  public <V> PostgrestFilterBuilder update(Map<String, V> value, Returning returning, Count count) {
    setMethod(Method.PATCH);
    setBody(JSON.toJSONString(value));
    appendHeader(count, returning, false);
    return new PostgrestFilterBuilder(this);
  }

  /**
   * update.
   */
  public <V> PostgrestFilterBuilder update(Map<String, V> value) {
    setMethod(Method.PATCH);
    setBody(JSON.toJSONString(value));
    appendHeader(null, Returning.REPRESENTATION, false);
    return new PostgrestFilterBuilder(this);
  }

  /**
   * rpc.
   */
  public <V> PostgrestQueryBuilder rpc(Map<String, V> params) {
    setMethod(Method.POST);
    setBody(JSON.toJSONString(params));
    return this;
  }

}
