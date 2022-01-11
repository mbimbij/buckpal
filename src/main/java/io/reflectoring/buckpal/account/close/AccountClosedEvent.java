package io.reflectoring.buckpal.account.close;

import io.reflectoring.buckpal.account.AccountId;
import io.reflectoring.buckpal.generic.DomainEvent;
import lombok.Value;

@Value
public class AccountClosedEvent implements DomainEvent {
    AccountId accountId;
}
