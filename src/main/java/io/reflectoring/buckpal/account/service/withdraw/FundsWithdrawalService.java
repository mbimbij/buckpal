package io.reflectoring.buckpal.account.service.withdraw;

import io.reflectoring.buckpal.account.application.port.in.IWithdrawFunds;
import io.reflectoring.buckpal.account.application.port.out.IPublishDomainEvents;
import io.reflectoring.buckpal.account.domain.AccountId;
import io.reflectoring.buckpal.account.domain.AccountRepository;
import lombok.RequiredArgsConstructor;

import javax.money.MonetaryAmount;
import java.time.ZonedDateTime;

@RequiredArgsConstructor
public class FundsWithdrawalService implements IWithdrawFunds {
    private final AccountRepository accountRepository;
    private final IPublishDomainEvents iPublishDomainEvents;

    @Override
    public void withdraw(AccountId accountId, MonetaryAmount monetaryAmount, ZonedDateTime withdrawDateTime, String description) {
        accountRepository.findById(accountId)
                         .ifPresent(account -> account.withdraw(monetaryAmount, withdrawDateTime, description));
        iPublishDomainEvents.publish(new FundsWithdrawnEvent(accountId, monetaryAmount, withdrawDateTime, description));
    }
}
