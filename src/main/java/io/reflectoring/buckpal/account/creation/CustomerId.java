package io.reflectoring.buckpal.account.creation;

import io.reflectoring.buckpal.generic.EntityId;

class CustomerId extends EntityId<String> {
    public CustomerId(String id) {
        super(id);
    }
}
