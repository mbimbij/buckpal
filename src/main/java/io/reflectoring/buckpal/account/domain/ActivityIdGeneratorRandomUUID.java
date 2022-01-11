package io.reflectoring.buckpal.account.domain;

import java.util.UUID;

public class ActivityIdGeneratorRandomUUID implements ActivityIdGenerator {
    @Override
    public ActivityId nextActivityId() {
        return new ActivityId(UUID.randomUUID().toString());
    }
}
