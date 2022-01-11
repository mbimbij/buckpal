package io.reflectoring.buckpal.account.service.deposit;

import io.reflectoring.buckpal.account.application.port.in.IDepositFunds;
import io.reflectoring.buckpal.account.domain.AccountId;
import io.reflectoring.buckpal.account.domain.AccountRepository;
import io.reflectoring.buckpal.account.application.port.out.IPublishDomainEvents;
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
