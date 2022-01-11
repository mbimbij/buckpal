package io.reflectoring.buckpal.account.application.port.out;

import io.reflectoring.buckpal.account.domain.Account;
import io.reflectoring.buckpal.account.domain.AccountId;

import java.util.Optional;

public interface AccountRepository {
    Optional<Account> findById(AccountId accountId);
}
