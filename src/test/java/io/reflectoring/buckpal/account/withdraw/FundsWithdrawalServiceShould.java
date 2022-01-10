package io.reflectoring.buckpal.account.withdraw;

import io.reflectoring.buckpal.account.Account;
import io.reflectoring.buckpal.account.AccountId;
import io.reflectoring.buckpal.account.AccountRepository;
import io.reflectoring.buckpal.account.rightside.MockPublisher;
import org.javamoney.moneta.Money;
import org.junit.jupiter.api.Test;

import javax.money.MonetaryAmount;
import java.time.ZonedDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

class FundsWithdrawalServiceShould {
    @Test
    void allowTheCustomerToWithdraw_whenSufficientFunds() {
        // GIVEN
        MockPublisher mockPublisher = new MockPublisher();
        AccountRepository accountRepository = mock(AccountRepository.class);
        FundsWithdrawalService fundsWithdrawalService = new FundsWithdrawalService(accountRepository, mockPublisher);

        AccountId accountId = new AccountId("id");
        MonetaryAmount monetaryAmount = Money.of(20, "EUR");
        ZonedDateTime withdrawDateTime = ZonedDateTime.now();
        String description = "some description";

        FundsWithdrawnEvent expectedFundsWithdrawnEvent = new FundsWithdrawnEvent(accountId, monetaryAmount, withdrawDateTime, description);

        // WHEN
        fundsWithdrawalService.withdraw(accountId, monetaryAmount, withdrawDateTime, description);

        // THEN
        assertThat(mockPublisher.getDomainEvents()).hasSize(1);
        assertThat(mockPublisher.getDomainEvents().get(0)).isEqualTo(expectedFundsWithdrawnEvent);
    }

    @Test
    void notAllowTheCustomerToWithdraw_whenInsufficientFunds() {
        // GIVEN
        MockPublisher mockPublisher = new MockPublisher();
        AccountRepository accountRepository = mock(AccountRepository.class);
        AccountId accountId = new AccountId("id");
        Account account = new Account(accountId, Money.of(100, "EUR"));
        doReturn(Optional.of(account)).when(accountRepository).findById(eq(accountId));
        FundsWithdrawalService fundsWithdrawalService = new FundsWithdrawalService(accountRepository, mockPublisher);

        MonetaryAmount monetaryAmount = Money.of(200, "EUR");
        ZonedDateTime withdrawDateTime = ZonedDateTime.now();
        String description = "some description";

        // WHEN
        assertThatThrownBy(() -> fundsWithdrawalService.withdraw(accountId, monetaryAmount, withdrawDateTime, description))
                .isInstanceOf(InsufficientFundsException.class);

        // THEN
        assertThat(mockPublisher.getDomainEvents()).isEmpty();
    }
}