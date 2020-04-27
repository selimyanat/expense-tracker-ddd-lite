package com.sy.expense.tracker.sharedkernel.infrastructure.events

import com.sy.expense.tracker.sharedkernel.domain.ADummyDomainEvent
import com.sy.expense.tracker.sharedkernel.infrastructure.events.fixture.DomainEventFixture
import com.sy.expense.tracker.sharedkernel.infrastructure.events.fixture.StoredEventFixture
import org.springframework.context.ApplicationEventPublisher
import spock.lang.Specification

class InMemoryNotificationPublisherSpec extends Specification {

    def store = Mock(OutboxStore)

    def publisher = Mock(ApplicationEventPublisher)

    def serializer = Mock(EventSerializer)

    def underTest = new InMemoryNotificationPublisher(store, publisher, serializer)

    def 'Should publish event'() {

        given:
            def aStoredEvent = StoredEventFixture.aStoredDummyEvent(1l)
        and:
            store.allStoredEventSince(0) >> List.of(aStoredEvent)
        and:
            def aDomainEvent = DomainEventFixture.aDummyDomainEvent()
        and:
            serializer.deserialize(aStoredEvent.getPayload(), ADummyDomainEvent.class) >>
                aDomainEvent
        and:
            publisher.publishEvent(aDomainEvent) >> { throw new RuntimeException() }
        when:
            underTest.publishNotifications()
        then:
            underTest.getLastEventOffset() == 1l
            1 * publisher.publishEvent(aDomainEvent)
    }
}
