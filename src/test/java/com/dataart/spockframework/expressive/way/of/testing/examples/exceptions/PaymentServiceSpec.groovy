package com.dataart.spockframework.expressive.way.of.testing.examples.exceptions

import com.dataart.spockframework.expressive.way.of.testing.examples.fixtures.OrderData
import com.dataart.spockframework.expressive.way.of.testing.examples.shop.*
import spock.lang.Specification

class PaymentServiceSpec extends Specification implements OrderData {

    PaymentRepository repository = new InMemoryPaymentRepository()
    PaymentService paymentService = new PaymentService(repository)

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
}
