package io.reflectoring.buckpal.account.service.deposit;

import io.reflectoring.buckpal.account.domain.DomainEvent;
import io.reflectoring.buckpal.account.domain.EntityId;
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
public class FundsDeposittedEvent extends DomainEvent {
    final MonetaryAmount monetaryAmount;
    final ZonedDateTime depositDateTime;
    final String description;

    public FundsDeposittedEvent(EntityId<?> accountId, MonetaryAmount monetaryAmount, ZonedDateTime depositDateTime, String description) {
        this.entityId = accountId;
        this.monetaryAmount = monetaryAmount;
        this.depositDateTime = depositDateTime;
        this.description = description;
    }
}
