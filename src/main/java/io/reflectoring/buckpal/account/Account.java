package io.reflectoring.buckpal.account;

import io.reflectoring.buckpal.account.withdraw.InsufficientFundsException;
import org.javamoney.moneta.Money;

import javax.money.MonetaryAmount;

public class Account {
    private final AccountId id;
    private MonetaryAmount balance;

    public Account(AccountId accountId) {
        id = accountId;
        balance = Money.of(0, "EUR");
    }

    public Account(AccountId accountId, MonetaryAmount balance) {
        id = accountId;
        this.balance = balance;
    }

    public AccountId getId() {
        return id;
    }

    public void withdraw(MonetaryAmount monetaryAmount) {
        if(balance.subtract(monetaryAmount).isNegative()){
            throw new InsufficientFundsException();
        }

        balance = balance.subtract(monetaryAmount);
    }
}
