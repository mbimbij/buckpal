package io.reflectoring.buckpal.account.domain;

import io.reflectoring.buckpal.account.Activity;
import io.reflectoring.buckpal.account.close.AccountAlreadyClosedException;
import io.reflectoring.buckpal.account.close.ClosingAccountWithNonZeroBalanceException;
import io.reflectoring.buckpal.account.withdraw.InsufficientFundsException;
import org.javamoney.moneta.Money;

import javax.money.MonetaryAmount;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

public class Account {
    private final AccountId id;
    private final List<Activity> activities = new ArrayList<>();
    private MonetaryAmount balance;
    private boolean closed;

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

    public void withdraw(MonetaryAmount monetaryAmount, ZonedDateTime withdrawDateTime, String description) {
        if (balance.subtract(monetaryAmount).isNegative()) {
            throw new InsufficientFundsException();
        }

        balance = balance.subtract(monetaryAmount);
        activities.add(Debit.newDebit(monetaryAmount, withdrawDateTime, description));
    }

    public void close() {
        if (!balance.isZero()) {
            throw new ClosingAccountWithNonZeroBalanceException();
        } else if (closed) {
            throw new AccountAlreadyClosedException();
        }
        closed = true;
    }

    public void deposit(MonetaryAmount monetaryAmount, ZonedDateTime depositDateTime, String description) {
        activities.add(Credit.newCredit(monetaryAmount, depositDateTime, description));
        balance = balance.add(monetaryAmount);
    }

    public MonetaryAmount getBalance() {
        return balance;
    }

    public List<Activity> getActivities() {
        return activities;
    }
}
