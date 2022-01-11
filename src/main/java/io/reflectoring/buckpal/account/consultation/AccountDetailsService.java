package io.reflectoring.buckpal.account.consultation;

import io.reflectoring.buckpal.account.Account;
import io.reflectoring.buckpal.account.AccountId;
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
