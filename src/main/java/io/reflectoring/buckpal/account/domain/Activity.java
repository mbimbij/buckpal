package io.reflectoring.buckpal.account.domain;

import lombok.Value;

import javax.money.MonetaryAmount;

@Value
public class Activity {
    MonetaryAmount monetaryAmount;
    AccountId sourceOrDestinationAccountId;
}
