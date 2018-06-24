package com.dataart.spockframework.expressive.way.of.testing.examples.mocks

import com.dataart.spockframework.expressive.way.of.testing.examples.fixtures.OrderData
import com.dataart.spockframework.expressive.way.of.testing.examples.shop.InMemoryPaymentRepository
import com.dataart.spockframework.expressive.way.of.testing.examples.shop.Payment
import com.dataart.spockframework.expressive.way.of.testing.examples.shop.PaymentRepository
import com.dataart.spockframework.expressive.way.of.testing.examples.shop.PaymentService
import com.sun.org.apache.regexp.internal.RE
import spock.lang.Specification

import static com.dataart.spockframework.expressive.way.of.testing.examples.shop.PaymentStatus.ACCEPTED
import static com.dataart.spockframework.expressive.way.of.testing.examples.shop.PaymentStatus.REJECTED
import static com.dataart.spockframework.expressive.way.of.testing.examples.shop.PaymentType.BLIK

class PaymentServiceSpec extends Specification implements OrderData {

    PaymentRepository repository = new InMemoryPaymentRepository()
    PaymentService paymentService = new PaymentService(repository)

    def "should verify mock"() {
        given:
            PaymentRepository repository = Mock(PaymentRepository)
            PaymentService paymentService = new PaymentService(repository)
            def order = getOrder()
        when:
            UUID id = paymentService.payForOrder(order)
        then:
            1 * repository.save(_ as Payment)
    }

    def "should verify mock parameter #1"() {
        given:
            PaymentRepository repository = Mock(PaymentRepository)
            PaymentService paymentService = new PaymentService(repository)
            def order = getOrder()
        when:
            UUID id = paymentService.payForOrder(order)
        then:
            1 * repository.save({ it.status == ACCEPTED })
    }

    def "should verify mock parameter #2"() {
        given:
            PaymentRepository repository = Mock(PaymentRepository)
            PaymentService paymentService = new PaymentService(repository)
            def order = getOrder()
        when:
            UUID id = paymentService.payForOrder(order)
        then:
            1 * repository.save(_ as Payment) >> { it ->
                assert it[0].status == ACCEPTED
            }
    }

    def "should verify mock parameter #3"() {
        given:
            PaymentRepository repository = Mock(PaymentRepository)
            PaymentService paymentService = new PaymentService(repository)
            def order = getOrder()
        when:
            UUID id = paymentService.payForOrder(order)
        then:
            1 * repository.save(_ as Payment) >> { Payment payment ->
                assert payment.status == ACCEPTED
            }
    }

    def "should stub return value"() {
        given:
            PaymentRepository repository = Mock(PaymentRepository)
            def uuid = UUID.randomUUID()
            repository.save(_ as Payment) >> uuid
            PaymentService paymentService = new PaymentService(repository)
            def order = getOrder()
        when:
            UUID id = paymentService.payForOrder(order)
        then:
            id == uuid
    }

    def "should stub with different value per invocation"() {
        given:
            PaymentRepository repository = Mock(PaymentRepository)
            def blikPayment = new Payment(200, ACCEPTED, BLIK)
            repository.find(_ as UUID) >> blikPayment >> null >> {
                throw new RuntimeException("data source exception")
            }
        when:
            def first = repository.find(UUID.randomUUID())
            def second = repository.find(UUID.randomUUID())
        then:
            first == blikPayment
            second == null
        when:
            repository.find(UUID.randomUUID())
        then:
            thrown RuntimeException
    }

    def "should stub and verify mock #1"() {
        given:
            PaymentRepository repository = Mock(PaymentRepository)
            PaymentService paymentService = new PaymentService(repository)
            def order = getOrder()
            def uuid = UUID.randomUUID()
        when:
            UUID id = paymentService.payForOrder(order)
        then:
            1 * repository.save({ it.status == ACCEPTED }) >> uuid
            id == uuid
    }

    def "should stub and verify mock #2"() {
        given:
            PaymentRepository repository = Mock(PaymentRepository)
            PaymentService paymentService = new PaymentService(repository)
            def order = getOrder()
            def uuid = UUID.randomUUID()
        when:
            UUID id = paymentService.payForOrder(order)
        then:
            1 * repository.save({ it.status == ACCEPTED }) >> { Payment payment ->
                assert payment.status == ACCEPTED
                uuid
            }
            id == uuid
    }

}
