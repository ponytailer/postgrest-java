// Copyright 2022 Leyantech Ltd. All Rights Reserved.

package com.leyantech.postgrest.exceptions;

/**
 * @author hs, {@literal <hs@leyantech.com>}
 * @date 2022-06-15.
 */
public class MethodNotFoundException extends Exception {

  public MethodNotFoundException(String exc) {
    super(exc);
  }

}
