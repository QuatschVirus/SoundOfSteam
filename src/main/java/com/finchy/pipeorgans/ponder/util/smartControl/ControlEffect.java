package com.finchy.pipeorgans.ponder.util.smartControl;

import net.createmod.ponder.api.scene.WorldInstructions;
import net.minecraft.core.BlockPos;

public interface ControlEffect {
    void apply(WorldInstructions world, BlockPos pos);
}
