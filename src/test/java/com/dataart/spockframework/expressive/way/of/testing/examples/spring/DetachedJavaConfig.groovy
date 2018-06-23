package com.dataart.spockframework.expressive.way.of.testing.examples.spring

import com.dataart.spockframework.expressive.way.of.testing.examples.shop.PaymentRepository
import org.springframework.context.annotation.Bean
import spock.mock.DetachedMockFactory

class DetachedJavaConfig {

    def mockFactory = new DetachedMockFactory()

    @Bean
    PaymentRepository paymentRepository() {
        return mockFactory.Mock(PaymentRepository)
    }
    //...
    // define any number of mocks
}
