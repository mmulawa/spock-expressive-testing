package com.dataart.spockframework.expressive.way.of.testing.examples.labels

import com.dataart.spockframework.expressive.way.of.testing.examples.shop.Item
import com.dataart.spockframework.expressive.way.of.testing.examples.shop.ItemCategory
import spock.lang.Specification

class GivenWhenThenSpec extends Specification {

    def 'should create item of given parameters'() {
        given:
            def name = 'spoon'
            def category = ItemCategory.HOME
            def price = 200
        when:
            def item = new Item(category, name, price)
        then:
            item.category == category
            item.name == name
            item.price == price
    }
}
