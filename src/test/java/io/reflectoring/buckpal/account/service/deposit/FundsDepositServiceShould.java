package io.reflectoring.buckpal.account.service.deposit;

import io.reflectoring.buckpal.account.adapter.out.InMemoryEventStore;
import io.reflectoring.buckpal.account.application.port.out.IPublishDomainEvents;
import io.reflectoring.buckpal.account.domain.AccountId;
import io.reflectoring.buckpal.account.domain.AccountRepository;
import io.reflectoring.buckpal.account.domain.DomainEvent;
import io.reflectoring.buckpal.account.utils.TestUtils;
import org.javamoney.moneta.Money;
import org.junit.jupiter.api.Test;
import org.springframework.data.util.Pair;

import javax.money.MonetaryAmount;
import java.time.ZonedDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

class FundsDepositServiceShould {
    @Test
    void allowACustomerToDepositFundsIntoAnExistingAccount() {
        // GIVEN
        Pair<IPublishDomainEvents, InMemoryEventStore> pair = TestUtils.createInMemoryEventPublisherAndEventStore();
        InMemoryEventStore eventStore = pair.getSecond();
        IPublishDomainEvents domainEventPublisher = pair.getFirst();
        FundsDepositService fundsDepositService = new FundsDepositService(mock(AccountRepository.class), domainEventPublisher);
        AccountId accountId = new AccountId("id");
        MonetaryAmount monetaryAmount = Money.of(10, "EUR");
        ZonedDateTime depositDateTime = ZonedDateTime.now();
        String description = "some description";

        FundsDeposittedEvent expectedFundsDeposittedEvent = new FundsDeposittedEvent(accountId, monetaryAmount, depositDateTime, description);

        // WHEN
        fundsDepositService.deposit(accountId, monetaryAmount, depositDateTime, description);

        // THEN
        assertThat(eventStore.getAll()).hasSize(1);
        DomainEvent domainEvent = eventStore.getAll().get(0);
        assertThat(domainEvent).isEqualTo(expectedFundsDeposittedEvent);
    }
}