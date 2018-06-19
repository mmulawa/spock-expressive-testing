package com.dataart.spockframework.expressive.way.of.testing.examples.labels

import spock.lang.Specification

class ExpectSpec extends Specification {

    def 'should check Groovy features'() {
        expect: 'true is true'
            true
        and: 'range from 1 to 5 is of size 5'
            (1..5).size() == 5
        and: 'closure returns last statement'
            def closure = {
                1
                'last'
            }
            closure() == 'last'
        and: 'to make assertion in closure body you have to use assert'
            (1..5).each { it ->
                assert it > 0
                assert it < 6
            }
        and: 'any value can evaluate to Groovy truth'
            !''
            'name'
            def anotherClosure = { '' }
            !anotherClosure()
    }
}
