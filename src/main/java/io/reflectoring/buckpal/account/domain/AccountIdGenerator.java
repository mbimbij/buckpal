package io.reflectoring.buckpal.account.domain;

import io.reflectoring.buckpal.account.domain.AccountId;

@FunctionalInterface
public
interface AccountIdGenerator {
    AccountId nextAccountId();
}
