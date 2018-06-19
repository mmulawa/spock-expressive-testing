package com.dataart.spockframework.expressive.way.of.testing.examples.maps

import com.dataart.spockframework.expressive.way.of.testing.examples.shop.ItemCategory
import spock.lang.Specification

import static com.dataart.spockframework.expressive.way.of.testing.examples.shop.ItemCategory.HOME

class LeftShiftMapOperatorSpec extends Specification {

    def 'should modify item map with << operator'() {
        given:
            def map = [category: HOME,
                       name    : 'spoon']
        expect:
            map.name == 'spoon'
        when:
            map << [name: 'knife']
        then:
            map.name == 'knife'
    }

    def 'should modify items map with << operator'() {
        given:
            def map = [items: [[category: HOME,
                                name    : 'spoon'],
                               []]]
        expect:
            map.items[0].name == 'spoon'
            map.items[0].category == HOME
        when:
            map.items[0] << [name: 'knife']
        then:
            map.items[0].name == 'knife'
            map.items[0].category == HOME
    }
}
