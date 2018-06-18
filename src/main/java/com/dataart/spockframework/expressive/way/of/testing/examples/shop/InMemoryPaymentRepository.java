package com.dataart.spockframework.expressive.way.of.testing.examples.shop;

import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class InMemoryPaymentRepository implements PaymentRepository {

    private Map<UUID, Payment> map = new ConcurrentHashMap<>();

    @Override
    public UUID save(Payment payment) {
        UUID id = UUID.randomUUID();
        map.put(id, payment);
        return id;
    }

    @Override
    public Payment find(UUID id) {
        return map.get(id);
    }
}
