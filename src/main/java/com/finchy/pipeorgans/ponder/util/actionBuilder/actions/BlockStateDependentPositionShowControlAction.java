package com.finchy.pipeorgans.ponder.util.actionBuilder.actions;

import com.finchy.pipeorgans.ponder.util.PonderContext;
import com.finchy.pipeorgans.ponder.util.actionBuilder.timing.keeper.TimeKeeper;
import net.createmod.catnip.math.Pointing;
import net.createmod.ponder.api.element.InputElementBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;
import java.util.function.Function;

public class BlockStateDependentPositionShowControlAction extends ShowControlAction {
    protected BlockPos blockPos;
    protected Function<BlockState, Vec3> getScenePosition;

    public BlockStateDependentPositionShowControlAction(@NotNull PonderContext context, @NotNull TimeKeeper timeKeeper, BlockPos pos, Function<BlockState, Vec3> getScenePosition, Pointing pointing, Consumer<InputElementBuilder> configurator) {
        super(context, timeKeeper, null, pointing, configurator);
        this.blockPos = pos;
        this.getScenePosition = getScenePosition;
    }

    /**
     * Executes the action within the given Ponder context.
     * Note that an action should usually only use a single instruction und should always use the provided duration parameter to determine how long it should last.
     *
     * @param duration the duration for which the action's instruction should be executed, in ticks
     */
    @Override
    public void execute(int duration) {
        BlockState bs = context.sceneBuilder().getScene().getWorld().getBlockState(blockPos);
        scenePosition = getScenePosition.apply(bs);
        super.execute(duration);
    }
}
