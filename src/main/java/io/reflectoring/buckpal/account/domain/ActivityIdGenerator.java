package io.reflectoring.buckpal.account.domain;

@FunctionalInterface
public interface ActivityIdGenerator {
    ActivityId nextActivityId();
}
