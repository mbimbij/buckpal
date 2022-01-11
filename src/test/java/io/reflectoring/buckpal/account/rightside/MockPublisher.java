package io.reflectoring.buckpal.account.rightside;

import io.reflectoring.buckpal.account.application.port.out.DomainEvent;
import io.reflectoring.buckpal.account.application.port.out.IPublishDomainEvents;

import java.util.ArrayList;
import java.util.List;

public class MockPublisher implements IPublishDomainEvents {
    private List<DomainEvent> domainEvents = new ArrayList<>();

    @Override
    public void publish(DomainEvent domainEvent) {
        domainEvents.add(domainEvent);
    }

    public List<DomainEvent> getDomainEvents() {
        return domainEvents;
    }
}
