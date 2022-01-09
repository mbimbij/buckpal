package io.reflectoring.buckpal.account.domain;

import io.reflectoring.buckpal.generic.Id;

import javax.money.MonetaryAmount;
import java.util.UUID;

public class ActivityFactory {
    public Activity createMovementActivity(MonetaryAmount monetaryAmount, AccountId accountId) {
        return new Activity(getNextId(), monetaryAmount, accountId);
    }

    public Id getNextId() {
        return new Id(UUID.randomUUID().toString());
    }
}
