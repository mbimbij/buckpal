package io.reflectoring.buckpal.account.creation;

import io.reflectoring.buckpal.account.AccountId;

@FunctionalInterface
interface AccountIdGenerator {
    AccountId nextAccountId();
}
