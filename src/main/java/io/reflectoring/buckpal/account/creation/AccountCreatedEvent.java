package io.reflectoring.buckpal.account.creation;

import io.reflectoring.buckpal.account.AccountId;
import io.reflectoring.buckpal.generic.DomainEvent;
import lombok.Value;

@Value
class AccountCreatedEvent implements DomainEvent {
    CustomerId customerId;
    AccountId accountId;
}
