package io.reflectoring.buckpal.account.creation;

import io.reflectoring.buckpal.account.application.port.in.CustomerRepository;
import io.reflectoring.buckpal.account.application.port.in.ICreateAccount;
import io.reflectoring.buckpal.account.domain.AccountFactory;
import io.reflectoring.buckpal.account.domain.AccountId;
import io.reflectoring.buckpal.account.domain.AccountIdGenerator;
import io.reflectoring.buckpal.account.domain.CustomerId;
import io.reflectoring.buckpal.account.rightside.MockPublisher;
import org.junit.jupiter.api.Test;

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

}