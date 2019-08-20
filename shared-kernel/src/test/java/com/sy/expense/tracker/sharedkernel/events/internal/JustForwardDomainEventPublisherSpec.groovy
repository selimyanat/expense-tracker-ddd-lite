package com.sy.expense.tracker.sharedkernel.events.internal

import com.sy.expense.tracker.sharedkernel.events.DomainEventMotherObject
import org.springframework.context.ApplicationEventPublisher
import spock.lang.Specification

/**
 * Test class for {@link JustForwardDomainEventPublisherSpec}
 */
class JustForwardDomainEventPublisherSpec extends Specification {

    def applicationEventPublisher = Mock(ApplicationEventPublisher)

    def underTest = new JustForwardDomainEventPublisher(applicationEventPublisher)

    def 'Should publish events' () {
        given: 'a collection of events'
            def anEvent = DomainEventMotherObject.aDomainEvent()
            def anotherEvent = DomainEventMotherObject.aDomainEvent()
            def aCollectionOfEvents = Set.of(anEvent, anotherEvent)
        when:
            underTest.publish(aCollectionOfEvents)
        then:
            1 * applicationEventPublisher.publishEvent(anEvent)
            1 * applicationEventPublisher.publishEvent(anotherEvent)
    }
}
