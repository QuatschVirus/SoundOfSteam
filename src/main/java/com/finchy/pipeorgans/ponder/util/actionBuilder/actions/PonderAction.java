package com.finchy.pipeorgans.ponder.util.actionBuilder.actions;

import com.finchy.pipeorgans.ponder.util.PonderContext;
import com.finchy.pipeorgans.ponder.util.actionBuilder.ActionBuilder;
import com.finchy.pipeorgans.ponder.util.actionBuilder.timing.keeper.TimeKeeper;
import com.finchy.pipeorgans.ponder.util.actionBuilder.timing.override.TimingOverride;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Represents an action that can be executed within a Ponder context.
 */
public abstract class PonderAction {
    protected final PonderContext context;
    protected final TimeKeeper timeKeeper;

    protected boolean completed = false;

    public PonderAction(@NotNull PonderContext context, @NotNull TimeKeeper timeKeeper) {
        this.context = context;
        this.timeKeeper = timeKeeper;
    }

    /**
     * Returns a unique key representing the type of action.
     * The default implementation uses the action's class as the key.
     * @return the action type key
     */
    @NotNull
    public Object actionTypeKey() {
        return this.getClass();
    }

    /**
     * Returns a unique key representing the specific instance of the action.
     * The {@link ActionBuilder} uses this to associate operations to actions with {@link java.util.Objects#equals(Object, Object)}.
     * @return the action instance key
     */
    public abstract @NotNull Object actionInstanceKey();

    /**
     * Executes the action within the given Ponder context.
     * Note that an action should usually only use a single instruction und should always use the provided duration parameter to determine how long it should last.
     * @param duration the duration for which the action's instruction should be executed, in ticks
     */
    public abstract void execute(int duration);

    public TimingOverride getDefaultDurationOverride() {
        return TimingOverride.NONE;
    }

    public TimeKeeper getTimeKeeper() {
        return timeKeeper;
    }

    public void complete() {
        completed = true;
        timeKeeper.close();
    }

    public boolean isCompleted() {
        return completed && timeKeeper.isCompleted();
    }

    /**
     * Indicates whether this action is an idle action.
     * Idle actions do not perform any operations and are used to introduce delays.
     * @return true if the action is an idle action, false otherwise
     */
    public boolean idles() {
        return false;
    }

    /**
     * Indicates whether this action is an instant action.
     * Instant actions are executed immediately without any duration. They may still have buffers before and after them.
     * @return true if the action is an instant action, false otherwise
     */
    public boolean instantAction() {
        return false;
    }

    public int getMinimumPreBuffer(PonderAction previousNonIdle) {
        return 5;
    }

    public int getMinimumPostBuffer(PonderAction nextNonIdle) {
        return 5;
    }

    /**
     * An optional action that is added when the action is automatically closed, for example when the {@link ActionBuilder} is executed and this action is still open.
     * @return the automatic post-close action, or null if none
     */
    public @Nullable PonderAction automaticPostCloseAction() {
        return null;
    }

    public void consumeAction(PonderAction action) {
        timeKeeper.consumeActionIfOpen(action);
    }
}
