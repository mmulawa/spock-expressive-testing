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
                    items   : [itemMap() << [category: MEDICINES]]
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
                    items   : [itemMap() << [category: MEDICINES]]
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
                        itemMap()
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

    private Map itemMap() {
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
