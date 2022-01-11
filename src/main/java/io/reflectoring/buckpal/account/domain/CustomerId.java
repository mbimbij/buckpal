package io.reflectoring.buckpal.account.domain;

import io.reflectoring.buckpal.generic.EntityId;

public class CustomerId extends EntityId<String> {
    public CustomerId(String id) {
        super(id);
    }
}
