package io.reflectoring.buckpal.account.domain.event;

import io.reflectoring.buckpal.account.domain.AccountId;
import io.reflectoring.buckpal.generic.Id;
import lombok.Value;

import javax.money.MonetaryAmount;

import static io.reflectoring.buckpal.generic.Id.generateNextId;

@Value
public class MoneyTransferred {
    Id id;
    MonetaryAmount monetaryAmount;
    AccountId source;
    AccountId destination;

    public static MoneyTransferred create(MonetaryAmount monetaryAmount,
                                          AccountId source,
                                          AccountId destination) {
        return new MoneyTransferred(generateNextId(), monetaryAmount, source, destination);
    }
}
