package io.reflectoring.buckpal.account.consultation;

import io.reflectoring.buckpal.account.application.port.in.IGetAccountDetails;
import io.reflectoring.buckpal.account.domain.Account;
import io.reflectoring.buckpal.account.domain.AccountId;
import io.reflectoring.buckpal.account.AccountRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class AccountDetailsService implements IGetAccountDetails {
    private final AccountRepository accountRepository;

    @Override
    public Account getAccountDetails(AccountId accountId) {
        return accountRepository.findById(accountId).orElseThrow(AccountNotExistException::new);
    }
}
