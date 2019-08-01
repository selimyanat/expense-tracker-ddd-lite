package com.sy.expense.tracker.identity.application

import com.sy.expense.tracker.identity.domain.EncryptionService
import com.sy.expense.tracker.identity.domain.User
import com.sy.expense.tracker.identity.domain.UserRepository
import com.sy.expense.tracker.sharedkernel.DomainEventPublisher
import spock.lang.Specification

/**
 * Test class for {@link IdentityApplicationService}
 */
class IdentityApplicationServiceTest extends Specification {

    UserRepository userRepository = Mock()

    EncryptionService encryptionService = Mock()

    DomainEventPublisher domainEventPublisher = Mock()

    IdentityApplicationService underTest = new IdentityApplicationService(userRepository,
            encryptionService, domainEventPublisher)

    def 'Should register user'() {
        given:
            def aCommand = RegisterUserCommandMotherObject.aRegisterUserCommand()
        when:
            underTest.registerUser(aCommand)
        then:
            1 * userRepository.save({ User aUser ->
                aUser.getUserName() == aCommand.getUsername() &&
                aUser.getId() == aCommand.getUserId()
                aUser.getEmail() == aCommand.getUserEmail()
                aUser.getPassword() == aCommand.getUserPassword()
            })
            1 * domainEventPublisher.publish(*_)
    }

    def 'Should update username'() {
    }
}
