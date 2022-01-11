package io.reflectoring.buckpal.account.withdraw;

import io.reflectoring.buckpal.account.domain.AccountId;
import io.reflectoring.buckpal.account.application.port.out.DomainEvent;
import lombok.Value;

import javax.money.MonetaryAmount;
import java.time.ZonedDateTime;

@Value
public class FundsWithdrawnEvent implements DomainEvent {
    AccountId accountId;
    MonetaryAmount monetaryAmount;
    ZonedDateTime withdrawDateTime;
    String description;
}
