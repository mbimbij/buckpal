package io.reflectoring.buckpal.account.creation;

import io.reflectoring.buckpal.account.domain.AccountId;
import io.reflectoring.buckpal.account.application.port.out.DomainEvent;
import io.reflectoring.buckpal.account.domain.CustomerId;
import lombok.Value;

@Value
class AccountCreatedEvent implements DomainEvent {
    CustomerId customerId;
    AccountId accountId;
}
