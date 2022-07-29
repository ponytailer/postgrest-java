package io.github.ponytailer.postgrest.builder;

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

  @Test
  public void testLimit() {
    PostgrestFilterBuilder builder = newBuilder();
    builder.limit(1L);
    Assert.assertEquals("1", builder.getParams().get("limit"));

    builder = newBuilder();
    builder.limit(1L, "item");
    Assert.assertEquals("1", builder.getParams().get("item.limit"));

  }

  @Test
  public void testOrder() {
    PostgrestFilterBuilder builder = newBuilder();
    builder.order("name");
    Assert.assertEquals("name.desc", builder.getParams().get("order"));

  }

  @Test
  public void testRange() {
    PostgrestFilterBuilder builder = newBuilder();
    builder.range(1L, 20L);
    Assert.assertEquals("1", builder.getParams().get("offset"));
    Assert.assertEquals("20", builder.getParams().get("limit"));

  }

  @Test
  public void testPagination() {
    PostgrestFilterBuilder builder = newBuilder();
    builder.pagination(1L, 20L);
    Assert.assertEquals("0", builder.getParams().get("offset"));
    Assert.assertEquals("20", builder.getParams().get("limit"));

  }

  @Test
  public void testCs() {
    PostgrestFilterBuilder builder = newBuilder();
    builder.cs("genders", Arrays.asList("男", "女"));
    Assert.assertEquals("cs.{男,女}", builder.getParams().get("genders"));

  }

  @Test
  public void testCd() {
    PostgrestFilterBuilder builder = newBuilder();
    builder.cd("genders", Arrays.asList("男", "女"));
    Assert.assertEquals("cd.{男,女}", builder.getParams().get("genders"));

  }

  @Test
  public void testOv() {
    PostgrestFilterBuilder builder = newBuilder();
    builder.ov("genders", Arrays.asList("男", "女"));
    Assert.assertEquals("ov.{男,女}", builder.getParams().get("genders"));
  }

}
