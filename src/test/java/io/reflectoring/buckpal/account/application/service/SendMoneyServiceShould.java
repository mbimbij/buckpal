package io.reflectoring.buckpal.account.application.service;

import io.reflectoring.buckpal.account.application.port.in.LoadAccountPort;
import io.reflectoring.buckpal.account.application.port.in.LockAccountPort;
import io.reflectoring.buckpal.account.application.port.in.SendMoneyCommand;
import io.reflectoring.buckpal.account.application.port.in.UpdateAccountPort;
import io.reflectoring.buckpal.account.domain.Account;
import io.reflectoring.buckpal.account.domain.AccountId;
import org.javamoney.moneta.Money;
import org.junit.jupiter.api.Test;

import javax.money.Monetary;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

public class SendMoneyServiceShould {
    @Test
    void sendMoney_inNominalCase() {
        // GIVEN
        AccountId sourceId = new AccountId("source");
        AccountId destinationId = new AccountId("destination");
        Account source = spy(new Account(sourceId));
        Account destination = spy(new Account(destinationId));

        LoadAccountPort loadAccountPort = mock(LoadAccountPort.class);
        doReturn(source).when(loadAccountPort).loadAccount(eq(sourceId));
        doReturn(destination).when(loadAccountPort).loadAccount(eq(destinationId));
        UpdateAccountPort updateAccountPort = mock(UpdateAccountPort.class);
        LockAccountPort accountLock = mock(LockAccountPort.class);

        SendMoneyService sendMoneyService = new SendMoneyService(loadAccountPort, accountLock, updateAccountPort);

        Money eur10 = Money.of(10, Monetary.getCurrency("EUR"));
        SendMoneyCommand sendMoneyCommand = new SendMoneyCommand(sourceId, destinationId, eur10);

        // WHEN
        sendMoneyService.sendMoney(sendMoneyCommand);

        // THEN
        verify(loadAccountPort).loadAccount(eq(sourceId));
        verify(loadAccountPort).loadAccount(eq(destinationId));

        verify(accountLock).lock(eq(sourceId));
        verify(accountLock).lock(eq(destinationId));

        verify(source).transferOut(eur10, destinationId);
        verify(destination).transferIn(eur10, sourceId);

        verify(updateAccountPort).updateAccount(eq(source));
        verify(updateAccountPort).updateAccount(eq(destination));

        verify(accountLock).unlock(eq(sourceId));
        verify(accountLock).unlock(eq(destinationId));
    }
}
