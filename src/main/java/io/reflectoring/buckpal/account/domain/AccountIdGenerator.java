package io.reflectoring.buckpal.account.domain;

@FunctionalInterface
public
interface AccountIdGenerator {
    AccountId nextAccountId();
}
