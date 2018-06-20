package com.dataart.spockframework.expressive.way.of.testing.examples.labels

import com.dataart.spockframework.expressive.way.of.testing.examples.shop.Item
import com.dataart.spockframework.expressive.way.of.testing.examples.shop.ItemCategory
import spock.lang.Specification
import spock.lang.Unroll

import static com.dataart.spockframework.expressive.way.of.testing.examples.shop.ItemCategory.*

class GivenWhenThenWhereWithUnrollSpec extends Specification {

    @Unroll
    def 'should create #name of given parameters'() {
        when:
            def item = new Item(category, name, price)
        then:
            item.category == category
            item.name == name + 'a'
            item.price == price
        where:
            category  | name      | price
            HOME      | 'spoon'   | 300
            HOME      | 'knife'   | 500
            MEDICINES | 'aspirin' | 100
    }
}
