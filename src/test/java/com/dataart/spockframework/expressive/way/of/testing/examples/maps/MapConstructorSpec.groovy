package com.dataart.spockframework.expressive.way.of.testing.examples.maps

import com.dataart.spockframework.expressive.way.of.testing.examples.shop.Item
import com.dataart.spockframework.expressive.way.of.testing.examples.shop.Order
import spock.lang.Specification

import java.time.LocalDate

import static com.dataart.spockframework.expressive.way.of.testing.examples.shop.ItemCategory.FOOD
import static com.dataart.spockframework.expressive.way.of.testing.examples.shop.ItemCategory.HOME
import static com.dataart.spockframework.expressive.way.of.testing.examples.shop.ItemCategory.MEDICINES
import static com.dataart.spockframework.expressive.way.of.testing.examples.shop.PaymentType.CARD

class MapConstructorSpec extends Specification {

    def 'should construct order of given map'() {
        given:
            def map = [customer: [firstName: 'Jan',
                                  address  : [city  : 'Lublin',
                                              street: 'Królewska']],
                       items   : [[name: 'Apple'], [name: 'Plum']] as Item[],
                       price   : 100]
        when:
            def explicit = new Order(map)
            Order implicit = map
            def explicitWithAs = map as Order
        then:
            explicit == implicit
            implicit == explicitWithAs
            explicit.customer.firstName == 'Jan'
            explicit['customer']['address']['city'] == 'Lublin'
            explicit.customer.address.street == 'Królewska'
            explicit.price == 100
            explicit.items[0].name == 'Apple'
            explicit.items.name == ['Apple', 'Plum']
    }
}
