package com.dataart.spockframework.expressive.way.of.testing.examples

import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification

@SpringBootTest
public class ContextLoadSpec extends Specification {

    def contextLoads() {
        expect:
            'context load'
    }

}
