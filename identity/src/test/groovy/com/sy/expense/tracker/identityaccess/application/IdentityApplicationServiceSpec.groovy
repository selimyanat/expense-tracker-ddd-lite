package com.sy.expense.tracker.identityaccess.application

import com.sy.expense.tracker.identityaccess.domain.*
import com.sy.expense.tracker.sharedkernel.events.DomainEventPublisher
import spock.lang.Specification

/**
 * Test class for {@link IdentityApplicationService}
 */
class IdentityApplicationServiceSpec extends Specification {

    UserRepository userRepository = Mock()

    EncryptionService encryptionService = Mock()

    DomainEventPublisher domainEventPublisher = Mock()

    AuthenticationService authenticationService = Mock()

    IdentityApplicationService underTest = new IdentityApplicationService(userRepository,
            encryptionService, domainEventPublisher, authenticationService)

    def 'Should register user'() {
        given:
            def aCommand = RegisterUserCommandMotherObject.aRegisterUserCommand()
        and:
            encryptionService.encrypt(aCommand.getPassword().getValue()) >> "encryptedPassword"
        when:
            underTest.registerUser(aCommand)
        then:
            1 * userRepository.insertUser({ User aUser ->
                aUser.getUsername() == aCommand.getUsername()
                aUser.getId() == aCommand.getUserId()
                aUser.getEmail() == aCommand.getUserEmail()
                aUser.getEncryptedPassword() == new EncryptedPassword("encryptedPassword")
            })
            1 * domainEventPublisher.publish(*_)
    }

    def 'Should update username'() {
        given:
            def aUser = UserMotherObject.aUser()
            def aCommand = ChangeUsernameCommandMotherObject.aChangeUsernameCommand(aUser)
        and:
            userRepository.findByUserId(aCommand.getUserId()) >> Optional.of(aUser)
        when:
            underTest.changeUsername(aCommand)
        then:
            aUser.getUsername() == aCommand.getChangedUsername()
            1 * userRepository.insertUser(aUser)
            1 * domainEventPublisher.publish(*_)
    }

    def 'Should not update username if user does not exist'() {
        given:
        def aUser = UserMotherObject.aUser()
        def aCommand = ChangeUsernameCommandMotherObject.aChangeUsernameCommand(aUser)
        and:
            userRepository.findByUserId(aCommand.getUserId()) >> Optional.empty()
        when:
            underTest.changeUsername(aCommand)
        then:
            def exception = thrown(IllegalArgumentException)
            exception.getMessage() == "User with given Id does not exists: " + aCommand.getUserId()
    }

    def 'Should update password'() {
        given:
            def aUser = UserMotherObject.aUser()
            def aCommand = ChangeUserPasswordCommandMotherObject.aChangeUserPasswordCommand(aUser)
        and:
            userRepository.findByUserId(aCommand.getUserId()) >> Optional.of(aUser)
        and:
            encryptionService.encrypt(aCommand.currentPasswordInPlainText.value) >> aUser
                    .getEncryptedPassword().value
        and:
            encryptionService.encrypt(aCommand.getChangedPasswordInPlainText().value) >>
                    "encryptedPassword"
        when:
            underTest.changeUserPassword(aCommand)
        then:
            aUser.getEncryptedPassword().value == "encryptedPassword"
            1 * userRepository.insertUser(aUser)
            1 * domainEventPublisher.publish(*_)
    }

    def 'Should authenticate user'() {
        given:
            def aUser = UserMotherObject.aUser()
            def aCommand = AuthenticateUserCommandMotherObject.anAuthenticationUserCommand()
        and:
            authenticationService.authenticate(aCommand.getUserEmail(), aCommand.getUserPassword()) >>
                    Optional.of(aUser)
        when:
            def userDescriptor = underTest.authenticateUser(aCommand)
        then:
            userDescriptor.get().getEmail() == aUser.getEmail()
            userDescriptor.get().getUsername() == aUser.getUsername()
    }
}
