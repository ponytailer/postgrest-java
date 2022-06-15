// Copyright 2022 Leyantech Ltd. All Rights Reserved.

package com.leyantech.postgrest;

import com.leyantech.postgrest.enums.Count;
import com.leyantech.postgrest.exceptions.MethodNotFoundException;
import com.leyantech.postgrest.response.PostgrestResponse;

import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * @author hs, {@literal <hs@leyantech.com>}
 * @date 2022-06-15.
 */
public class Main {

  public static void main(String[] args) throws URISyntaxException, MethodNotFoundException {

    PostgrestClient client = new PostgrestClient("http://192.168.9.47");

    Optional<PostgrestResponse> response = client.from("item_with_categories")
        .select("*", false, Count.EXACT)
        .execute();

    if (response.isPresent()) {
      PostgrestResponse resp = response.get();
      List<ItemDTO> items = resp.valueList(ItemDTO.class);

      items.forEach(System.out::println);
    }
  }

}
