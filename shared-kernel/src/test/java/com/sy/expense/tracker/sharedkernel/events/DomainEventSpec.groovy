package com.sy.expense.tracker.sharedkernel.events


import com.sy.expense.tracker.sharedkernel.domain.AggregateId
import spock.lang.Specification

import java.time.Instant

/**
 * Test class for {@link DomainEvent}
 */
class DomainEventSpec extends Specification {

    def 'Should reject invalid event id' () {
        when:
            new EventId(null)
        then:
            def exception = thrown(IllegalArgumentException)
            exception.getMessage() == "Event id cannot be null"
    }

    def 'Should reject invalid event name' () {
        when:
            new EventName(eventName)
        then:
            def exception = thrown(IllegalArgumentException)
            exception.getMessage() == "Event name cannot be null or empty"
        where:
            eventName << [null, ""]
    }

    def 'Should reject invalid aggregate id' () {
        when:
            new AggregateId(null)
        then:
            def exception = thrown(IllegalArgumentException)
            exception.getMessage() == "Aggregate id cannot be null"
    }

    def 'Should reject invalid timestamp' () {
        when:
            new EventTimestamp(null)
        then:
            def exception = thrown(IllegalArgumentException)
            exception.getMessage() == "Timestamp cannot be null"
    }

    def 'Should create user event'() {
        given:
            def eventId = new EventId(UUID.randomUUID())
            def eventName = new EventName("customEvent")
            def aggregateId = new AggregateId(UUID.randomUUID())
            def occurredOn = new EventTimestamp(Instant.now())
        when:
            def domainEvent = new DomainEvent(eventId, eventName, aggregateId, occurredOn)
        then:
            domainEvent.getId() == eventId
            domainEvent.getName() == eventName
            domainEvent.getAggregateId() == aggregateId
            domainEvent.getOccurredOn() == occurredOn
    }

}
