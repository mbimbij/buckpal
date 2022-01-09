package io.reflectoring.buckpal.account.application.port.in;

import io.reflectoring.buckpal.account.domain.Account;

public interface UpdateAccountPort {
    void updateAccount(Account account);
}
