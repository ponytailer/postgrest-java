// Copyright 2022 Leyantech Ltd. All Rights Reserved.

package com.leyantech.postgrest;

import com.leyantech.postgrest.enums.Count;
import com.leyantech.postgrest.exceptions.MethodNotFoundException;

import java.net.URISyntaxException;
import java.util.List;

/**
 * @author hs, {@literal <hs@leyantech.com>}
 * @date 2022-06-15.
 */
public class Main {

  public static void main(String[] args) throws URISyntaxException, MethodNotFoundException {

    PostgrestClient client = new PostgrestClient("http://192.168.9.47");

    List<ItemDTO> items = client.from("item_with_categories")
        .select("*", false, Count.EXACT)
        .eq("seller_id", 1)
        .executeAndGetList(ItemDTO.class);

    items.forEach(System.out::println);

  }

}
