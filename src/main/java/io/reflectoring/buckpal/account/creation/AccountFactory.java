package io.reflectoring.buckpal.account.creation;

import io.reflectoring.buckpal.account.Account;
import io.reflectoring.buckpal.account.AccountId;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class AccountFactory {
    private final AccountIdGenerator accountIdGenerator;

    Account createAccount(CustomerId customerId) {
        AccountId accountId = accountIdGenerator.nextAccountId();
        return new Account(accountId);
    }
}
