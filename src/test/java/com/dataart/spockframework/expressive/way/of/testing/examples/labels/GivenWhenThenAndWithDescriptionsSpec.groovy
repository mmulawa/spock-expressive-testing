package com.dataart.spockframework.expressive.way.of.testing.examples.labels

import com.dataart.spockframework.expressive.way.of.testing.examples.shop.Item
import com.dataart.spockframework.expressive.way.of.testing.examples.shop.ItemCategory
import spock.lang.Specification

class GivenWhenThenAndWithDescriptionsSpec extends Specification {

    def 'should create new item of given parameters'() {
        given: 'item name'
            def name = 'spoon'
        and: 'item category'
            def category = ItemCategory.HOME
        and: 'item price'
            def price = 200
        when: 'item constructor was called'
            def item = new Item(category, name, price)
        then: 'category is given category'
            item.category == category
        and: 'name is given name'
            item.name == name
        and: 'price is given price'
            item.price == price
    }
}
