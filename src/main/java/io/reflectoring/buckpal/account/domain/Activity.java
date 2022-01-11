package io.reflectoring.buckpal.account.domain;

import io.reflectoring.buckpal.account.domain.ActivityId;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

import javax.money.MonetaryAmount;
import java.time.ZonedDateTime;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public abstract class Activity {
    ActivityId activityId;
    MonetaryAmount monetaryAmount;
    ZonedDateTime dateTime;
    String description;
}
