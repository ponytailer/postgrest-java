package com.ponytailer.postgrest.http;

import com.ponytailer.postgrest.enums.Method;
import com.ponytailer.postgrest.response.PostgrestResponse;
import com.leyantech.utility.Logger;

import com.google.inject.Singleton;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

/**
 * @author hs, {@literal <hs@leyantech.com>}
 * @date 2022-06-15.
 */

@Singleton
public class HttpClient {

  private static final Logger LOGGER = new Logger(HttpClient.class);

  private static final OkHttpClient client = new OkHttpClient();

  private static final MediaType APPLICATION_JSON = MediaType.parse(
      "application/json; charset=utf-8");

  private static final String COUNT_HEADER_KEY = "content-range";

  private Long getCount(Response response) {
    String value = response.headers().get(COUNT_HEADER_KEY);
    if (Objects.isNull(value)) {
      return 0L;
    }
    String[] valueArray = value.split("/");
    if (valueArray.length > 1) {
      return Long.parseLong(valueArray[1]);
    }
    return 0L;
  }

  /**
   * execute request to postgrest-sever.
   */
  public Optional<PostgrestResponse> execute(Method method, String url,
      Map<String, String> headers,
      String body) {
    Request.Builder builder;
    if (Method.GET.equals(method) || Method.HEAD.equals(method)) {
      builder = new Request.Builder().url(url).get();
    } else {
      RequestBody requestBody = RequestBody.create(APPLICATION_JSON, body);
      builder = new Request.Builder().url(url).method(method.name(), requestBody);
    }
    headers.forEach((key, value) -> builder.addHeader(key, value));

    try {
      Response response = client.newCall(builder.build()).execute();
      PostgrestResponse resp = new PostgrestResponse(response.code(), response.body().string(),
          getCount(response));
      return Optional.of(resp);
    } catch (IOException e) {
      LOGGER.error(e, "call postgrest-server error");
      return Optional.empty();
    }
  }

}
