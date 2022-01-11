package io.reflectoring.buckpal.account.application.port.out;

import io.reflectoring.buckpal.account.domain.DomainEvent;
import io.reflectoring.buckpal.account.domain.EntityId;

import java.util.Collection;

public interface IStoreDomainEvents {
    void add(DomainEvent domainEvent);
    <T> Collection<DomainEvent> getById(EntityId<T> entityId);
}
