package com.finchy.pipeorgans.ponder.util.actionBuilder.actions;

import com.finchy.pipeorgans.ponder.util.PonderContext;
import com.finchy.pipeorgans.ponder.util.actionBuilder.ActionBuilder;
import com.finchy.pipeorgans.ponder.util.actionBuilder.timing.keeper.TimeKeeper;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class IdleAction extends PonderAction {
    public IdleAction(@NotNull PonderContext context, @NotNull TimeKeeper timeKeeper) {
        super(context, timeKeeper);
    }

    /**
     * Returns a unique key representing the specific instance of the action.
     * The {@link ActionBuilder} uses this to associate operations to actions with {@link Objects#equals(Object, Object)}.
     *
     * @return the action instance key
     */
    @Override
    public @NotNull Object actionInstanceKey() {
        return new Object();
    }

    /**
     * Executes the action within the given Ponder context.
     * Note that an action should usually only use a single instruction und should always use the provided duration parameter to determine how long it should last.
     *
     * @param duration the duration for which the action's instruction should be executed, in ticks
     */
    @Override
    public void execute(int duration) {
        context.sceneBuilder().idle(duration);
    }

    @Override
    public int getMinimumPostBuffer(PonderAction nextNonIdle) {
        return 0;
    }

    @Override
    public int getMinimumPreBuffer(PonderAction previousNonIdle) {
        return 0;
    }

    /**
     * Indicates whether this action is an idle action.
     * Idle actions do not perform any operations and are used to introduce delays.
     *
     * @return true if the action is an idle action, false otherwise
     */
    @Override
    public boolean idles() {
        return true;
    }
}
