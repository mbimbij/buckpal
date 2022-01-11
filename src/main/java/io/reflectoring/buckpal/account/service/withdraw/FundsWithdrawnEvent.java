package io.reflectoring.buckpal.account.service.withdraw;

import io.reflectoring.buckpal.account.domain.AccountId;
import io.reflectoring.buckpal.account.domain.DomainEvent;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

import javax.money.MonetaryAmount;
import java.time.ZonedDateTime;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode(callSuper = true)
public class FundsWithdrawnEvent extends DomainEvent {
    MonetaryAmount monetaryAmount;
    ZonedDateTime withdrawDateTime;
    String description;

    public FundsWithdrawnEvent(AccountId accountId, MonetaryAmount monetaryAmount, ZonedDateTime withdrawDateTime, String description) {
        this.entityId = accountId;
        this.monetaryAmount = monetaryAmount;
        this.withdrawDateTime = withdrawDateTime;
        this.description = description;
    }
}
