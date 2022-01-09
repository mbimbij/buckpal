package io.reflectoring.buckpal.account.application.port.in;

import io.reflectoring.buckpal.account.domain.AccountId;
import org.javamoney.moneta.Money;

import lombok.Value;

@Value
public class SendMoneyCommand {
    AccountId source;
    AccountId destination;
    Money amount;
}
