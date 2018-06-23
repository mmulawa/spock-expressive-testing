package com.dataart.spockframework.expressive.way.of.testing.examples.spring

import com.dataart.spockframework.expressive.way.of.testing.examples.maps.OrderData
import com.dataart.spockframework.expressive.way.of.testing.examples.shop.PaymentRepository
import com.dataart.spockframework.expressive.way.of.testing.examples.shop.PaymentService;
import org.junit.Test;
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Import
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner
import spock.lang.Specification;

@SpringBootTest
@Import(DetachedJavaConfig)
public class MockSpringBeansSpec extends Specification implements OrderData {

    @Autowired
    PaymentService paymentService

    @Autowired
    PaymentRepository paymentRepository

    def 'should use repository mock'() {
        when:
            paymentService.payForOrder(getOrder())
        then:
            1 * paymentRepository.save(_)
    }
}
