package com.sy.expense.tracker.identityaccess.domain

import spock.lang.Specification

/**
 * Test class for {@link User}.
 */
class UserSpec extends Specification {

    def 'Should create user'() {
        given:
            def anId = new UserId(UUID.randomUUID())
            def aUserName = new Username("aUsername")
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
            def aUser = UserMotherObject.aUser()
            def theFormerUsername = aUser.getUsername()
            def aNewUsername = new Username("anotherUsername")
        when:
            aUser.changeUsername(aNewUsername)
        then:
            aUser.getUsername() == aNewUsername
            aUser.getDomainEvents().size() == 1
            aUser.getDomainEvents().contains(new UserEvent.UserNameChanged(
                    aUser.getId(),
                    theFormerUsername,
                    aNewUsername))
    }

    def 'Should change password'() {
        given: 'a user'
            def aUser = UserMotherObject.aUser()
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
            exception.getMessage() == "The given password does not match existing password"
    }

    def 'Should not change password when the new password is equal to the username'() {
        given:
            def aUser = UserMotherObject.aUser()
            def theCurrentPassword = new Password(aUser.username.getValue())
            def aNewPassword = new Password(aUser.getUsername().getValue())
            def encryptionService = Mock(EncryptionService)
        when:
            aUser.changePassword(theCurrentPassword, aNewPassword, encryptionService)
        then:
            def exception = thrown(IllegalArgumentException)
            exception.getMessage() == "The user name and encryptedPassword must not be the same"
    }
}
