// Copyright 2022 Leyantech Ltd. All Rights Reserved.

package com.leyantech.postgrest.builder;

import com.leyantech.postgrest.enums.Method;

import java.net.URI;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

/**
 * @author hs, {@literal <hs@leyantech.com>}
 * @date 2022-06-15.
 */
public class PostgrestBuilder {

  public URI uri;
  public Map<String, String> headers = new HashMap<>();

  public Map<String, String> params = new HashMap<>();

  public Method method;

}
