package com.sy.expense.tracker.sharedkernel.infrastructure.events

import spock.lang.Specification

import static com.sy.expense.tracker.sharedkernel.infrastructure.events.fixture.DomainEventFixture.aDummyDomainEvent

/**
 * Test class for {@link DomainEventToStoredEventMapper}
 */
class DomainEventToStoredEventMapperSpec extends Specification {

    def eventSerializer = Mock(EventSerializer)

    def underTest = new DomainEventToStoredEventMapper(eventSerializer)

    def 'Should mao domain event to stored event'() {

        given:
            def anEvent = aDummyDomainEvent()
        and:
            eventSerializer.serialize(aDummyDomainEvent()) >> "aPayload"
        when:
            def aStoredEvent = underTest.toStoredEvent(aDummyDomainEvent())
        then:
            aStoredEvent.getEventId() == anEvent.getId().getValue().toString()
            aStoredEvent.getAggregateId() == anEvent.getAggregateId().getValue().toString()
            aStoredEvent.getTypeName() == anEvent.getClass().getName()
            aStoredEvent.getOccurredOn() == anEvent.getOccurredOn().getValue()
            aStoredEvent.getPayload() == "aPayload";

    }
}
