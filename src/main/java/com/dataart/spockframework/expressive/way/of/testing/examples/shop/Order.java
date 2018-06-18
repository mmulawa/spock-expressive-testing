package com.dataart.spockframework.expressive.way.of.testing.examples.shop;

import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
@Builder
public class Order {
    private Customer customer;
    private Address shipmentAddress;
    private List<Item> items;
    private Integer price;
    private PaymentType paymentType;
    private UUID paymentId;
}
