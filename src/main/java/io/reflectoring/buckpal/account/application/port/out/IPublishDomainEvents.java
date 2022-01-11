package io.reflectoring.buckpal.account.application.port.out;

public interface IPublishDomainEvents {
    void publish(DomainEvent domainEvent);
}
