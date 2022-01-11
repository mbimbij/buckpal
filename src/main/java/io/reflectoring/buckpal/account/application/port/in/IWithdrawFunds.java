package io.reflectoring.buckpal.account.application.port.in;

import io.reflectoring.buckpal.account.domain.AccountId;

import javax.money.MonetaryAmount;
import java.time.ZonedDateTime;

public interface IWithdrawFunds {
    void withdraw(AccountId accountId, MonetaryAmount monetaryAmount, ZonedDateTime withdrawDateTime, String description);
}
