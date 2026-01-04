package com.finchy.pipeorgans.ponder.util.smartControl;

import net.createmod.ponder.api.scene.WorldInstructions;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;

import java.util.function.UnaryOperator;

public record BlockStateEffect(UnaryOperator<BlockState> modifier, boolean particles) implements ControlEffect {
    @Override
    public void apply(WorldInstructions world, BlockPos pos) {
        world.modifyBlock(pos, modifier, particles);
    }

    public static <T extends Comparable<T>> BlockStateEffect set(Property<T> property, T value, boolean particles) {
        return new BlockStateEffect(bs -> bs.setValue(property, value), particles);
    }

    public static <T extends Comparable<T>> BlockStateEffect setFromPrevious(Property<T> property, UnaryOperator<T> changer, boolean particles) {
        return new BlockStateEffect(bs -> bs.setValue(property, changer.apply(bs.getValue(property))), particles);
    }

    public static <T extends Comparable<T>> BlockStateEffect cycle(Property<T> property, boolean particles) {
        return new BlockStateEffect(bs -> bs.cycle(property), particles);
    }
}
