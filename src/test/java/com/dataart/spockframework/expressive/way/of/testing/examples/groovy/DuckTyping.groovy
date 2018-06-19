package com.dataart.spockframework.expressive.way.of.testing.examples.groovy

import com.dataart.spockframework.expressive.way.of.testing.examples.shop.Item
import com.dataart.spockframework.expressive.way.of.testing.examples.shop.ItemCategory
import com.dataart.spockframework.expressive.way.of.testing.examples.shop.Order
import spock.lang.Specification

import static com.dataart.spockframework.expressive.way.of.testing.examples.shop.ItemCategory.HOME
import static com.dataart.spockframework.expressive.way.of.testing.examples.shop.ItemCategory.MEDICINES

class DuckTyping extends Specification {

    def 'should create order of items'() {
        given:
            def spoon = new Item(HOME, 'spoon', 200)
            def knife = new Item(HOME, 'knife', 100)
            def aspirin = new Item(MEDICINES, 'aspirin', 200)
        when:
            def order = Order.builder()
                    .item(spoon)
                    .item(knife)
                    .item(aspirin)
                    .build()
        then:
            order.items.size() == 3
    }
}
