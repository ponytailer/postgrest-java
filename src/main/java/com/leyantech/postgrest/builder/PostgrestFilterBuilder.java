package com.leyantech.postgrest.builder;


import com.leyantech.postgrest.enums.Method;
import com.leyantech.postgrest.exceptions.MethodNotFoundException;
import com.leyantech.postgrest.http.HttpClient;
import com.leyantech.postgrest.response.PostgrestResponse;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

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

}
