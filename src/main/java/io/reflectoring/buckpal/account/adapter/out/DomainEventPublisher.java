package io.reflectoring.buckpal.account.adapter.out;

import io.reflectoring.buckpal.account.application.port.out.IHandleDomainEvents;
import io.reflectoring.buckpal.account.application.port.out.IPublishDomainEvents;
import io.reflectoring.buckpal.account.domain.DomainEvent;

import java.util.ArrayList;
import java.util.List;

public class DomainEventPublisher implements IPublishDomainEvents {
    private final List<IHandleDomainEvents> subscribers = new ArrayList<>();

    @Override
    public void publish(DomainEvent domainEvent) {
        subscribers.stream()
                   .filter(eventHandler -> eventHandler.accept(domainEvent))
                   .forEach(eventHandler -> eventHandler.handle(domainEvent));
    }

    @Override
    public void subscribe(IHandleDomainEvents iHandleDomainEvents) {
        subscribers.add(iHandleDomainEvents);
    }

    @Override
    public void unsubscribe(IHandleDomainEvents iHandleDomainEvents) {
        subscribers.remove(iHandleDomainEvents);
    }
}
