package com.dataart.spockframework.expressive.way.of.testing.examples.unicode

import org.junit.Rule
import org.springframework.boot.test.rule.OutputCapture;
import spock.lang.IgnoreIf
import spock.lang.Specification
import spock.lang.Unroll;

public class WinterSportsSpec extends Specification {

    public static final int SLOPE_WIDTH = SLOPE_HEIGHT
    public static final int SLOPE_HEIGHT = 10

    @Rule
    private OutputCapture capture = new OutputCapture()

    @Unroll
    def 'should #person ride down the slope'() {
        given: 'slope'
            def slope = (1..SLOPE_HEIGHT)
            def random = new Random()
            def position = random.nextInt(SLOPE_WIDTH)
            def direction = [-2, -1, 1, 2][random.nextInt(3)]
        when:
            slope.each {
                direction = (it % Math.abs(direction) == 0) ? -direction : direction
                position = Math.max(1, Math.min(SLOPE_WIDTH, position + direction))
                drawPosition(position, person)
            }
        then:
            capture.toString().findAll(person).size() == SLOPE_HEIGHT
        where:
            person << ['🏂', '⛷']
    }

    private void drawPosition(int position, def person) {
        (1..position).each { print('_') }
        print person
        (position..SLOPE_WIDTH).each { print('_') }
        print '\n'
    }
}
