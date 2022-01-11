package io.reflectoring.buckpal.account.domain;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public
class AccountFactory {
    private final AccountIdGenerator accountIdGenerator;

    public Account createAccount(CustomerId customerId) {
        AccountId accountId = accountIdGenerator.nextAccountId();
        return new Account(accountId);
    }
}
