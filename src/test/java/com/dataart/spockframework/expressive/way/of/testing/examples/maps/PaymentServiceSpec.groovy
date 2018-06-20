package com.dataart.spockframework.expressive.way.of.testing.examples.maps

import com.dataart.spockframework.expressive.way.of.testing.examples.shop.*
import spock.lang.Specification
import spock.lang.Unroll

import java.time.LocalDate
import java.time.Period

import static com.dataart.spockframework.expressive.way.of.testing.examples.shop.ItemCategory.FOOD
import static com.dataart.spockframework.expressive.way.of.testing.examples.shop.ItemCategory.MEDICINES
import static com.dataart.spockframework.expressive.way.of.testing.examples.shop.PaymentStatus.ACCEPTED
import static com.dataart.spockframework.expressive.way.of.testing.examples.shop.PaymentStatus.REJECTED
import static com.dataart.spockframework.expressive.way.of.testing.examples.shop.PaymentType.BLIK
import static com.dataart.spockframework.expressive.way.of.testing.examples.shop.PaymentType.CARD
import static com.dataart.spockframework.expressive.way.of.testing.examples.shop.PaymentType.CASH

class PaymentServiceSpec extends Specification {

    public static final LocalDate KID_DOB = LocalDate.now().minusYears(10)
    public static final LocalDate ADULT_DOB = LocalDate.now().minusYears(20)
    PaymentRepository repository = new InMemoryPaymentRepository()
    PaymentService paymentService = new PaymentService(repository)

    def "should verify mock"() {
        given:
            PaymentRepository repository = Mock(PaymentRepository)
            PaymentService paymentService = new PaymentService(repository)
            Order order = orderMap
        when:
            UUID id = paymentService.payForOrder(order)
        then:
            1 * repository.save(_ as Payment)
    }

    def "should verify mock parameter"() {
        given:
            PaymentRepository repository = Mock(PaymentRepository)
            PaymentService paymentService = new PaymentService(repository)
            Order order = orderMap
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
            Order order = orderMap
        when:
            UUID id = paymentService.payForOrder(order)
        then:
            id == uuid
    }

    def "should stub with different value per invocaion"() {
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
            thrown()
    }

    def "should throw concrete exception"() {
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
            Order order = orderMap
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
            Order order = getOrderMap() << [paymentType: paymentType]
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
            Order order = getOrderMap() << [paymentType: paymentType]
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
            Order order = orderMap
        when:
            UUID id = paymentService.payForOrder(order)
        then:
            repository.find(id).status == ACCEPTED
        where:
            orderMap                             | _
            getOrderMap() << [paymentType: CASH] | _
            getOrderMap() << [paymentType: CARD] | _
            getOrderMap() << [paymentType: BLIK] | _
            paymentType = orderMap.paymentType

    }

    def "should not allow to pay for medicines to kid"() {
        given:
            Order order = orderMap << [
                    customer: customerMap << [
                            dateOfBirth: KID_DOB
                    ],
                    items   : [itemMap << [category: MEDICINES]]
            ]
        when:
            UUID id = paymentService.payForOrder(order)
        then:
            repository.find(id).status == REJECTED
    }

    @Unroll
    def "should #result to pay for medicines to #age"() {
        given:
            Order order = orderMap << [
                    customer: customerMap << [
                            dateOfBirth: dateOfBirth
                    ],
                    items   : [getItemMap() << [category: MEDICINES]]
            ]
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

    private Map getOrderMap() {
        [
                customer       : getCustomerMap(),
                shipmentAddress: getAddressMap(),
                items          : [
                        getItemMap()
                ],
                price          : 100,
                paymentType    : CARD
        ]
    }

    private Map getCustomerMap() {
        [
                firstName  : 'Jan',
                lastName   : 'Kowalski',
                dateOfBirth: LocalDate.of(1996, 5, 23),
                address    : getAddressMap()
        ]
    }

    private Map getAddressMap() {
        [
                country     : 'USA',
                province    : 'California',
                city        : 'Folsom',
                postalCode  : 'CA 95630',
                street      : 'St Louis Street',
                streetNumber: '681',
                homeNumber  : '23'
        ]
    }

    private Map getItemMap() {
        [
                category: FOOD,
                name    : 'Item name',
                price   : 200
        ]
    }

    private boolean isUnderAge(dateOfBirth) {
        Period.between(dateOfBirth, LocalDate.now()).years < 18
    }

}
