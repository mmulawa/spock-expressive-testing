package com.dataart.spockframework.expressive.way.of.testing.examples.parametrization

import com.dataart.spockframework.expressive.way.of.testing.examples.shop.Item
import spock.lang.Specification
import spock.lang.Unroll

import static com.dataart.spockframework.expressive.way.of.testing.examples.shop.ItemCategory.HOME
import static com.dataart.spockframework.expressive.way.of.testing.examples.shop.ItemCategory.MEDICINES

class GivenWhenThenWhereWithUnrollSpec extends Specification {

    @Unroll
    def 'should create #name of given parameters'() {
        when:
            def item = new Item(category, name, price)
        then:
            item.category == category
            item.name == name
            item.price == price
        where:
            category  | name      | price
            HOME      | 'spoon'   | 300
            HOME      | 'knife'   | 500
            MEDICINES | 'aspirin' | 100
    }
}
