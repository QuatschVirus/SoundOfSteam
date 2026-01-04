package com.finchy.pipeorgans.ponder.util.actionBuilder.actions;

import com.finchy.pipeorgans.ponder.util.PonderContext;
import com.finchy.pipeorgans.ponder.util.actionBuilder.ActionBuilder;
import com.finchy.pipeorgans.ponder.util.actionBuilder.timing.keeper.TimeKeeper;
import com.finchy.pipeorgans.ponder.util.smartExternalSlot.SmartExternalSlot;
import net.createmod.ponder.api.PonderPalette;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class HighlightSlotAction extends PonderAction {
    protected BlockPos pos;
    protected SmartExternalSlot slot;
    protected PonderPalette color;
    protected String tag = "";

    public HighlightSlotAction(@NotNull PonderContext context, @NotNull TimeKeeper timeKeeper, BlockPos pos, SmartExternalSlot slot, PonderPalette color, String tag) {
        super(context, timeKeeper);
        this.pos = pos;
        this.slot = slot;
        this.color = color;
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
        BlockState bs = context.sceneBuilder().getScene().getWorld().getBlockState(pos);
        context.sceneBuilder().overlay().chaseBoundingBoxOutline(color, slot, slot.getAABB(bs, pos), duration);
    }
}
