package com.dataart.spockframework.expressive.way.of.testing.examples.valueRecording

import com.dataart.spockframework.expressive.way.of.testing.examples.shop.Item
import com.dataart.spockframework.expressive.way.of.testing.examples.shop.ItemCategory
import spock.lang.Specification

import static com.dataart.spockframework.expressive.way.of.testing.examples.shop.ItemCategory.HOME

class ValueRecordingSpec extends Specification {

    def 'should check old value'() {
        given:
            def index = 0
        when:
            index++
        then:
            old(index) == 0
    }

    def 'should check old price'() {
        given:
            def item = new Item(HOME, 'spoon', 300)
        when:
            item.price = item.price * 0.9
        then:
            old(item.price) == (item.price / 90) * 100
    }
}
