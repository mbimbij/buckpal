package io.reflectoring.buckpal.account.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;

import javax.money.MonetaryAmount;

@Getter
@EqualsAndHashCode
public class Account {
    private final AccountId accountId;
    private Activities activities;

    public Account(AccountId accountId) {
        this.accountId = accountId;
        activities = new Activities();
    }

    public boolean transferOut(MonetaryAmount monetaryAmount, AccountId destinationId) {
        activities.add(new Activity(monetaryAmount.negate(), destinationId));
        return true;
    }

    public void transferIn(MonetaryAmount monetaryAmount, AccountId sourceId) {
        activities.add(new Activity(monetaryAmount, sourceId));
    }

    public Activities getActivities() {
        return activities;
    }
}
