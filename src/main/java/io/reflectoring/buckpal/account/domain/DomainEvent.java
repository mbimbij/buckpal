package io.reflectoring.buckpal.account.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@EqualsAndHashCode
@Getter
public abstract class DomainEvent {
    protected EntityId<?> entityId;
}
