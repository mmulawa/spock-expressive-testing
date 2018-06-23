package com.dataart.spockframework.expressive.way.of.testing.examples.maps

import com.dataart.spockframework.expressive.way.of.testing.examples.shop.InMemoryPaymentRepository
import com.dataart.spockframework.expressive.way.of.testing.examples.shop.Item
import com.dataart.spockframework.expressive.way.of.testing.examples.shop.Order
import com.dataart.spockframework.expressive.way.of.testing.examples.shop.PaymentRepository
import com.dataart.spockframework.expressive.way.of.testing.examples.shop.PaymentService
import spock.lang.Specification
import spock.lang.Unroll

import java.time.LocalDate
import java.time.Period

import static com.dataart.spockframework.expressive.way.of.testing.examples.shop.ItemCategory.MEDICINES
import static com.dataart.spockframework.expressive.way.of.testing.examples.shop.PaymentStatus.ACCEPTED
import static com.dataart.spockframework.expressive.way.of.testing.examples.shop.PaymentStatus.REJECTED

class DummyDataSeparation extends Specification implements OrderData {

    public static final LocalDate KID_DOB = LocalDate.now().minusYears(10)
    public static final LocalDate ADULT_DOB = LocalDate.now().minusYears(20)

    PaymentRepository repository = new InMemoryPaymentRepository()
    PaymentService paymentService = new PaymentService(repository)

    def "should not allow to pay for medicines to kid"() {
        given:
            def order = getOrder([customer: getCustomer(dateOfBirth: KID_DOB),
                                  items   : [getItem(category: MEDICINES)]])
        when:
            UUID id = paymentService.payForOrder(order)
        then:
            repository.find(id).status == REJECTED
    }

    @Unroll
    def "should #result to pay for medicines to #age"() {
        given:
            def order = getOrder([customer: getCustomer(dateOfBirth: dateOfBirth),
                                  items   : [getItem(category: MEDICINES)]])
        when:
            UUID id = paymentService.payForOrder(order)
        then:
            repository.find(id).status == expectedStatus
        where:
            dateOfBirth || expectedStatus
            KID_DOB     || REJECTED
            ADULT_DOB   || ACCEPTED
            age = isUnderAge(dateOfBirth) ? 'kid' : 'adult'
            result = (expectedStatus == ACCEPTED) ? 'allow' : 'not allow'
    }


    private boolean isUnderAge(dateOfBirth) {
        Period.between(dateOfBirth, LocalDate.now()).years < 18
    }
}
