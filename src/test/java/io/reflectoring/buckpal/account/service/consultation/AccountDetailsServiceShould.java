package io.reflectoring.buckpal.account.service.consultation;

import io.reflectoring.buckpal.account.domain.Account;
import io.reflectoring.buckpal.account.domain.AccountId;
import io.reflectoring.buckpal.account.application.port.out.AccountRepository;
import io.reflectoring.buckpal.account.domain.ActivityId;
import io.reflectoring.buckpal.account.domain.ActivityIdGenerator;
import io.reflectoring.buckpal.account.domain.Credit;
import io.reflectoring.buckpal.account.domain.Debit;
import org.javamoney.moneta.Money;
import org.junit.jupiter.api.Test;

import java.time.ZonedDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

class AccountDetailsServiceShould {
    @Test
    void getAccountDetails() {
        // GIVEN
        ActivityId activityId = new ActivityId("id");
        ActivityIdGenerator activityIdGenerator = mock(ActivityIdGenerator.class);
        doReturn(activityId).when(activityIdGenerator).nextActivityId();
        Credit.setActivityIdGenerator(activityIdGenerator);
        Debit.setActivityIdGenerator(activityIdGenerator);

        AccountId accountId = new AccountId("id");
        Account account = new Account(accountId);
        ZonedDateTime depositDateTime = ZonedDateTime.now();
        ZonedDateTime withdrawalDateTime = ZonedDateTime.now().plusDays(1);
        String depositDescription = "deposit";
        String withdrawalDescription = "withdrawal";
        account.deposit(Money.of(100, "EUR"), depositDateTime, depositDescription);
        account.withdraw(Money.of(20, "EUR"), withdrawalDateTime, withdrawalDescription);

        AccountRepository accountRepository = mock(AccountRepository.class);
        doReturn(Optional.of(account)).when(accountRepository).findById(eq(accountId));

        AccountDetailsService accountDetailsService = new AccountDetailsService(accountRepository);

        // WHEN
        Account accountDetails = accountDetailsService.getAccountDetails(accountId);

        // THEN
        assertThat(accountDetails.getBalance()).isEqualTo(Money.of(80, "EUR"));
        assertThat(accountDetails.getActivities()).containsExactly(
                Credit.newCredit(Money.of(100, "EUR"), depositDateTime, depositDescription),
                Debit.newDebit(Money.of(20, "EUR"), withdrawalDateTime, withdrawalDescription)
        );
    }
}