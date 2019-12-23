package com.sy.expense.tracker.identity.domain

import com.sy.expense.tracker.identity.domain.event.UserEvent
import com.sy.expense.tracker.identity.domain.fixture.UserFixture
import spock.lang.Specification

/**
 * Test class for {@link User}.
 */
class UserSpec extends Specification {

    def 'Should create user'() {
        given:
            def anId = new UserId(UUID.randomUUID())
            def aUserName = "aUsername"
            def aPassword = new EncryptedPassword("aPassword")
            def anEmail = new Email("user@somewhere.com")
        when:
            def aUser = new User(anId, aUserName, aPassword, anEmail)
        then:
            aUser.getId().value == anId.value
            aUser.getUsername().value == aUserName.value
            aUser.getEncryptedPassword().value == aPassword.value
            aUser.getEmail().value == anEmail.value
    }

    def 'Should change username'() {
        given:
            def aUser = UserFixture.aUser()
            def theFormerUsername = aUser.getUsername()
            def aNewUsername = "anotherUsername"
        when:
            aUser.changeUsername(aNewUsername)
        then:
            aUser.getUsername() == aNewUsername
        then:
            aUser.getDomainEvents().size() == 1
            aUser.getDomainEvents().contains(new com.sy.expense.tracker.identity.domain.event.UserEvent.UserNameChanged(
                aUser.getId().getValue(),
                theFormerUsername,
                aNewUsername))
    }

    def 'Should change password'() {
        given: 'a user'
            def aUser = UserFixture.aUser()
            def theCurrentPassword = new Password("currentPassword")
            def aNewPassword = new Password('anotherPassword')
            def encryptionService = Mock(EncryptionService)
        and:
            encryptionService.encrypt(theCurrentPassword.getValue()) >> aUser.getEncryptedPassword().getValue()
        and:
            encryptionService.encrypt(aNewPassword.getValue()) >> "encryptedPassword"
        when:
            aUser.changePassword(theCurrentPassword, aNewPassword, encryptionService)
        then:
            aUser.getEncryptedPassword() == new EncryptedPassword("encryptedPassword")
        then:
            aUser.getDomainEvents().size() == 1
            aUser.getDomainEvents().contains(new UserEvent.UserPasswordChanged(aUser.getId().getValue()))
    }

    def 'Should not change password when the current password is not the same as the existing password'() {
        given:
            def aUser = UserFixture.aUser()
            def theCurrentPassword = new Password("notTheSamePasswordAsTheExistingPassword")
            def aNewPassword = new Password('anotherPassword')
            def encryptionService = Mock(EncryptionService)
        and:
            encryptionService.encrypt(theCurrentPassword.getValue()) >> "anEncryptedPasswordThatDoesNotMatchTheExistingPassword"
        when:
            aUser.changePassword(theCurrentPassword, aNewPassword, encryptionService)
        then:
            def exception = thrown(IllegalArgumentException)
            exception.getMessage() == "The given password does not match existing password"
    }

    def 'Should not change password when the new password is equal to the username'() {
        given:
            def aUser = UserFixture.aUser()
            def theCurrentPassword = new Password(aUser.getUsername())
            def aNewPassword = new Password(aUser.getUsername())
            def encryptionService = Mock(EncryptionService)
        when:
            aUser.changePassword(theCurrentPassword, aNewPassword, encryptionService)
        then:
            def exception = thrown(IllegalArgumentException)
            exception.getMessage() == "The user name and encryptedPassword must not be the same"
    }
}
