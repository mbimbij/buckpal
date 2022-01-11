package io.reflectoring.buckpal.account.close;

import io.reflectoring.buckpal.account.Account;
import io.reflectoring.buckpal.account.AccountId;
import io.reflectoring.buckpal.account.AccountRepository;
import io.reflectoring.buckpal.generic.IPublishDomainEvents;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CloseAccountService implements ICloseAccount {
    private final AccountRepository accountRepository;
    private final IPublishDomainEvents iPublishDomainEvents;

    @Override
    public void closeAccount(AccountId accountId) {
        accountRepository.findById(accountId)
                         .ifPresent(Account::close);

        iPublishDomainEvents.publish(new AccountClosedEvent(accountId));
    }
}
