package io.reflectoring.buckpal.account.service.close;

import io.reflectoring.buckpal.account.adapter.out.InMemoryEventStore;
import io.reflectoring.buckpal.account.application.port.out.IPublishDomainEvents;
import io.reflectoring.buckpal.account.domain.Account;
import io.reflectoring.buckpal.account.domain.AccountId;
import io.reflectoring.buckpal.account.domain.AccountRepository;
import io.reflectoring.buckpal.account.utils.TestUtils;
import org.javamoney.moneta.Money;
import org.junit.jupiter.api.Test;
import org.springframework.data.util.Pair;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

class CloseAccountServiceShould {
    @Test
    void allowTheCustomerToCloseACheckingAccount_onlyIfTheBalanceIsZero() {
        // GIVEN
        AccountId accountId = new AccountId("id");
        Account account = new Account(accountId, Money.of(0, "EUR"));

        AccountRepository accountRepository = mock(AccountRepository.class);
        doReturn(Optional.of(account)).when(accountRepository).findById(any());

        Pair<IPublishDomainEvents, InMemoryEventStore> pair = TestUtils.createInMemoryEventPublisherAndEventStore();
        InMemoryEventStore eventStore = pair.getSecond();
        IPublishDomainEvents domainEventPublisher = pair.getFirst();

        CloseAccountService closeAccountService = new CloseAccountService(accountRepository, domainEventPublisher);

        // WHEN
        closeAccountService.closeAccount(accountId);

        // THEN
        AccountClosedEvent expectedEvent = new AccountClosedEvent(accountId);
        assertThat(eventStore.getAll()).hasSize(1);
        assertThat(eventStore.getAll().get(0)).isEqualTo(expectedEvent);
    }

    @Test
    void notAllowTheCustomerToCloseACheckingAccount_ifTheBalanceIsNotZero() {
        // GIVEN
        AccountId accountId = new AccountId("id");
        Account account = new Account(accountId, Money.of(10, "EUR"));

        AccountRepository accountRepository = mock(AccountRepository.class);
        doReturn(Optional.of(account)).when(accountRepository).findById(any());

        Pair<IPublishDomainEvents, InMemoryEventStore> pair = TestUtils.createInMemoryEventPublisherAndEventStore();
        InMemoryEventStore eventStore = pair.getSecond();
        IPublishDomainEvents domainEventPublisher = pair.getFirst();

        CloseAccountService closeAccountService = new CloseAccountService(accountRepository, domainEventPublisher);

        // WHEN
        assertThatThrownBy(() -> closeAccountService.closeAccount(accountId))
                .isInstanceOf(ClosingAccountWithNonZeroBalanceException.class);

        // THEN
        assertThat(eventStore.getAll()).isEmpty();
    }

    @Test
    void notAllowTheCustomerToCloseAnAccount_ifAlreadyClosed() {
        // GIVEN
        AccountId accountId = new AccountId("id");
        Account account = new Account(accountId, Money.of(0, "EUR"));

        AccountRepository accountRepository = mock(AccountRepository.class);
        doReturn(Optional.of(account)).when(accountRepository).findById(any());

        Pair<IPublishDomainEvents, InMemoryEventStore> pair = TestUtils.createInMemoryEventPublisherAndEventStore();
        InMemoryEventStore eventStore = pair.getSecond();
        IPublishDomainEvents domainEventPublisher = pair.getFirst();

        CloseAccountService closeAccountService = new CloseAccountService(accountRepository, domainEventPublisher);

        assertThat(eventStore.getAll()).isEmpty();
        closeAccountService.closeAccount(accountId);
        assertThat(eventStore.getAll()).hasSize(1);

        // WHEN
        assertThatThrownBy(() -> closeAccountService.closeAccount(accountId))
                .isInstanceOf(AccountAlreadyClosedException.class);

        // THEN
        assertThat(eventStore.getAll()).hasSize(1);
    }
}