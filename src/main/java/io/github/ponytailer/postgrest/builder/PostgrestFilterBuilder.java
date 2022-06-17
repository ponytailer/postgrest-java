package io.github.ponytailer.postgrest.builder;

import io.github.ponytailer.postgrest.enums.FilterOperator;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author hs, {@literal <hs@leyantech.com>}
 * @date 2022-06-15.
 */

public class PostgrestFilterBuilder extends PostgrestBuilder {

  /**
   * constructor.
   */
  public PostgrestFilterBuilder(PostgrestBuilder builder) {
    this.uri = builder.uri;
    setMethod(builder.getMethod());
    addAllHeaders(builder.getHeaders());
    addAllParams(builder.getParams());
  }

  /**
   * not("id", FilterOperator.EQ, 1) -> id != 1.
   */
  public <V> PostgrestFilterBuilder not(String column, FilterOperator operator, V value) {
    addParams(column, "not." + operator.name().toLowerCase() + value);
    return this;
  }

  /**
   * eq("id", 1) -> id=1.
   */
  public <V> PostgrestFilterBuilder eq(String column, V value) {
    addParams(column, "eq." + value);
    return this;
  }

  /**
   * neq("id", 1) -> id != 1.
   */
  public <V> PostgrestFilterBuilder neq(String column, V value) {
    addParams(column, "neq." + value);
    return this;
  }

  /**
   * gt("id", 1) -> id > 1.
   */
  public <V> PostgrestFilterBuilder gt(String column, V value) {
    addParams(column, "gt." + value);
    return this;
  }

  /**
   * gte("id",  1) -> id >= 1.
   */
  public <V> PostgrestFilterBuilder gte(String column, V value) {
    addParams(column, "gte." + value);
    return this;
  }

  /**
   * lt("id",  1) -> id < 1.
   */
  public <V> PostgrestFilterBuilder lt(String column, V value) {
    addParams(column, "lt." + value);
    return this;
  }

  /**
   * lte("id",  1) -> id <= 1.
   */
  public <V> PostgrestFilterBuilder lte(String column, V value) {
    addParams(column, "lte." + value);
    return this;
  }

  /**
   * like("name", "123") -> like '%123%'.
   */
  public <V> PostgrestFilterBuilder like(String column, V value) {
    addParams(column, "like." + value);
    return this;
  }

  /**
   * ilike("name", "123") -> ilike '%123%'.
   */
  public <V> PostgrestFilterBuilder ilike(String column, V value) {
    addParams(column, "ilike." + value);
    return this;
  }

  /**
   * is("name", NULL) -> name is NULL.
   */
  public <V> PostgrestFilterBuilder is(String column, V value) {
    addParams(column, "is." + value);
    return this;
  }

  private <V> String cleanFilterArray(List<V> valueList) {
    return valueList.stream().map(String::valueOf).collect(Collectors.joining(","));
  }

  /**
   * in("id", [1, 2, 3]) -> id in (1, 2, 3).
   */
  public <V> PostgrestFilterBuilder in(String column, List<V> valueList) {
    addParams(column, "in.(" + cleanFilterArray(valueList) + ")");
    return this;
  }

}
