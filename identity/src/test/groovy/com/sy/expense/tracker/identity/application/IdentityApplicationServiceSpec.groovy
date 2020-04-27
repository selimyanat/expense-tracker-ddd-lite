package com.sy.expense.tracker.identity.application

import com.sy.expense.tracker.identity.application.fixture.AuthenticateUserCommandFixture
import com.sy.expense.tracker.identity.application.fixture.ChangeUserPasswordCommandFixture
import com.sy.expense.tracker.identity.application.fixture.ChangeUsernameCommandFixture
import com.sy.expense.tracker.identity.application.fixture.RegisterUserCommandFixture
import com.sy.expense.tracker.identity.domain.*
import com.sy.expense.tracker.identity.domain.fixture.UserFixture
import com.sy.expense.tracker.sharedkernel.domain.DomainEventPublisher
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
            def aCommand = RegisterUserCommandFixture.aRegisterUserCommand()
        and:
            userRepository.findByUserEmail(aCommand.getUserEmail()) >> Optional.empty()
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

    def 'Should not register user if email already exist'() {
        given:
            def aCommand = RegisterUserCommandFixture.aRegisterUserCommand()
        and:
            userRepository.findByUserEmail(aCommand.getUserEmail()) >> Optional.of(UserFixture.aUser())
        when:
            underTest.registerUser(aCommand)
        then:
            def exception = thrown(IllegalArgumentException)
            exception.getMessage() == String.format("User with email %s already exit",
                    aCommand.getUserEmail().getValue())
    }

    def 'Should update username'() {
        given:
            def aUser = UserFixture.aUser()
            def aCommand = ChangeUsernameCommandFixture.aChangeUsernameCommand(aUser)
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
            def aUser = UserFixture.aUser()
            def aCommand = ChangeUsernameCommandFixture.aChangeUsernameCommand(aUser)
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
            def aUser = UserFixture.aUser()
            def aCommand = ChangeUserPasswordCommandFixture.aChangeUserPasswordCommand(aUser)
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
            def aUser = UserFixture.aUser()
            def aCommand = AuthenticateUserCommandFixture.anAuthenticationUserCommand()
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
