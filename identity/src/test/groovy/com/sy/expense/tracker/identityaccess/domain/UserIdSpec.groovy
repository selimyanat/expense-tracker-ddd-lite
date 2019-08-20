package com.sy.expense.tracker.identityaccess.domain

import spock.lang.Specification

/**
 * Test class for {@link UserId}.
 *
 * @author selim
 */
class UserIdSpec extends Specification {

    def 'Should reject invalid user id' () {
        when:
            new UserId(null)
        then:
            def exception = thrown(IllegalArgumentException)
            exception.getMessage() == "User id cannot be null"
    }
}
