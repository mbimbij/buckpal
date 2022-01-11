package io.reflectoring.buckpal.account.application.port.in;

import io.reflectoring.buckpal.account.domain.AccountId;

import javax.money.MonetaryAmount;
import java.time.ZonedDateTime;

public interface IDepositFunds {
    void deposit(AccountId accountId, MonetaryAmount monetaryAmount, ZonedDateTime depositDateTime, String description);
}
