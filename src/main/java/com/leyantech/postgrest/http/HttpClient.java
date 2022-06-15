package com.leyantech.postgrest.http;

import com.leyantech.postgrest.enums.Method;
import com.leyantech.postgrest.response.PostgrestResponse;

import com.google.inject.Singleton;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;
import javafx.beans.property.ObjectProperty;

import java.io.IOException;
import java.util.Objects;
import java.util.Optional;
import java.util.logging.Logger;

/**
 * @author hs, {@literal <hs@leyantech.com>}
 * @date 2022-06-15.
 */

@Singleton
public class HttpClient {

  private static final Logger LOGGER = Logger.getLogger(HttpClient.class.toString());

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

  public Optional<PostgrestResponse> execute(Method method, String url, String body) {
    RequestBody requestBody = RequestBody.create(APPLICATION_JSON, body);
    Request request = new Request.Builder().url(url).method(method.name(), requestBody).build();
    try {
      Response response = client.newCall(request).execute();
      return Optional.of(
          new PostgrestResponse(response.code(), response.body().string(), getCount(response)));
    } catch (IOException e) {
      LOGGER.warning("call postgrest-server error: " + e);
      return Optional.empty();
    }
  }

}
