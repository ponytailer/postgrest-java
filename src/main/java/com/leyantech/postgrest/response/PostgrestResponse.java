package com.leyantech.postgrest.response;

import com.alibaba.fastjson.JSONObject;

import java.util.List;

/**
 * @author ponytailer, {@literal <hs@leyantech.com>}
 * @date 2022-06-15.
 */

public class PostgrestResponse<T> {

  private int status;
  private String body;
  private Long count;

  public PostgrestResponse() {
  }

  public PostgrestResponse<T> populate(int status, String body, Long count) {
    this.status = status;
    this.body = body;
    this.count = count;
  }

  public String getBody() {
    return body;
  }

  public T value(Class<T> clazz) {
    return JSONObject.parseObject(body, clazz);
  }

  public List<T> valueList(Class<T> clazz) {
    return JSONObject.parseArray(body, clazz);
  }

  public int getStatus() {
    return status;

  }

  public Long getCount() {
    return count;
  }
}
