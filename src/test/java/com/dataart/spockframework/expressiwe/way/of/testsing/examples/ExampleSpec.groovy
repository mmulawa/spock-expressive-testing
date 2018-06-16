package com.dataart.spockframework.expressiwe.way.of.testsing.examples

import spock.lang.Specification

class ExampleSpec extends Specification {


    def 'should check if true is true'() {
        given: 'Groovy written Spock Specification'
            // preconditions goes there
        when: 'value is being assigned to dynamic Groovy type'
            // actual behaviour being tested
            def dynamicTypedBooleanVariable = true
        then: 'true is true'
            // assertions
            // any boolean expression
            true
        and: 'dynamic typed variable is true'
            dynamicTypedBooleanVariable
    }


}
