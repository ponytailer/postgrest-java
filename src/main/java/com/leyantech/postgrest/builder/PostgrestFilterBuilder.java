package com.leyantech.postgrest.builder;

/**
 * @author hs, {@literal <hs@leyantech.com>}
 * @date 2022-06-15.
 */

public class PostgrestFilterBuilder<T> extends PostgrestBuilder<T> {

  public PostgrestFilterBuilder(PostgrestBuilder<T> builder) {
    this.uri = builder.uri;
    this.method = builder.method;
    this.headers.putAll(builder.headers);
    this.params.putAll(builder.params);
  }

  public PostgrestFilterBuilder<T> eq(String column, T value) {
    params.put(column, "eq." + value);
    return this;
  }

}
