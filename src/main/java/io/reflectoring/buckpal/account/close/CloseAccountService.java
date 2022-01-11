package io.reflectoring.buckpal.account.close;

import io.reflectoring.buckpal.account.application.port.in.ICloseAccount;
import io.reflectoring.buckpal.account.domain.Account;
import io.reflectoring.buckpal.account.domain.AccountId;
import io.reflectoring.buckpal.account.AccountRepository;
import io.reflectoring.buckpal.account.application.port.out.IPublishDomainEvents;
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
