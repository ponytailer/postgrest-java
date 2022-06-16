// Copyright 2022 Leyantech Ltd. All Rights Reserved.

package com.ponytailer.postgrest.builder;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author ponytailer, {@literal <huangxiaohen2738@gmail.com>}
 * @date 2022-06-16.
 */
public class PostgrestFilterBuilderTest {

  private PostgrestFilterBuilder newBuilder() {
    return new PostgrestFilterBuilder(
        new PostgrestQueryBuilder("http://localhost", Collections.emptyMap()));
  }

  @Test
  public void eqTest() {
    PostgrestFilterBuilder builder = newBuilder();
    builder.eq("name", "test");
    builder.eq("id", 1);
    Assert.assertEquals("eq.test", builder.getParams().get("name"));
    Assert.assertEquals("eq.1", builder.getParams().get("id"));
  }

  @Test
  public void neqTest() {
    PostgrestFilterBuilder builder = newBuilder();
    builder.neq("name", "test");
    builder.neq("id", 1);
    Assert.assertEquals("neq.test", builder.getParams().get("name"));
    Assert.assertEquals("neq.1", builder.getParams().get("id"));
  }

  @Test
  public void gtTest() {
    PostgrestFilterBuilder builder = newBuilder();
    builder.gt("id", 1);
    Assert.assertEquals("gt.1", builder.getParams().get("id"));
    builder.gte("id", 1);
    Assert.assertEquals("gte.1", builder.getParams().get("id"));
  }

  @Test
  public void ltTest() {
    PostgrestFilterBuilder builder = newBuilder();
    builder.lt("id", 1);
    Assert.assertEquals("lt.1", builder.getParams().get("id"));
    builder.lte("id", 1);
    Assert.assertEquals("lte.1", builder.getParams().get("id"));
  }

  @Test
  public void likeTest() {
    PostgrestFilterBuilder builder = newBuilder();
    builder.like("name", "test");
    Assert.assertEquals("like.test", builder.getParams().get("name"));
    builder.ilike("name", "test");
    Assert.assertEquals("ilike.test", builder.getParams().get("name"));
  }

  @Test
  public void isTest() {
    PostgrestFilterBuilder builder = newBuilder();
    builder.is("name", null);
    Assert.assertEquals("is.null", builder.getParams().get("name"));
  }

  @Test
  public void inTest() {
    PostgrestFilterBuilder builder = newBuilder();
    List<String> nameList = Arrays.asList("test", "test2");
    builder.in("name", nameList);
    Assert.assertEquals("in.(test,test2)", builder.getParams().get("name"));
  }


}
