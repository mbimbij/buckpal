package io.reflectoring.buckpal.account.domain;

import io.reflectoring.buckpal.account.Activity;

import javax.money.MonetaryAmount;
import java.time.ZonedDateTime;

public class Credit extends Activity {
    private Credit(ActivityId activityId, MonetaryAmount monetaryAmount, ZonedDateTime dateTime, String description) {
        super(activityId, monetaryAmount, dateTime, description);
    }

    public static ActivityIdGenerator activityIdGenerator = new ActivityIdGeneratorRandomUUID();

    public static Activity newCredit(MonetaryAmount monetaryAmount,
                                     ZonedDateTime dateTime,
                                     String description) {
        return new Credit(activityIdGenerator.nextActivityId(), monetaryAmount, dateTime, description);
    }

    public static void setActivityIdGenerator(ActivityIdGenerator activityIdGenerator) {
        Credit.activityIdGenerator = activityIdGenerator;
    }
}
