package io.github.ponytailer.postgrest.builder;

import io.github.ponytailer.postgrest.enums.Method;
import io.github.ponytailer.postgrest.exceptions.MethodNotFoundException;
import io.github.ponytailer.postgrest.http.HttpClient;
import io.github.ponytailer.postgrest.response.PostgrestResponse;

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
public class PostgrestBuilder {

  private static final String CONTENT_TYPE = "Content-Type";
  private static final String MIN_TYPE = "application/json";
  private final Map<String, String> headers = new HashMap<>();
  private final Map<String, String> params = new HashMap<>();
  public String uri;
  // insert & update.
  private String body = "";
  private Method method;

  public void addParams(String key, String value) {
    this.params.put(key, value);
  }

  public void addAllParams(Map<String, String> params) {
    this.params.putAll(params);
  }

  public Map<String, String> getParams() {
    return this.params;
  }

  public Method getMethod() {
    return this.method;
  }

  public void setMethod(Method method) {
    this.method = method;
  }

  public String getBody() {
    return this.body;
  }

  public void setBody(String body) {
    this.body = body;
  }

  public Map<String, String> getHeaders() {
    return this.headers;
  }

  public void addHeaders(String name, String value) {
    this.headers.put(name, value);
  }

  public void addAllHeaders(Map<String, String> headers) {
    this.headers.putAll(headers);
  }

  /**
   * call postgrest-server to get response.
   */
  public Optional<PostgrestResponse> execute() throws MethodNotFoundException {
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
    return new HttpClient().execute(method, url, headers, body);
  }

  public <T> T executeAndGetSingle(Class<T> clazz) throws MethodNotFoundException {
    Optional<PostgrestResponse> response = execute();
    return response.map(tPostgrestResponse -> tPostgrestResponse.value(clazz)).orElse(null);
  }

  public <T> List<T> executeAndGetList(Class<T> clazz) throws MethodNotFoundException {
    Optional<PostgrestResponse> response = execute();
    return response.map(tPostgrestResponse -> tPostgrestResponse.valueList(clazz)).orElse(null);
  }
}
