package io.reflectoring.buckpal.account.service.close;

import io.reflectoring.buckpal.account.domain.AccountId;
import io.reflectoring.buckpal.account.domain.DomainEvent;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode(callSuper = true)
public class AccountClosedEvent extends DomainEvent {

    public AccountClosedEvent(AccountId accountId) {
        this.entityId = accountId;
    }
}
