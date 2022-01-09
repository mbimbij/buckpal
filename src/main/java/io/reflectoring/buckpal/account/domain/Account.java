package io.reflectoring.buckpal.account.domain;

import io.reflectoring.buckpal.account.domain.event.MoneyTransferred;
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

    public MoneyTransferred transferOut(MonetaryAmount monetaryAmount, AccountId destinationId) {
        activities.add(Activity.create(monetaryAmount.negate(), destinationId));
        return MoneyTransferred.create(monetaryAmount, accountId, destinationId);
    }

    public MoneyTransferred transferIn(MonetaryAmount monetaryAmount, AccountId sourceId) {
        activities.add(Activity.create(monetaryAmount, sourceId));
        return MoneyTransferred.create(monetaryAmount, sourceId, accountId);
    }

    public Activities getActivities() {
        return activities;
    }
}
