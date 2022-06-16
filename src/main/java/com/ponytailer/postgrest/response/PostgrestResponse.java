package com.ponytailer.postgrest.response;

import com.alibaba.fastjson.JSONObject;

import java.util.List;

/**
 * @author ponytailer, {@literal <hs@leyantech.com>}
 * @date 2022-06-15.
 */

public class PostgrestResponse {

  private final int status;
  private final String body;
  private final Long count;

  /**
   * constructor.
   */
  public PostgrestResponse(int status, String body, Long count) {
    this.status = status;
    this.body = body;
    this.count = count;
  }

  /**
   * get body.
   */
  public String getBody() {
    return body;
  }

  /**
   * get value with class.
   */
  public <T> T value(Class<T> clazz) {
    return JSONObject.parseObject(body, clazz);
  }

  /**
   * get list value with class.
   */
  public <T> List<T> valueList(Class<T> clazz) {
    return JSONObject.parseArray(body, clazz);
  }

  /**
   * get http status.
   */
  public int getStatus() {
    return status;

  }

  /**
   * get count.
   */
  public Long getCount() {
    return count;
  }
}
