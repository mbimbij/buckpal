package io.reflectoring.buckpal.account.close;

import io.reflectoring.buckpal.account.domain.Account;
import io.reflectoring.buckpal.account.domain.AccountId;
import io.reflectoring.buckpal.account.AccountRepository;
import io.reflectoring.buckpal.account.rightside.MockPublisher;
import org.javamoney.moneta.Money;
import org.junit.jupiter.api.Test;

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

        MockPublisher mockPublisher = new MockPublisher();

        CloseAccountService closeAccountService = new CloseAccountService(accountRepository, mockPublisher);

        // WHEN
        closeAccountService.closeAccount(accountId);

        // THEN
        AccountClosedEvent expectedEvent = new AccountClosedEvent(accountId);
        assertThat(mockPublisher.getDomainEvents()).hasSize(1);
        assertThat(mockPublisher.getDomainEvents().get(0)).isEqualTo(expectedEvent);
    }

    @Test
    void notAllowTheCustomerToCloseACheckingAccount_ifTheBalanceIsNotZero() {
        // GIVEN
        AccountId accountId = new AccountId("id");
        Account account = new Account(accountId, Money.of(10, "EUR"));

        AccountRepository accountRepository = mock(AccountRepository.class);
        doReturn(Optional.of(account)).when(accountRepository).findById(any());

        MockPublisher mockPublisher = new MockPublisher();

        CloseAccountService closeAccountService = new CloseAccountService(accountRepository, mockPublisher);

        // WHEN
        assertThatThrownBy(() -> closeAccountService.closeAccount(accountId))
                .isInstanceOf(ClosingAccountWithNonZeroBalanceException.class);

        // THEN
        assertThat(mockPublisher.getDomainEvents()).isEmpty();
    }

    @Test
    void notAllowTheCustomerToCloseAnAccount_ifAlreadyClosed() {
        // GIVEN
        AccountId accountId = new AccountId("id");
        Account account = new Account(accountId, Money.of(0, "EUR"));

        AccountRepository accountRepository = mock(AccountRepository.class);
        doReturn(Optional.of(account)).when(accountRepository).findById(any());

        MockPublisher mockPublisher = new MockPublisher();

        CloseAccountService closeAccountService = new CloseAccountService(accountRepository, mockPublisher);

        assertThat(mockPublisher.getDomainEvents()).isEmpty();
        closeAccountService.closeAccount(accountId);
        assertThat(mockPublisher.getDomainEvents()).hasSize(1);

        // WHEN
        assertThatThrownBy(() -> closeAccountService.closeAccount(accountId))
                .isInstanceOf(AccountAlreadyClosedException.class);

        // THEN
        assertThat(mockPublisher.getDomainEvents()).hasSize(1);
    }
}