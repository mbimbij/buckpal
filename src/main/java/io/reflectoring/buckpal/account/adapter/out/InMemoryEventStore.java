package io.reflectoring.buckpal.account.adapter.out;

import io.reflectoring.buckpal.account.application.port.out.IHandleDomainEvents;
import io.reflectoring.buckpal.account.application.port.out.IStoreDomainEvents;
import io.reflectoring.buckpal.account.domain.DomainEvent;
import io.reflectoring.buckpal.account.domain.EntityId;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class InMemoryEventStore implements IStoreDomainEvents, IHandleDomainEvents {
    private final List<DomainEvent> domainEvents = new ArrayList<>();

    @Override
    public void handle(DomainEvent domainEvent) {
        this.add(domainEvent);
    }

    @Override
    public boolean accept(DomainEvent domainEvent) {
        return true;
    }

    @Override
    public void add(DomainEvent domainEvent) {
        domainEvents.add(domainEvent);
    }

    @Override
    public <T> Collection<DomainEvent> getById(EntityId<T> entityId) {
        return domainEvents.stream()
                           .filter(domainEvent -> Objects.equals(domainEvent.getEntityId(), entityId))
                           .collect(Collectors.toList());
    }

    public List<DomainEvent> getAll() {
        return domainEvents;
    }
}
