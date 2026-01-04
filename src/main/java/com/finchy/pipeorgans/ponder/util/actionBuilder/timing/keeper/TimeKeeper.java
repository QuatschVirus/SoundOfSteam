package com.finchy.pipeorgans.ponder.util.actionBuilder.timing.keeper;

import com.finchy.pipeorgans.ponder.util.actionBuilder.actions.PonderAction;

public abstract class TimeKeeper {
    protected boolean closed = false;

    public abstract boolean isCompleted();
    protected abstract void consumeAction(PonderAction action);

    public void consumeActionIfOpen(PonderAction action) {
        if (!closed) {
            consumeAction(action);
        }
    }

    public void close() {
        closed = true;
    }

    public abstract int getTime();

    public static final TimeKeeper NONE = new TimeKeeper() {
        @Override
        public boolean isCompleted() {
            return true;
        }

        @Override
        protected void consumeAction(PonderAction action) {
            // No action needed
        }

        @Override
        public int getTime() {
            return 0;
        }
    };
}