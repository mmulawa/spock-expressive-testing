package com.dataart.spockframework.expressive.way.of.testing.examples.labels;

import spock.lang.Specification
import spock.lang.Unroll;

public class ExpectWhereSpec extends Specification {

    @Unroll
    def 'should check if #dynamicTypedBooleanVariable is #dynamicTypedBooleanVariable'() {
        expect: 'true is true'
            true
        and: 'false is false'
            !false
        and:
            dynamicTypedBooleanVariable == dynamicTypedBooleanVariable
        where:
            dynamicTypedBooleanVariable << [true, false]
    }
}
