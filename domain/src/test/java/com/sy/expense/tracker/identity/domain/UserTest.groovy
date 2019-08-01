package com.sy.expense.tracker.identity.domain

import spock.lang.Specification

/**
 * Test class for {@link User}.
 */
class UserTest extends Specification {

    def 'Should create user'() {
        given:
            def anId = new UserId(UUID.randomUUID())
            def aUserName = new Username("aUsername")
            def aPassword = new Password("aPassword")
            def anEmail = new Email("user@somewhere.com")
        when:
            def aUser = new User(anId, aUserName, aPassword, anEmail)
        then:
            aUser.getId().value == anId.value
            aUser.getUserName().value == aUserName.value
            aUser.getPassword().value == aPassword.value
            aUser.getEmail().value == anEmail.value
    }

    def 'Should change username'() {
        given:
            def aUser = UserMotherObject.aUser()
            def theFormerUsername = aUser.getUserName()
            def aNewUsername = new Username("anotherUsername")
        when:
            aUser.changeUsername(aNewUsername)
        then:
            aUser.getUserName() == aNewUsername
            aUser.getDomainEvents().size() == 1
            aUser.getDomainEvents().contains(new UserEvent.UserNameChanged(
                    aUser.getId(),
                    theFormerUsername,
                    aNewUsername))
    }

    def 'Should change password'() {
        given: 'a user'
            def aUser = UserMotherObject.aUser()
            def theCurrentPassword = aUser.getPassword()
            def aNewPassword = new Password('anotherPassword')
            def encryptionService = Mock(EncryptionService)
        and:
            encryptionService.encrypt(theCurrentPassword.getValue()) >> aUser.getPassword().getValue()
        and:
            encryptionService.encrypt(aNewPassword.getValue()) >> "encryptedPassword"
        when:
            aUser.changePassword(theCurrentPassword, aNewPassword, encryptionService)
        then:
            aUser.getPassword() == new Password("encryptedPassword")
            aUser.getDomainEvents().size() == 1
            aUser.getDomainEvents().contains(new UserEvent.UserPasswordChanged(aUser.getId()))
    }

    def 'Should not change password when the current password is not the same as the existing password'() {
        given:
            def aUser = UserMotherObject.aUser()
            def theCurrentPassword = new Password("notTheSamePasswordAsTheExistingPassword")
            def aNewPassword = new Password('anotherPassword')
            def encryptionService = Mock(EncryptionService)
        and:
            encryptionService.encrypt(theCurrentPassword.getValue()) >> "anEncryptedPasswordThatDoesNotMatchTheExistingPassword"
        when:
            aUser.changePassword(theCurrentPassword, aNewPassword, encryptionService)
        then:
            def exception = thrown(IllegalArgumentException)
            exception.getMessage() == "The current password does not match existing password"
    }

    def 'Should not change password when the new password is equal to the username'() {
        given:
            def aUser = UserMotherObject.aUser()
            def aNewPassword = new Password(aUser.getUserName().getValue())
            def encryptionService = Mock(EncryptionService)
        when:
            aUser.changePassword(aUser.getPassword(), aNewPassword, encryptionService)
        then:
            def exception = thrown(IllegalArgumentException)
            exception.getMessage() == "The user name and password must not be the same"
    }
}
