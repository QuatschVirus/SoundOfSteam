package com.finchy.pipeorgans.ponder.util.actionBuilder.actions;

import com.finchy.pipeorgans.ponder.util.PonderContext;
import com.finchy.pipeorgans.ponder.util.actionBuilder.ActionBuilder;
import com.finchy.pipeorgans.ponder.util.actionBuilder.timing.keeper.TimeKeeper;
import com.finchy.pipeorgans.ponder.util.smartControl.ControlEffect;
import net.minecraft.core.BlockPos;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class ControlEffectAction extends PonderAction {
    protected BlockPos pos;
    protected ControlEffect effect;

    public ControlEffectAction(@NotNull PonderContext context,  BlockPos pos, ControlEffect effect) {
        super(context, TimeKeeper.NONE);
        this.pos = pos;
        this.effect = effect;
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
        effect.apply(context.sceneBuilder().world(), pos);
    }

    /**
     * Indicates whether this action is an instant action.
     * Instant actions are executed immediately without any duration. They may still have buffers before and after them.
     *
     * @return true if the action is an instant action, false otherwise
     */
    @Override
    public boolean instantAction() {
        return true;
    }
}
