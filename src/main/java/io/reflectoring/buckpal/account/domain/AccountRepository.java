package io.reflectoring.buckpal.account.domain;

import io.reflectoring.buckpal.account.application.port.out.IStoreDomainEvents;
import io.reflectoring.buckpal.account.service.close.AccountClosedEvent;
import io.reflectoring.buckpal.account.service.creation.AccountCreatedEvent;
import io.reflectoring.buckpal.account.service.deposit.FundsDeposittedEvent;
import io.reflectoring.buckpal.account.service.withdraw.FundsWithdrawnEvent;
import lombok.RequiredArgsConstructor;

import java.util.Collection;
import java.util.Optional;

@RequiredArgsConstructor
public class AccountRepository {
    private final IStoreDomainEvents iStoreDomainEvents;

    public Optional<Account> findById(AccountId accountId) {
        Collection<DomainEvent> events = iStoreDomainEvents.getById(accountId);
        if (events.isEmpty()) {
            return Optional.empty();
        }
        Account account = new Account(accountId);
        for (DomainEvent event : events) {
            if (event instanceof AccountCreatedEvent accountCreatedEvent) {
                account.handle(accountCreatedEvent);
            } else if (event instanceof FundsDeposittedEvent fundsDeposittedEvent) {
                account.handle(fundsDeposittedEvent);
            } else if (event instanceof FundsWithdrawnEvent fundsWithdrawnEvent) {
                account.handle(fundsWithdrawnEvent);
            } else if (event instanceof AccountClosedEvent accountClosedEvent) {
                account.handle(accountClosedEvent);
            }
        }
        return Optional.of(account);
    }
}
