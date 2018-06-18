package com.dataart.spockframework.expressive.way.of.testing.examples.labels

import com.dataart.spockframework.expressive.way.of.testing.examples.shop.Item
import com.dataart.spockframework.expressive.way.of.testing.examples.shop.ItemCategory
import spock.lang.Specification

class GivenWhenThenAndSpec extends Specification {

    def 'should create new item of given parameters'() {
        given:
            def name = 'spoon'
        and:
            def category = ItemCategory.HOME
        and:
            def price = 200
        when:
            def item = new Item(category, name, price)
        then:
            item.category == category
        and:
            item.name == name
        and:
            item.price == price
    }

}
