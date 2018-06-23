package com.dataart.spockframework.expressive.way.of.testing.examples.maps

import com.dataart.spockframework.expressive.way.of.testing.examples.shop.Item
import com.dataart.spockframework.expressive.way.of.testing.examples.shop.ItemCategory
import com.dataart.spockframework.expressive.way.of.testing.examples.shop.Order
import spock.lang.Specification

import static com.dataart.spockframework.expressive.way.of.testing.examples.shop.ItemCategory.HOME

class LeftShiftMapOperatorSpec extends Specification {

    def 'should construct order of given modified map'() {
        given:
            def map = [customer: [firstName: 'Jan',
                                  address  : [city  : 'Lublin',
                                              street: 'Królewska']],
                       items   : [[name: 'Apple']] as Item[],
                       price   : 100]
        when:
            map << [items: [[name: 'Pear'] as Item]]
            map.customer << [address: [city  : 'Świdnik',
                                       street: 'Aleja Lotników Polskich']]
            def order = new Order(map)
        then:
            order.customer.firstName == 'Jan'
            order['customer']['address']['city'] == 'Świdnik'
            order.customer.address.street == 'Aleja Lotników Polskich'
            order.price == 100
            order.items[0].name == 'Pear'
    }
}
