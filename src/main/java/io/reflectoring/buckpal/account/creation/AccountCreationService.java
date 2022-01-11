package io.reflectoring.buckpal.account.creation;

import io.reflectoring.buckpal.account.application.port.in.ICreateAccount;
import io.reflectoring.buckpal.account.domain.Account;
import io.reflectoring.buckpal.account.application.port.out.IPublishDomainEvents;
import io.reflectoring.buckpal.account.domain.AccountFactory;
import io.reflectoring.buckpal.account.domain.CustomerId;

class AccountCreationService implements ICreateAccount {
    private final AccountFactory accountFactory;
    private final IPublishDomainEvents iPublishDomainEvents;

    public AccountCreationService(AccountFactory accountFactory, IPublishDomainEvents iPublishDomainEvents) {
        this.accountFactory = accountFactory;
        this.iPublishDomainEvents = iPublishDomainEvents;
    }

    @Override
    public void createAccount(CustomerId customerId) {
        Account account = accountFactory.createAccount(customerId);
        AccountCreatedEvent accountCreatedEvent = new AccountCreatedEvent(customerId, account.getId());
        iPublishDomainEvents.publish(accountCreatedEvent);
    }
}
