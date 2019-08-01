package com.sy.expense.tracker.domain

import spock.lang.Specification

/**
 * Test class for {@link User}.
 */
class UserTest extends Specification {

    def 'Should reject invalid user id' () {
        when:
            new User.UserId(null)
        then:
            def exception = thrown(IllegalArgumentException)
            exception.getMessage() == "User id cannot be null"
    }

    def 'Should reject invalid user name' () {
        when:
            new User.UserName(userName)
        then:
            def exception = thrown(IllegalArgumentException)
            exception.getMessage() == "User name cannot be null or empty"
        where:
            userName << [null, ""]
    }

    def 'Should reject invalid user password' () {
        when:
            new User.UserPasswordHash(userPasswordHash)
        then:
            def exception = thrown(IllegalArgumentException)
            exception.getMessage() == "User password hash cannot be null or empty"
        where:
            userPasswordHash << [null, ""]
    }

    def 'Should reject invalid user email' () {
        when:
            new User.UserEmail(userEmail)
        then:
            def exception = thrown(IllegalArgumentException)
            exception.getMessage() == "User email " + userEmail + " is not a valid email"
        where:
            userEmail << [null, "", "invalid_email", "12345_", "@somewhere.com"]

    }
}
