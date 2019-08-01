package com.sy.expense.tracker.identity.domain

import spock.lang.Specification

/**
 * Test class for {@link Username}.
 *
 * @author selim
 */
class UsernameTest extends Specification {

    def 'Should reject invalid user name' () {
        when:
            new Username(userName)
        then:
            def exception = thrown(IllegalArgumentException)
            exception.getMessage() == "User name cannot be null or empty"
        where:
            userName << [null, ""]
    }
}
