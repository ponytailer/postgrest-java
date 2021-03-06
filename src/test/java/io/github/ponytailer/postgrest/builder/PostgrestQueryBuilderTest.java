// Copyright 2022 Leyantech Ltd. All Rights Reserved.

package io.github.ponytailer.postgrest.builder;

import io.github.ponytailer.postgrest.enums.Count;
import io.github.ponytailer.postgrest.enums.Method;
import io.github.ponytailer.postgrest.enums.Returning;

import org.junit.Assert;
import org.junit.Test;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @author ponytailer, {@literal <huangxiaohen2738@gmail.com>}
 * @date 2022-06-16.
 */
public class PostgrestQueryBuilderTest {

  private static final String headerKey = "Prefer";

  private PostgrestQueryBuilder newBuilder() {
    return new PostgrestQueryBuilder("http://localhost", Collections.emptyMap());
  }

  @Test
  public void testSelect() {
    PostgrestQueryBuilder builder = newBuilder();

    builder.select();
    Assert.assertEquals(Method.GET, builder.getMethod());
    Assert.assertEquals("count=exact", builder.getHeaders().get(headerKey));
    Assert.assertEquals("*", builder.getParams().get("select"));

    builder = newBuilder();
    builder.select("id,name");
    Assert.assertEquals(Method.GET, builder.getMethod());
    Assert.assertEquals("count=exact", builder.getHeaders().get(headerKey));
    Assert.assertEquals("id,name", builder.getParams().get("select"));

    builder = newBuilder();
    builder.select("id,name", true, null);
    Assert.assertEquals(Method.HEAD, builder.getMethod());
    Assert.assertNull(builder.getHeaders().get(headerKey));
    Assert.assertEquals("id,name", builder.getParams().get("select"));

  }

  @Test
  public void testInsert() {

    PostgrestQueryBuilder builder = newBuilder();
    final String expectedBody = "[{\"name\":\"title\"}]";
    Map<String, String> value = new HashMap<>();
    value.put("name", "title");

    builder.insert(value);
    Assert.assertEquals(Method.POST, builder.getMethod());
    Assert.assertEquals("return=representation", builder.getHeaders().get(headerKey));
    Assert.assertEquals(expectedBody, builder.getBody());

    builder = newBuilder();
    builder.insert(Collections.singletonList(value));
    Assert.assertEquals(Method.POST, builder.getMethod());
    Assert.assertEquals("return=representation", builder.getHeaders().get(headerKey));
    Assert.assertEquals(expectedBody, builder.getBody());

    builder = newBuilder();
    builder.insert(value, true, "name", Returning.REPRESENTATION, Count.EXACT);
    Assert.assertEquals(Method.POST, builder.getMethod());
    Assert.assertEquals(
        "return=representation,resolution=merge-duplicates,count=exact",
        builder.getHeaders().get(headerKey));

    Assert.assertEquals("name", builder.getParams().get("on_conflict"));
    Assert.assertEquals(expectedBody, builder.getBody());

    builder = newBuilder();
    builder.insert(Collections.singletonList(value), true, "name", Returning.REPRESENTATION, null);
    Assert.assertEquals(Method.POST, builder.getMethod());
    Assert.assertEquals("return=representation,resolution=merge-duplicates",
        builder.getHeaders().get(headerKey));
    Assert.assertEquals("name", builder.getParams().get("on_conflict"));
    Assert.assertEquals(expectedBody, builder.getBody());
  }

  @Test
  public void testDelete() {
    PostgrestQueryBuilder builder = newBuilder();

    builder.delete();
    Assert.assertEquals(Method.DELETE, builder.getMethod());
    Assert.assertEquals("return=representation", builder.getHeaders().get(headerKey));

    builder.delete(Returning.MINIMAL, Count.ESTIMATED);
    Assert.assertEquals(Method.DELETE, builder.getMethod());
    Assert.assertEquals("return=minimal,count=estimated", builder.getHeaders().get(headerKey));

  }

  @Test
  public void testUpdate() {
    PostgrestQueryBuilder builder = newBuilder();
    final String expectedBody = "{\"name\":\"title\"}";
    Map<String, String> value = new HashMap<>();
    value.put("name", "title");
    builder.update(value);

    Assert.assertEquals(Method.PATCH, builder.getMethod());
    Assert.assertEquals("return=representation", builder.getHeaders().get(headerKey));
    Assert.assertEquals(expectedBody, builder.getBody());

    builder = newBuilder();
    builder.update(value, Returning.MINIMAL, Count.EXACT);
    Assert.assertEquals(Method.PATCH, builder.getMethod());
    Assert.assertEquals("return=minimal,count=exact", builder.getHeaders().get(headerKey));
    Assert.assertEquals(expectedBody, builder.getBody());

  }

}
