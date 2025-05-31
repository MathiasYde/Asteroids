package com.mathiasyde.GameEngine;

import java.util.ArrayList;
import java.util.List;

class ScheduledAction {
    public float time;
    public Runnable action;

    public ScheduledAction(float time, Runnable action) {
        this.time = time;
        this.action = action;
    }
}

public class Time {
    public static float deltaTime = 0f;
    public static float totalTime;

    private static List<ScheduledAction> scheduledActions = new ArrayList<>();

    public static void after(float time, Runnable action) {
        scheduledActions.add(new ScheduledAction(time, action));
    }

    public static void update() {
        totalTime += deltaTime;
        scheduledActions.removeIf(scheduledAction -> {
                    scheduledAction.time -= deltaTime;
                    if (scheduledAction.time <= 0) {
                        scheduledAction.action.run();
                        return true;
                    }

                    return false;
            }
        );
    }
}
