package io.reflectoring.buckpal.account.application.port.out;

import io.reflectoring.buckpal.account.domain.DomainEvent;

public interface IPublishDomainEvents {
    void publish(DomainEvent domainEvent);

    void subscribe(IHandleDomainEvents iHandleDomainEvents);

    void unsubscribe(IHandleDomainEvents iHandleDomainEvents);
}
