package io.reflectoring.buckpal.account.domain.event;

import io.reflectoring.buckpal.account.domain.AccountId;
import io.reflectoring.buckpal.generic.Id;
import lombok.Value;

import javax.money.MonetaryAmount;

@Value
public class MoneyTransferred {
    Id id;
    MonetaryAmount monetaryAmount;
    AccountId source;
    AccountId destination;
}
