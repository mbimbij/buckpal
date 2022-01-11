package io.reflectoring.buckpal.account;

@FunctionalInterface
public interface ActivityIdGenerator {
    ActivityId nextActivityId();
}
