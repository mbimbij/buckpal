package io.reflectoring.buckpal.account.consultation;

import io.reflectoring.buckpal.account.Account;
import io.reflectoring.buckpal.account.AccountId;

public interface IGetAccountDetails {
    Account getAccountDetails(AccountId accountId);
}
