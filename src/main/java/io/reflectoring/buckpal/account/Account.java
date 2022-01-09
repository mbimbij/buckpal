package io.reflectoring.buckpal.account;

public class Account {
    private AccountId id;

    public Account(AccountId accountId) {
        id = accountId;
    }

    public AccountId getId() {
        return id;
    }
}
