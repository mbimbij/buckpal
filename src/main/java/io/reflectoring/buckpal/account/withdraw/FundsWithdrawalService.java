package io.reflectoring.buckpal.account.withdraw;

import io.reflectoring.buckpal.account.AccountId;
import io.reflectoring.buckpal.account.AccountRepository;
import io.reflectoring.buckpal.generic.IPublishDomainEvents;
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
                         .ifPresent(account -> account.withdraw(monetaryAmount));
        iPublishDomainEvents.publish(new FundsWithdrawnEvent(accountId, monetaryAmount, withdrawDateTime, description));
    }
}
