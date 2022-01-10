package io.reflectoring.buckpal.account.withdraw;

import io.reflectoring.buckpal.account.AccountId;

import javax.money.MonetaryAmount;
import java.time.ZonedDateTime;

public interface IWithdrawFunds {
    void withdraw(AccountId accountId, MonetaryAmount monetaryAmount, ZonedDateTime withdrawDateTime, String description);
}
