package io.reflectoring.buckpal.account.domain;

import io.reflectoring.buckpal.account.Activity;

import javax.money.MonetaryAmount;
import java.time.ZonedDateTime;

public class Debit extends Activity {
    private Debit(ActivityId activityId, MonetaryAmount monetaryAmount, ZonedDateTime dateTime, String description) {
        super(activityId, monetaryAmount, dateTime, description);
    }

    public static ActivityIdGenerator activityIdGenerator = new ActivityIdGeneratorRandomUUID();

    public static Activity newDebit(MonetaryAmount monetaryAmount,
                                    ZonedDateTime dateTime,
                                    String description) {
        return new Debit(activityIdGenerator.nextActivityId(), monetaryAmount, dateTime, description);
    }

    public static void setActivityIdGenerator(ActivityIdGenerator activityIdGenerator) {
        Debit.activityIdGenerator = activityIdGenerator;
    }
}
