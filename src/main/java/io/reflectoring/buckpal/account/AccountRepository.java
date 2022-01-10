package io.reflectoring.buckpal.account;

import java.util.Optional;

public interface AccountRepository {
    Optional<Account> findById(AccountId accountId);
}
