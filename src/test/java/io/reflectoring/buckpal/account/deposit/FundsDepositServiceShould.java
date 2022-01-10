package io.reflectoring.buckpal.account.deposit;

import io.reflectoring.buckpal.account.AccountId;
import io.reflectoring.buckpal.account.rightside.MockPublisher;
import io.reflectoring.buckpal.generic.DomainEvent;
import org.javamoney.moneta.Money;
import org.junit.jupiter.api.Test;

import javax.money.MonetaryAmount;
import java.time.ZonedDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class FundsDepositServiceShould {
    @Test
    void allowACustomerToDepositFundsIntoAnExistingAccount() {
        // GIVEN
        MockPublisher mockPublisher = new MockPublisher();
        FundsDepositService fundsDepositService = new FundsDepositService(mockPublisher);
        AccountId accountId = new AccountId("id");
        MonetaryAmount monetaryAmount = Money.of(10, "EUR");
        ZonedDateTime depositDateTime = ZonedDateTime.now();
        String description = "some description";

        FundsDeposittedEvent expectedFundsDeposittedEvent = new FundsDeposittedEvent(accountId, monetaryAmount, depositDateTime, description);

        // WHEN
        fundsDepositService.deposit(accountId, monetaryAmount, depositDateTime, description);

        // THEN
        assertThat(mockPublisher.getDomainEvents()).hasSize(1);
        DomainEvent domainEvent = mockPublisher.getDomainEvents().get(0);
        assertThat(domainEvent).isEqualTo(expectedFundsDeposittedEvent);
    }
}