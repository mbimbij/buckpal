package io.reflectoring.buckpal.account.creation;

import io.reflectoring.buckpal.account.Account;
import io.reflectoring.buckpal.generic.IPublishDomainEvents;

public class AccountCreationService implements ICreateAccount {
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
