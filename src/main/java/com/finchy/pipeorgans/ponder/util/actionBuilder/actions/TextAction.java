package com.finchy.pipeorgans.ponder.util.actionBuilder.actions;

import com.finchy.pipeorgans.ponder.util.PonderContext;
import com.finchy.pipeorgans.ponder.util.actionBuilder.ActionBuilder;
import com.finchy.pipeorgans.ponder.util.actionBuilder.timing.keeper.TimeKeeper;
import com.finchy.pipeorgans.ponder.util.smartText.SmartText;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public class TextAction extends PonderAction {
    protected SmartText text;
    protected String tag = "";

    public TextAction(@NotNull PonderContext context, @NotNull TimeKeeper timeKeeper, @NotNull SmartText text, @Nullable String tag) {
        super(context, timeKeeper);
        this.text = text;
        if (tag != null) {
            this.tag = tag;
        }
    }

    /**
     * Returns a unique key representing the specific instance of the action.
     * The {@link ActionBuilder} uses this to associate operations to actions with {@link Objects#equals(Object, Object)}.
     *
     * @return the action instance key
     */
    @Override
    public @NotNull Object actionInstanceKey() {
        return tag;
    }

    /**
     * Executes the action within the given Ponder context.
     * Note that an action should usually only use a single instruction und should always use the provided duration parameter to determine how long it should last.
     *
     * @param duration the duration for which the action's instruction should be executed, in ticks
     */
    @Override
    public void execute(int duration) {
        text.building().accept(context.sceneBuilder().overlay().showText(duration).text(text.text()));
    }

    @Override
    public int getMinimumPostBuffer(PonderAction nextNonIdle) {
        return context.textDisplay().getMinBuffer(text);
    }
}
