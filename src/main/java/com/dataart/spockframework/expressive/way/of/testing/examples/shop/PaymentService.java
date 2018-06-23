package com.dataart.spockframework.expressive.way.of.testing.examples.shop;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.UUID;

@Service
public class PaymentService {

    private PaymentRepository paymentRepository;

    @Autowired
    public PaymentService(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    public UUID payForOrder(Order order) {
        PaymentStatus status = (isUnderage(order.getCustomer())
                && order.getItems().stream().filter(item -> item.getCategory() == ItemCategory.MEDICINES).findAny().isPresent())
                ? PaymentStatus.REJECTED : PaymentStatus.ACCEPTED;
        Payment payment = new Payment(order.getPrice(), status, order.getPaymentType());
        UUID paymentId = paymentRepository.save(payment);
        order.setPaymentId(paymentId);
        return paymentId;
    }

    private Boolean isUnderage(Customer customer) {
        return Period.between(customer.getDateOfBirth(), LocalDate.now()).getYears() < 18;
    }
}
