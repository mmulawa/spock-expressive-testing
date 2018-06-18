package com.dataart.spockframework.expressive.way.of.testing.examples.shop;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class Payment {
    private Integer amount;
    private PaymentStatus status;
    private PaymentType type;
}
