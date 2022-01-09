package io.reflectoring.buckpal.account.creation;

import io.reflectoring.buckpal.account.AccountId;
import io.reflectoring.buckpal.generic.DomainEvent;
import io.reflectoring.buckpal.generic.IPublishDomainEvents;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class AccountCreationServiceShould {

    @Test
    void createAccount_forExistingCustomer() {
        // GIVEN
        CustomerRepository customerRepository = mock(CustomerRepository.class);
        AccountIdGenerator accountIdGenerator = mock(AccountIdGenerator.class);
        AccountId accountId = new AccountId("id");
        doReturn(accountId).when(accountIdGenerator).nextAccountId();
        AccountFactory accountFactory = new AccountFactory(accountIdGenerator);
        MockPublisher mockPublisher = new MockPublisher();

        ICreateAccount iCreateAccount = new AccountCreationService(accountFactory, mockPublisher);

        CustomerId customerId = new CustomerId("custid");

        AccountCreatedEvent expectedEvent = new AccountCreatedEvent(customerId,accountId);

        // WHEN
        iCreateAccount.createAccount(customerId);

        // THEN
        assertThat(mockPublisher.getDomainEvents()).hasSize(1);
        assertThat(mockPublisher.getDomainEvents().get(0)).isEqualTo(expectedEvent);
    }

    public static class MockPublisher implements IPublishDomainEvents {
        private List<DomainEvent> domainEvents = new ArrayList<>();

        @Override
        public void publish(DomainEvent domainEvent) {
            domainEvents.add(domainEvent);
        }

        public List<DomainEvent> getDomainEvents() {
            return domainEvents;
        }
    }
}