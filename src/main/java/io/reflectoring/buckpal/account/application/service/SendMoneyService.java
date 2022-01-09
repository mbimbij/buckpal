package io.reflectoring.buckpal.account.application.service;

import io.reflectoring.buckpal.account.application.port.in.LoadAccountPort;
import io.reflectoring.buckpal.account.application.port.in.LockAccountPort;
import io.reflectoring.buckpal.account.application.port.in.SendMoneyCommand;
import io.reflectoring.buckpal.account.application.port.in.UpdateAccountPort;
import io.reflectoring.buckpal.account.domain.Account;
import io.reflectoring.buckpal.account.domain.AccountId;
import io.reflectoring.buckpal.account.domain.ActivityFactory;
import lombok.RequiredArgsConstructor;
import org.javamoney.moneta.Money;

@RequiredArgsConstructor
public class SendMoneyService {

    private final LoadAccountPort loadAccountPort;
    private final LockAccountPort lockAccountPort;
    private final UpdateAccountPort updateAccountPort;
    private final ActivityFactory activityFactory;

    public void sendMoney(SendMoneyCommand sendMoneyCommand) {
        Money monetaryAmount = sendMoneyCommand.getAmount();
        AccountId sourceId = sendMoneyCommand.getSource();
        AccountId destinationId = sendMoneyCommand.getDestination();
        Account sourceAccount = loadAccountPort.loadAccount(sourceId);
        Account destinationAccount = loadAccountPort.loadAccount(destinationId);

        lockAccountPort.lock(sourceId);
        lockAccountPort.lock(destinationId);

        sourceAccount.transferOut(activityFactory, monetaryAmount, destinationId);
        destinationAccount.transferIn(activityFactory, monetaryAmount, sourceId);

        updateAccountPort.updateAccount(sourceAccount);
        updateAccountPort.updateAccount(destinationAccount);

        lockAccountPort.unlock(sourceId);
        lockAccountPort.unlock(destinationId);
    }
}
