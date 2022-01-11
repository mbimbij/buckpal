package io.reflectoring.buckpal.account.application.port.in;

import io.reflectoring.buckpal.account.domain.CustomerId;

public interface ICreateAccount {
    void createAccount(CustomerId customerId);
}
