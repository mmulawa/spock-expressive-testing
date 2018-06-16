package com.dataart.spockframework.expressiwe.way.of.testsing.examples

import org.junit.Rule
import org.springframework.boot.test.rule.OutputCapture
import spock.lang.Specification

class PrintFiveCatsSpec extends Specification {

    @Rule
    private OutputCapture capture = new OutputCapture()

    def "should print five 🐱"() {
        given: 'single cat'
            def cat = '🐱'
        when: 'print for each in one to five range'
            (1..5).each { print cat }
        then: 'printed five cats has been captured'
            capture.toString() == '🐱🐱🐱🐱🐱'
    }
}
