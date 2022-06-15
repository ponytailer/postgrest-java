package com.leyantech.postgrest.builder;

/**
 * @author hs, {@literal <hs@leyantech.com>}
 * @date 2022-06-15.
 */

public class PostgrestFilterBuilder extends PostgrestBuilder {

  public PostgrestFilterBuilder(PostgrestBuilder builder) {
    this.uri = builder.uri;
    this.method = builder.method;
    this.headers.putAll(builder.headers);
    this.params.putAll(builder.params);
  }

  public <V> PostgrestFilterBuilder eq(String column, V value) {
    params.put(column, "eq." + value);
    return this;
  }

  public <V> PostgrestFilterBuilder neq(String column, V value) {
    params.put(column, "neq." + value);
    return this;
  }

}
