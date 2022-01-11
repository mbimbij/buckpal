package io.reflectoring.buckpal.account.service.creation;

import io.reflectoring.buckpal.account.domain.AccountId;
import io.reflectoring.buckpal.account.domain.CustomerId;
import io.reflectoring.buckpal.account.domain.DomainEvent;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode(callSuper = true)
public class AccountCreatedEvent extends DomainEvent {
    final CustomerId customerId;

    public AccountCreatedEvent(AccountId accountId, CustomerId customerId) {
        this.entityId = accountId;
        this.customerId = customerId;
    }
}
