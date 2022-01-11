package io.reflectoring.buckpal.account.deposit;

import io.reflectoring.buckpal.account.domain.AccountId;
import io.reflectoring.buckpal.account.application.port.out.DomainEvent;
import lombok.Value;

import javax.money.MonetaryAmount;
import java.time.ZonedDateTime;

@Value
public class FundsDeposittedEvent implements DomainEvent {
    AccountId accountId;
    MonetaryAmount monetaryAmount;
    ZonedDateTime depositDateTime;
    String description;
}
