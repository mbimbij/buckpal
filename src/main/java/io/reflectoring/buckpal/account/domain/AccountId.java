package io.reflectoring.buckpal.account.domain;

import io.reflectoring.buckpal.generic.EntityId;

public class AccountId extends EntityId<String> {
    public AccountId(String id) {
        super(id);
    }
}
