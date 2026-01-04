package com.finchy.pipeorgans.ponder.util.smartExternalSlot;

import com.finchy.pipeorgans.util.MathUtils;
import net.createmod.catnip.math.Pointing;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.function.Function;

public record SmartExternalSlot(
        float size,
        Function<BlockState, Vec3> slotPositionProvider,
        Function<BlockState, Direction> slotDirectionProvider
) {
    public AABB getAABB(BlockState state, BlockPos pos) {
        Vec3 slotPos = slotPositionProvider.apply(state);
        return MathUtils.inflateAsPerpendicularPlane(new AABB(slotPos, slotPos), slotDirectionProvider.apply(state).getAxis(), size / 2f).move(pos);
    }

    public Vec3 getPointingPosition(BlockState state, BlockPos pos, Pointing targetPointing, float margin) {
        Vec3 slotPos = slotPositionProvider.apply(state);
        Direction slotDir = slotDirectionProvider.apply(state);
        Direction stepDir = targetPointing.getCombinedDirection(slotDir).getOpposite();
        float step = size / 2f + margin;
        return MathUtils.computeDirectedOffset(slotPos, stepDir, step).add(pos.getX(), pos.getY(), pos.getZ());
    }
}
