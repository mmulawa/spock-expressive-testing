package com.dataart.spockframework.expressive.way.of.testing.examples.shop;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class Item {
    private ItemCategory category;
    private String name;
    private Integer price;
}
