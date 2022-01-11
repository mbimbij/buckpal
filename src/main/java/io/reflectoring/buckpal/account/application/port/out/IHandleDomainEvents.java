package io.reflectoring.buckpal.account.application.port.out;

import io.reflectoring.buckpal.account.domain.DomainEvent;

public interface IHandleDomainEvents {
    void handle(DomainEvent domainEvent);

    boolean accept(DomainEvent domainEvent);
}
