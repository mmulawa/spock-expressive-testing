package com.dataart.spockframework.expressive.way.of.testing.examples.maps

import com.dataart.spockframework.expressive.way.of.testing.examples.shop.*
import spock.lang.Specification
import spock.lang.Unroll

import static com.dataart.spockframework.expressive.way.of.testing.examples.shop.PaymentStatus.ACCEPTED
import static com.dataart.spockframework.expressive.way.of.testing.examples.shop.PaymentType.BLIK
import static com.dataart.spockframework.expressive.way.of.testing.examples.shop.PaymentType.CARD
import static com.dataart.spockframework.expressive.way.of.testing.examples.shop.PaymentType.CASH

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

    def "should verify mock parameter"() {
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

    def "should not throw exception"() {
        given:
            PaymentRepository repository = Mock(PaymentRepository)
        when:
            repository.find(UUID.randomUUID())
        then:
            noExceptionThrown()
    }

    def "should throw exception"() {
        given:
            PaymentRepository repository = Mock(PaymentRepository)
            repository.find(_ as UUID) >> {
                throw new RuntimeException("data source exception")
            }
        when:
            repository.find(UUID.randomUUID())
        then:
            thrown(RuntimeException)
    }

    def "should verify thrown exception"() {
        given:
            PaymentRepository repository = Mock(PaymentRepository)
            repository.find(_ as UUID) >> {
                throw new RuntimeException("data source exception")
            }
        when:
            repository.find(UUID.randomUUID())
        then:
            def ex = thrown(RuntimeException)
            ex.message == "data source exception"
    }

    def "should stub and verify repository mock"() {
        given:
            PaymentRepository repository = Mock(PaymentRepository)
            PaymentService paymentService = new PaymentService(repository)
            def order = getOrder()
            def uuid = UUID.randomUUID()
        when:
            UUID id = paymentService.payForOrder(order)
        then:
            1 * repository.save(_ as Payment) >> { it ->
                assert it[0].status == ACCEPTED
                uuid
            }
            id == uuid
    }

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
