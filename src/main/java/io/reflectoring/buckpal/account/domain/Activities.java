package io.reflectoring.buckpal.account.domain;

import java.util.ArrayList;
import java.util.List;

public class Activities {
    private final List<Activity> activityList = new ArrayList<>();

    public int size(){
        return activityList.size();
    }

    public void add(Activity activity) {
        activityList.add(activity);
    }

    public Activity get(int i) {
        return activityList.get(i);
    }
}
