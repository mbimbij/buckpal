package io.reflectoring.buckpal.account.application.port.in;

import io.reflectoring.buckpal.account.domain.Account;
import io.reflectoring.buckpal.account.domain.AccountId;

public interface IGetAccountDetails {
    Account getAccountDetails(AccountId accountId);
}
