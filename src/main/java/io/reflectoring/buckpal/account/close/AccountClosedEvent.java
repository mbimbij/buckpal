package io.reflectoring.buckpal.account.close;

import io.reflectoring.buckpal.account.domain.AccountId;
import io.reflectoring.buckpal.account.application.port.out.DomainEvent;
import lombok.Value;

@Value
public class AccountClosedEvent implements DomainEvent {
    AccountId accountId;
}
