package com.dataart.spockframework.expressive.way.of.testing.examples.parametrization

import com.dataart.spockframework.expressive.way.of.testing.examples.fixtures.OrderData
import com.dataart.spockframework.expressive.way.of.testing.examples.shop.*
import spock.lang.Specification
import spock.lang.Unroll

import static com.dataart.spockframework.expressive.way.of.testing.examples.shop.PaymentStatus.ACCEPTED
import static com.dataart.spockframework.expressive.way.of.testing.examples.shop.PaymentType.*

class PaymentServiceSpec extends Specification implements OrderData {

    PaymentRepository repository = new InMemoryPaymentRepository()
    PaymentService paymentService = new PaymentService(repository)

    @Unroll
    def "should pay for order with #paymentType and store payment in repository"(PaymentType paymentType) {
        given:
            PaymentRepository repository = Mock(PaymentRepository)
            PaymentService paymentService = new PaymentService(repository)
            def order = getOrder(paymentType: paymentType)
        when:
            UUID id = paymentService.payForOrder(order)
        then:
            1 * repository.save(new Payment(100, ACCEPTED, paymentType))
        where:
            paymentType << PaymentType.findAll()
    }

    @Unroll
    def "should pay for order with #paymentType and store payment in repository"() {
        given:
            def order = getOrder(paymentType: paymentType)
        when:
            UUID id = paymentService.payForOrder(order)
        then:
            repository.find(id).status == ACCEPTED
        where:
            paymentType << PaymentType.findAll()
    }

    @Unroll
    def "should pay for order with #paymentType"() {
        given:
            def order = getOrder(paymentType: paymentType)
        when:
            UUID id = paymentService.payForOrder(order)
        then:
            repository.find(id).status == ACCEPTED
        where:
            paymentType | _
            CASH        | _
            CARD        | _
            BLIK        | _
    }
}
