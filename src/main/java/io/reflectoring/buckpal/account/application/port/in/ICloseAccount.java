package io.reflectoring.buckpal.account.application.port.in;

import io.reflectoring.buckpal.account.domain.AccountId;

public interface ICloseAccount {
    void closeAccount(AccountId accountId);
}
