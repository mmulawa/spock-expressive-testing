package com.dataart.spockframework.expressive.way.of.testing.examples.shop;

import java.util.UUID;

public interface PaymentRepository {
    UUID save(Payment payment);
    Payment find(UUID id);
}
