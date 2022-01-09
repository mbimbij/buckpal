package io.reflectoring.buckpal.account.domain;

import io.reflectoring.buckpal.account.domain.event.MoneyTransferred;
import io.reflectoring.buckpal.generic.Id;
import org.assertj.core.api.SoftAssertions;
import org.javamoney.moneta.Money;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class AccountShould {

    @Test
    void createAnActivity_whenTransferOut() {
        // GIVEN
        Account account = new Account(new AccountId("AccountId"));
        AccountId destination = new AccountId("destination");
        assertThat(account.getActivities().size()).isEqualTo(0);

        // WHEN
        account.transferOut(Money.of(10, "EUR"), destination);

        // THEN
        assertThat(account.getActivities().size()).isEqualTo(1);
        Activity activity = account.getActivities().get(0);
        SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(activity.getMonetaryAmount()).isEqualTo(Money.of(-10, "EUR"));
            softAssertions.assertThat(activity.getAccountId()).isEqualTo(destination);
        });
    }

    @Test
    void createAnActivity_whenTransferIn() {
        // GIVEN
        Account account = new Account(new AccountId("AccountId"));
        AccountId source = new AccountId("source");
        assertThat(account.getActivities().size()).isEqualTo(0);

        // WHEN
        account.transferIn(Money.of(10, "EUR"), source);

        // THEN
        assertThat(account.getActivities().size()).isEqualTo(1);
        Activity activity = account.getActivities().get(0);
        SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(activity.getMonetaryAmount()).isEqualTo(Money.of(10, "EUR"));
            softAssertions.assertThat(activity.getAccountId()).isEqualTo(source);
        });
    }

    @Test
    void createAMoneyTransferredEvent_whenTransferOut() {
        // GIVEN
        Account account = new Account(new AccountId("AccountId"));
        AccountId destination = new AccountId("destination");
        assertThat(account.getActivities().size()).isEqualTo(0);
        Id someId = new Id("someId");
        Id.setIdSupplier(() -> someId);
        Money monetaryAmount = Money.of(10, "EUR");
        MoneyTransferred expectedMoneyTransferred = new MoneyTransferred(someId, monetaryAmount, account.getAccountId(), destination);

        // WHEN
        MoneyTransferred moneyTransferred = account.transferOut(monetaryAmount, destination);

        // THEN
        assertThat(moneyTransferred).isEqualTo(expectedMoneyTransferred);
    }

    @Test
    void createAMoneyTransferredEvent_whenTransferIn() {
        // GIVEN
        Account account = new Account(new AccountId("AccountId"));
        AccountId source = new AccountId("source");
        assertThat(account.getActivities().size()).isEqualTo(0);
        Id someId = new Id("someId");
        Id.setIdSupplier(() -> someId);
        Money monetaryAmount = Money.of(10, "EUR");
        MoneyTransferred expectedMoneyTransferred = new MoneyTransferred(someId, monetaryAmount, source, account.getAccountId());

        // WHEN
        MoneyTransferred moneyTransferred = account.transferIn(monetaryAmount, source);

        // THEN
        assertThat(moneyTransferred).isEqualTo(expectedMoneyTransferred);
    }
}