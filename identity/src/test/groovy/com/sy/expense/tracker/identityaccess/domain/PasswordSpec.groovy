package com.sy.expense.tracker.identityaccess.domain

import spock.lang.Specification

/**
 * Test class for {@link Password}.
 *
 * @author selim
 */
class PasswordSpec extends Specification {

    def 'Should reject invalid user password' () {
        when:
            new Password(userPassword)
        then:
            def exception = thrown(IllegalArgumentException)
            exception.getMessage() == "User password cannot be null or empty"
        where:
            userPassword << [null, ""]
    }
}