package com.leyantech.postgrest.builder;

import com.leyantech.postgrest.enums.Method;
import com.leyantech.postgrest.exceptions.MethodNotFoundException;
import com.leyantech.postgrest.http.HttpClient;
import com.leyantech.postgrest.response.PostgrestResponse;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author hs, {@literal <hs@leyantech.com>}
 * @date 2022-06-15.
 */
public class PostgrestBuilder<T> {

  private static final String CONTENT_TYPE = "Content-Type";
  private static final String MIN_TYPE = "application/json";
  public String uri;
  public Map<String, String> headers = new HashMap<>();
  public Map<String, String> params = new HashMap<>();
  public Method method;
  // insert & update.
  public String body = "";

  public Optional<PostgrestResponse<T>> execute() throws MethodNotFoundException {
    if (Objects.isNull(method)) {
      throw new MethodNotFoundException("not support this method");
    }
    if (method != Method.GET && method != Method.HEAD) {
      this.headers.put(CONTENT_TYPE, MIN_TYPE);
    }

    String arguments = params.entrySet().stream().map(entry -> {
      try {
        String encodeValue = URLEncoder.encode(entry.getValue(), "UTF-8");
        return entry.getKey() + "=" + encodeValue;
      } catch (UnsupportedEncodingException e) {
        String encodeValue = entry.getValue();
        return entry.getKey() + "=" + encodeValue;
      }
    }).collect(Collectors.joining("&"));

    String url = uri + "?" + arguments;
    Optional<PostgrestResponse<T>> response = new HttpClient().execute(method, url, headers, body);
    return response;
  }

  public T executeAndGetSingle(Class<T> clazz) throws MethodNotFoundException {
    Optional<PostgrestResponse<T>> response = execute();
    return response.map(tPostgrestResponse -> tPostgrestResponse.value(clazz)).orElse(null);
  }

  public List<T> executeAndGetList(Class<T> clazz) throws MethodNotFoundException {
    Optional<PostgrestResponse<T>> response = execute();
    return response.map(tPostgrestResponse -> tPostgrestResponse.valueList(clazz)).orElse(null);
  }
}
