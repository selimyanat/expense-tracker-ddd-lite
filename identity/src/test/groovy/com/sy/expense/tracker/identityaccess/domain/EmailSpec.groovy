package com.sy.expense.tracker.identityaccess.domain

import spock.lang.Specification

/**
 * Test class for {@link Email}.
 *
 * @author selim
 */
class EmailSpec extends Specification {

    def 'Should reject invalid user email' () {
        when:
            new Email(userEmail)
        then:
            def exception = thrown(IllegalArgumentException)
            exception.getMessage() == "User email " + userEmail + " is not a valid email"
        where:
            userEmail << [null, "", "invalid_email", "12345_", "@somewhere.com"]
    }
}
