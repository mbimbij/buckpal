package io.reflectoring.buckpal.generic;

public interface IPublishDomainEvents {
    void publish(DomainEvent domainEvent);
}
