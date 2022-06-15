// Copyright 2022 Leyantech Ltd. All Rights Reserved.

package com.leyantech.postgrest.response;

/**
 * @author hs, {@literal <hs@leyantech.com>}
 * @date 2022-06-15.
 */

public class PostgrestResponse {

  int status;
  String body;
  Long count;

  public PostgrestResponse(int status, String body, Long count) {
    this.status = status;
    this.body = body;
    this.count = count;
  }
}
