package com.finchy.pipeorgans.ponder.util.actionBuilder.timing.keeper;

import com.finchy.pipeorgans.ponder.util.actionBuilder.actions.PonderAction;

import java.util.LinkedList;
import java.util.List;

public class MinimumUntilClosedTimeKeeper extends TimeKeeper {
    protected final List<TimeKeeper> incompleteDependencies = new LinkedList<>();
    protected int minimumTime;
    protected int accumulatedTime = 0;

    public MinimumUntilClosedTimeKeeper(int minimumTime) {
        this.minimumTime = minimumTime;
    }

    @Override
    public boolean isCompleted() {
        return incompleteDependencies.isEmpty() && closed;
    }

    @Override
    protected void consumeAction(PonderAction action) {
        TimeKeeper actionTimeKeeper = action.getTimeKeeper();
        if (!actionTimeKeeper.isCompleted()) {
            incompleteDependencies.add(actionTimeKeeper);
        } else {
            accumulatedTime += actionTimeKeeper.getTime();
        }
    }

    @Override
    public int getTime() {
        return Math.max(minimumTime, accumulatedTime);
    }
}
