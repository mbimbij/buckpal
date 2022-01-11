package io.reflectoring.buckpal.account.service.withdraw;

import io.reflectoring.buckpal.account.adapter.out.InMemoryEventStore;
import io.reflectoring.buckpal.account.application.port.out.IPublishDomainEvents;
import io.reflectoring.buckpal.account.domain.Account;
import io.reflectoring.buckpal.account.domain.AccountId;
import io.reflectoring.buckpal.account.domain.AccountRepository;
import io.reflectoring.buckpal.account.utils.TestUtils;
import org.javamoney.moneta.Money;
import org.junit.jupiter.api.Test;
import org.springframework.data.util.Pair;

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
        Pair<IPublishDomainEvents, InMemoryEventStore> pair = TestUtils.createInMemoryEventPublisherAndEventStore();
        InMemoryEventStore eventStore = pair.getSecond();
        IPublishDomainEvents domainEventPublisher = pair.getFirst();
        AccountRepository accountRepository = mock(AccountRepository.class);
        FundsWithdrawalService fundsWithdrawalService = new FundsWithdrawalService(accountRepository, domainEventPublisher);

        AccountId accountId = new AccountId("id");
        MonetaryAmount monetaryAmount = Money.of(20, "EUR");
        ZonedDateTime withdrawDateTime = ZonedDateTime.now();
        String description = "some description";

        FundsWithdrawnEvent expectedFundsWithdrawnEvent = new FundsWithdrawnEvent(accountId, monetaryAmount, withdrawDateTime, description);

        // WHEN
        fundsWithdrawalService.withdraw(accountId, monetaryAmount, withdrawDateTime, description);

        // THEN
        assertThat(eventStore.getAll()).hasSize(1);
        assertThat(eventStore.getAll().get(0)).isEqualTo(expectedFundsWithdrawnEvent);
    }

    @Test
    void notAllowTheCustomerToWithdraw_whenInsufficientFunds() {
        // GIVEN
        Pair<IPublishDomainEvents, InMemoryEventStore> pair = TestUtils.createInMemoryEventPublisherAndEventStore();
        InMemoryEventStore eventStore = pair.getSecond();
        IPublishDomainEvents domainEventPublisher = pair.getFirst();
        AccountRepository accountRepository = mock(AccountRepository.class);
        AccountId accountId = new AccountId("id");
        Account account = new Account(accountId, Money.of(100, "EUR"));
        doReturn(Optional.of(account)).when(accountRepository).findById(eq(accountId));
        FundsWithdrawalService fundsWithdrawalService = new FundsWithdrawalService(accountRepository, domainEventPublisher);

        MonetaryAmount monetaryAmount = Money.of(200, "EUR");
        ZonedDateTime withdrawDateTime = ZonedDateTime.now();
        String description = "some description";

        // WHEN
        assertThatThrownBy(() -> fundsWithdrawalService.withdraw(accountId, monetaryAmount, withdrawDateTime, description))
                .isInstanceOf(InsufficientFundsException.class);

        // THEN
        assertThat(eventStore.getAll()).isEmpty();
    }
}