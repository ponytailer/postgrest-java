// Copyright 2022 Leyantech Ltd. All Rights Reserved.

package io.github.ponytailer.postgrest.query;

import java.util.List;

/**
 * @author hs, {@literal <hs@leyantech.com>}
 * @date 2022-06-15.
 */
public class ItemDTO {

  private final List<String> genders;
  private final Long numIid;

  /**
   * test.
   */
  public ItemDTO(Long numIid, List<String> genders) {
    this.numIid = numIid;
    this.genders = genders;

  }

  @Override
  public String toString() {
    return "numIid: " + numIid + "; genders: " + genders.toString();
  }
}

