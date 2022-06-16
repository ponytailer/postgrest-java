// Copyright 2022 Leyantech Ltd. All Rights Reserved.

package com.ponytailer.postgrest.query;

import com.ponytailer.postgrest.PostgrestClient;
import com.ponytailer.postgrest.exceptions.MethodNotFoundException;

import org.junit.Test;

import java.util.List;

/**
 * @author hs, {@literal <hs@leyantech.com>}
 * @date 2022-06-15.
 */
public class PgTest {

  @Test
  public void testGet() throws MethodNotFoundException {
    PostgrestClient client = new PostgrestClient("http://192.168.9.47");

    List<ItemDTO> items = client.from("item_with_categories")
        .select("*")
        .eq("seller_id", 1)
        .executeAndGetList(ItemDTO.class);

    items.forEach(System.out::println);

  }

}
