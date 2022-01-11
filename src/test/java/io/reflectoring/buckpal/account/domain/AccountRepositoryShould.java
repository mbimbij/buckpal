package io.reflectoring.buckpal.account.domain;

import io.reflectoring.buckpal.account.adapter.out.InMemoryEventStore;
import io.reflectoring.buckpal.account.service.close.AccountClosedEvent;
import io.reflectoring.buckpal.account.service.creation.AccountCreatedEvent;
import io.reflectoring.buckpal.account.service.deposit.FundsDeposittedEvent;
import io.reflectoring.buckpal.account.service.withdraw.FundsWithdrawnEvent;
import org.assertj.core.api.SoftAssertions;
import org.javamoney.moneta.Money;
import org.junit.jupiter.api.Test;

import java.time.ZonedDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class AccountRepositoryShould {
    @Test
    void getAccountStateFromEvents() {
        // GIVEN
        CustomerId customerId1 = new CustomerId("id1");
        CustomerId customerId2 = new CustomerId("id2");
        AccountId accountId1 = new AccountId("1");
        AccountId accountId2 = new AccountId("2");

        ZonedDateTime depositDateTime = ZonedDateTime.now();
        ZonedDateTime withdrawDateTime = depositDateTime.plusDays(1);

        InMemoryEventStore eventStore = new InMemoryEventStore();
        eventStore.add(new AccountCreatedEvent(accountId1, customerId1));
        eventStore.add(new AccountCreatedEvent(accountId2, customerId2));
        eventStore.add(new AccountClosedEvent(accountId1));
        eventStore.add(new FundsDeposittedEvent(accountId2, Money.of(100, "EUR"), depositDateTime, "deposit"));
        eventStore.add(new FundsWithdrawnEvent(accountId2, Money.of(20, "EUR"), withdrawDateTime, "withdrawal"));

        AccountRepository accountRepository = new AccountRepository(eventStore);

        // WHEN
        Optional<Account> accountOpt1 = accountRepository.findById(accountId1);

        // THEN
        assertThat(accountOpt1).isNotEmpty();
        Account account1 = accountOpt1.get();
        SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(account1.getCustomerId()).isEqualTo(customerId1);
            softAssertions.assertThat(account1.isClosed()).isTrue();
            softAssertions.assertThat(account1.getBalance()).isEqualTo(Money.of(0, "EUR"));
            softAssertions.assertThat(account1.getActivities()).isEmpty();
        });

        // WHEN
        Optional<Account> accountOpt2 = accountRepository.findById(accountId2);

        // THEN
        assertThat(accountOpt2).isNotEmpty();
        Account account2 = accountOpt2.get();
        SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(account2.getCustomerId()).isEqualTo(customerId2);
            softAssertions.assertThat(account2.isClosed()).isFalse();
            softAssertions.assertThat(account2.getBalance()).isEqualTo(Money.of(80, "EUR"));
            softAssertions.assertThat(account2.getActivities()).hasSize(2);
        });
    }
}