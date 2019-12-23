package com.sy.expense.tracker.sharedkernel.infrastructure.events


import spock.lang.Specification

import static com.sy.expense.tracker.sharedkernel.infrastructure.events.fixture.DomainEventFixture.aDummyDomainEvent
import static com.sy.expense.tracker.sharedkernel.infrastructure.events.fixture.StoredEventFixture.aStoredDummyEvent

/**
 * Test class for {@link JustStoreDomainEventPublisherSpec}
 */
class JustStoreDomainEventPublisherSpec extends Specification {

    def mapper = Mock(DomainEventToStoredEventMapper)

    def store = Mock(OutboxStore)

    def underTest = new JustStoreDomainEventPublisher(store, mapper)

    def 'Should publish events'() {
        given:
            def anEvent = aDummyDomainEvent()
        and:
            def aStoredEvent = aStoredDummyEvent(1)
        and:
            mapper.toStoredEvent(anEvent) >> aStoredEvent
        when:
            underTest.publish(Set.of(anEvent))
        then:
            1 * store.append(aStoredEvent)
    }
}
