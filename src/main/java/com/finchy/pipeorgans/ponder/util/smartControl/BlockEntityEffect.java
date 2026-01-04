package com.finchy.pipeorgans.ponder.util.smartControl;

import net.createmod.ponder.api.scene.WorldInstructions;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;

import java.util.function.Consumer;

public record BlockEntityEffect<T extends BlockEntity>(Consumer<T> modifier, Class<T> blockEntityClass) implements ControlEffect {

    @Override
    public void apply(WorldInstructions world, BlockPos pos) {
        world.modifyBlockEntity(pos, blockEntityClass, modifier);
    }
}
