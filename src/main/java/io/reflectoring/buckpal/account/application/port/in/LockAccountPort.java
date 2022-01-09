package io.reflectoring.buckpal.account.application.port.in;

import io.reflectoring.buckpal.account.domain.AccountId;

public interface LockAccountPort {
    void lock(AccountId accountId);

    void unlock(AccountId accountId);
}
