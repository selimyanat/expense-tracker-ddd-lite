package com.sy.expense.tracker.sharedkernel.infrastructure.events

import com.jayway.jsonpath.JsonPath
import com.sy.expense.tracker.sharedkernel.domain.ADummyDomainEvent
import spock.lang.Specification

import static com.sy.expense.tracker.sharedkernel.infrastructure.events.fixture.DomainEventFixture.aDummyDomainEvent

/**
 * Test class for {@link EventSerializer}
 */
class EventSerializerSpec extends Specification {

    def underTest = new EventSerializer();

    def 'Should serialize domain event'() {
        given:
            def anEvent = aDummyDomainEvent()
        when:
            def aSerializedEvent = JsonPath.parse(underTest.serialize(anEvent)).json()
        then:
            aSerializedEvent["message"] == aDummyDomainEvent().getMessage()
            aSerializedEvent["id"]["value"] == aDummyDomainEvent().getId().getValue().toString()
            aSerializedEvent["aggregateId"]["value"] == aDummyDomainEvent().getAggregateId().getValue().toString()
            aSerializedEvent["occurredOn"]["value"]["seconds"] == aDummyDomainEvent().getOccurredOn().getValue().epochSecond
    }

    def 'Should deserialize domain event'() {
        given:
            def anEvent = aDummyDomainEvent()
        and:
            def aSerializedEvent = underTest.serialize(anEvent)
        when:
            def aDeserializedEvent = underTest.deserialize(aSerializedEvent, ADummyDomainEvent.class)
        then:
            aDeserializedEvent == anEvent
    }
}
