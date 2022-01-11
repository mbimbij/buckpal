package io.reflectoring.buckpal.account.service.creation;

import io.reflectoring.buckpal.account.adapter.out.InMemoryEventStore;
import io.reflectoring.buckpal.account.application.port.in.CustomerRepository;
import io.reflectoring.buckpal.account.application.port.in.ICreateAccount;
import io.reflectoring.buckpal.account.application.port.out.IPublishDomainEvents;
import io.reflectoring.buckpal.account.domain.AccountFactory;
import io.reflectoring.buckpal.account.domain.AccountId;
import io.reflectoring.buckpal.account.domain.AccountIdGenerator;
import io.reflectoring.buckpal.account.domain.CustomerId;
import io.reflectoring.buckpal.account.utils.TestUtils;
import org.junit.jupiter.api.Test;
import org.springframework.data.util.Pair;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

class AccountCreationServiceShould {

    @Test
    void createAccount_forExistingCustomer() {
        // GIVEN
        CustomerRepository customerRepository = mock(CustomerRepository.class);
        AccountIdGenerator accountIdGenerator = mock(AccountIdGenerator.class);
        AccountId accountId = new AccountId("id");
        doReturn(accountId).when(accountIdGenerator).nextAccountId();
        AccountFactory accountFactory = new AccountFactory(accountIdGenerator);
        Pair<IPublishDomainEvents, InMemoryEventStore> pair = TestUtils.createInMemoryEventPublisherAndEventStore();
        InMemoryEventStore eventStore = pair.getSecond();
        IPublishDomainEvents domainEventPublisher = pair.getFirst();

        ICreateAccount iCreateAccount = new AccountCreationService(accountFactory, domainEventPublisher);

        CustomerId customerId = new CustomerId("custid");

        AccountCreatedEvent expectedEvent = new AccountCreatedEvent(accountId, customerId);

        // WHEN
        iCreateAccount.createAccount(customerId);

        // THEN
        assertThat(eventStore.getAll()).hasSize(1);
        assertThat(eventStore.getAll().get(0)).isEqualTo(expectedEvent);
    }

}