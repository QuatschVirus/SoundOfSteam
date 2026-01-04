package com.finchy.pipeorgans.ponder.util.actionBuilder.timing.keeper;

import com.finchy.pipeorgans.ponder.util.actionBuilder.actions.PonderAction;

public class ConstantTimeKeeper extends TimeKeeper {
    protected int time;
    public ConstantTimeKeeper(int time) {
        this.time = time;
    }

    @Override
    public boolean isCompleted() {
        return true;
    }

    @Override
    protected void consumeAction(PonderAction action) {}

    @Override
    public int getTime() {
        return time;
    }
}
