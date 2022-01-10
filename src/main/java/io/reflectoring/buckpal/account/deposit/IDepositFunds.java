package io.reflectoring.buckpal.account.deposit;

import io.reflectoring.buckpal.account.AccountId;

import javax.money.MonetaryAmount;
import java.time.ZonedDateTime;

public interface IDepositFunds {
    void deposit(AccountId accountId, MonetaryAmount monetaryAmount, ZonedDateTime depositDateTime, String description);
}
