package com.dataart.spockframework.expressive.way.of.testing.examples

import groovy.util.logging.Slf4j
import org.junit.Rule
import org.springframework.boot.test.rule.OutputCapture
import spock.lang.IgnoreIf
import spock.lang.Requires
import spock.lang.Specification

@Slf4j
class PrintFiveCatsSpec extends Specification {

    @Rule
    private OutputCapture capture = new OutputCapture()

    @IgnoreIf({ System.getProperty('surefire.real.class.path') })
    def "should print five 🐱"() {
        given: 'single cat'
            def cat = '🐱'
        when: 'print for each in one to five range'
            (1..5).each { print cat }
        then: 'printed five cats has been captured'
            capture.toString() == '🐱🐱🐱🐱🐱'
    }
}
