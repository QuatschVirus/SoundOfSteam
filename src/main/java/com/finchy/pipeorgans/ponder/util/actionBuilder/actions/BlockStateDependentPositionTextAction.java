package com.finchy.pipeorgans.ponder.util.actionBuilder.actions;

import com.finchy.pipeorgans.ponder.util.PonderContext;
import com.finchy.pipeorgans.ponder.util.actionBuilder.timing.keeper.TimeKeeper;
import com.finchy.pipeorgans.ponder.util.smartText.SmartText;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Function;

public class BlockStateDependentPositionTextAction extends TextAction {
    public BlockStateDependentPositionTextAction(@NotNull PonderContext context, @NotNull TimeKeeper timeKeeper, BlockPos pos, Function<BlockState, Vec3> getScenePosition, @NotNull SmartText text, @Nullable String tag) {
        super(context, timeKeeper, new SmartText(text.text(),
                t -> {
                text.building().accept(t);
                BlockState bs = context.sceneBuilder().getScene().getWorld().getBlockState(pos);
                t.pointAt(getScenePosition.apply(bs));
        }, text.durationOverride(), text.bufferOverride()), tag);
    }
}
