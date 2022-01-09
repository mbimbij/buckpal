package io.reflectoring.buckpal.account.domain;

import io.reflectoring.buckpal.generic.Id;
import lombok.Value;

import javax.money.MonetaryAmount;

@Value
public class Activity {
    Id id;
    MonetaryAmount monetaryAmount;
    AccountId accountId;
}
