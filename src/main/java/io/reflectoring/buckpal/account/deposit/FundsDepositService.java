package io.reflectoring.buckpal.account.deposit;

import io.reflectoring.buckpal.account.AccountId;
import io.reflectoring.buckpal.account.AccountRepository;
import io.reflectoring.buckpal.generic.IPublishDomainEvents;
import lombok.RequiredArgsConstructor;

import javax.money.MonetaryAmount;
import java.time.ZonedDateTime;

@RequiredArgsConstructor
public class FundsDepositService implements IDepositFunds {
    private final AccountRepository accountRepository;
    private final IPublishDomainEvents iPublishDomainEvents;

    @Override
    public void deposit(AccountId accountId, MonetaryAmount monetaryAmount, ZonedDateTime depositDateTime, String description) {
        accountRepository.findById(accountId)
                         .ifPresent(account -> account.deposit(monetaryAmount, depositDateTime, description));
        iPublishDomainEvents.publish(new FundsDeposittedEvent(accountId, monetaryAmount, depositDateTime, description));
    }
}
