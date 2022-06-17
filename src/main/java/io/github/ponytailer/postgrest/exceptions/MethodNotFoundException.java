// Copyright 2022 Leyantech Ltd. All Rights Reserved.

package io.github.ponytailer.postgrest.exceptions;

/**
 * @author hs, {@literal <hs@leyantech.com>}
 * @date 2022-06-15.
 */
public class MethodNotFoundException extends Exception {

  public MethodNotFoundException(String exc) {
    super(exc);
  }

}
