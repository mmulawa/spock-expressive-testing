package com.dataart.spockframework.expressive.way.of.testing.examples.maps

import com.dataart.spockframework.expressive.way.of.testing.examples.shop.Order
import spock.lang.Specification

import static com.dataart.spockframework.expressive.way.of.testing.examples.shop.ItemCategory.FOOD
import static com.dataart.spockframework.expressive.way.of.testing.examples.shop.ItemCategory.HOME
import static com.dataart.spockframework.expressive.way.of.testing.examples.shop.ItemCategory.MEDICINES
import static com.dataart.spockframework.expressive.way.of.testing.examples.shop.PaymentType.CARD

class MapConstructorSpec extends Specification {


    def 'should construct order and items of given map'() {
        given:
            def map = [items: [[
                                       category: FOOD,
                                       name    : 'Item name',
                                       price   : 200]],
                       price: 100]
        expect:
            map instanceof LinkedHashMap
        when:
            def order = new Order(map)
        then:
            order.price == 100
            order.items[0].category == FOOD
            order.items[0].name == 'Item name'
            order.items[0].price == 200
    }
}
