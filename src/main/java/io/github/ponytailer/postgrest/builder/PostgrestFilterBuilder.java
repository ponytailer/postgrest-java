package io.github.ponytailer.postgrest.builder;

import com.google.common.base.Strings;
import io.github.ponytailer.postgrest.enums.FilterOperator;

import java.util.List;
import java.util.Objects;
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

  /**
   * limit.
   */
  public PostgrestFilterBuilder limit(Long size) {
    addParams("limit", size.toString());
    return this;
  }

  /**
   * limit / foreignTable.
   */
  public PostgrestFilterBuilder limit(Long size, String foreignTable) {
    String key = Objects.isNull(foreignTable) ? "limit" : foreignTable + ".limit";
    addParams(key, size.toString());
    return this;
  }

  /**
   * order, 默认是nullfirst.
   */
  public PostgrestFilterBuilder order(String column, Boolean ascending, String foreignTable) {
    String key = Strings.isNullOrEmpty(foreignTable) ? "order" : foreignTable + ".order";

    addParams(key, column + (ascending ? ".asc" : ".desc"));
    return this;
  }

  /**
   * order_by.
   */
  public PostgrestFilterBuilder order(String column, Boolean ascending) {
    addParams("order", column + (ascending ? ".asc" : ".desc"));
    return this;
  }

  /**
   * order desc.
   */
  public PostgrestFilterBuilder order(String column) {
    addParams("order", column + ".desc");
    return this;
  }

  /**
   * range.
   */
  public PostgrestFilterBuilder range(Long from, Long to) {
    addParams("offset", from.toString());
    addParams("limit", String.valueOf(to - from + 1));
    return this;
  }

  /**
   * pagination.
   */
  public PostgrestFilterBuilder pagination(Long page, Long size) {
    addParams("offset", String.valueOf((page - 1) * size));
    addParams("limit", size.toString());
    return this;
  }

  /**
   * 等同于 @>.
   */
  public <V> PostgrestFilterBuilder cs(String column, List<V> values) {
    if (values.isEmpty()) {
      return this;
    }
    addParams(column, "cs.{" + cleanFilterArray(values) + "}");
    return this;
  }

  /**
   * 等同于 @<.
   */
  public <V> PostgrestFilterBuilder cd(String column, List<V> values) {
    if (values.isEmpty()) {
      return this;
    }
    addParams(column, "cd.{" + cleanFilterArray(values) + "}");
    return this;
  }

  /**
   * overlap, 等同于 &&.
   */
  public <V> PostgrestFilterBuilder ov(String column, List<V> values) {
    if (values.isEmpty()) {
      return this;
    }
    addParams(column, "ov.{" + cleanFilterArray(values) + "}");
    return this;
  }

}
