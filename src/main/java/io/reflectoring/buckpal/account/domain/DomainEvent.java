package io.reflectoring.buckpal.account.domain;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public abstract class DomainEvent {
    protected EntityId<?> entityId;
}
