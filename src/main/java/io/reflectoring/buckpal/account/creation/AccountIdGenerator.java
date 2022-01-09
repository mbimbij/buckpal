package io.reflectoring.buckpal.account.creation;

import io.reflectoring.buckpal.account.AccountId;

@FunctionalInterface
public interface AccountIdGenerator {
    public AccountId nextAccountId();
}
