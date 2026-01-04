package com.finchy.pipeorgans.ponder.util.actionBuilder.actions;

import com.finchy.pipeorgans.ponder.util.PonderContext;
import com.finchy.pipeorgans.ponder.util.actionBuilder.ActionBuilder;
import com.finchy.pipeorgans.ponder.util.actionBuilder.timing.keeper.TimeKeeper;
import net.createmod.catnip.math.Pointing;
import net.createmod.ponder.api.element.InputElementBuilder;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.function.Consumer;

public class ShowControlAction extends PonderAction {
    public static final Object ACTION_KEY = new Object();

    protected Vec3 scenePosition;
    protected final Pointing pointing;
    protected final Consumer<InputElementBuilder> configurator;

    public ShowControlAction(@NotNull PonderContext context, @NotNull TimeKeeper timeKeeper, Vec3 scenePosition, Pointing pointing, Consumer<InputElementBuilder> configurator) {
        super(context, timeKeeper);
        this.scenePosition = scenePosition;
        this.pointing = pointing;
        this.configurator = configurator;
    }

    /**
     * Returns a unique key representing the specific instance of the action.
     * The {@link ActionBuilder} uses this to associate operations to actions with {@link Objects#equals(Object, Object)}.
     *
     * @return the action instance key
     */
    @Override
    public @NotNull Object actionInstanceKey() {
        return ACTION_KEY;
    }

    /**
     * Executes the action within the given Ponder context.
     * Note that an action should usually only use a single instruction und should always use the provided duration parameter to determine how long it should last.
     *
     * @param duration the duration for which the action's instruction should be executed, in ticks
     */
    @Override
    public void execute(int duration) {
        configurator.accept(context.sceneBuilder().overlay().showControls(scenePosition, pointing, duration));
    }
}
