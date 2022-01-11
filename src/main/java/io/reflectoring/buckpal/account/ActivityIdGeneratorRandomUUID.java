package io.reflectoring.buckpal.account;

import java.util.UUID;

public class ActivityIdGeneratorRandomUUID implements ActivityIdGenerator {
    @Override
    public ActivityId nextActivityId() {
        return new ActivityId(UUID.randomUUID().toString());
    }
}
