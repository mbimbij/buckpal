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

    public boolean transferOut(ActivityFactory activityFactory, MonetaryAmount monetaryAmount, AccountId destinationId) {
        activities.add(activityFactory.createMovementActivity(monetaryAmount.negate(), destinationId));
        return true;
    }

    public void transferIn(ActivityFactory activityFactory, MonetaryAmount monetaryAmount, AccountId sourceId) {
        activities.add(activityFactory.createMovementActivity(monetaryAmount, sourceId));
    }

    public Activities getActivities() {
        return activities;
    }
}
