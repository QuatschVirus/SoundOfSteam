package com.finchy.pipeorgans.ponder.util.smartExternalSlot;

import com.finchy.pipeorgans.ponder.util.actionBuilder.timing.override.TimingOverride;
import com.finchy.pipeorgans.ponder.util.smartControl.ControlEffect;
import com.finchy.pipeorgans.ponder.util.smartControl.SmartControl;
import net.createmod.ponder.api.element.InputElementBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

import java.util.function.Consumer;

public record SmartExternalSlotControl(
        SmartExternalSlot slot,
        float pointingMargin,
        Consumer<InputElementBuilder> configurator,
        ControlEffect effect,
        TimingOverride highlightBufferOverride,
        TimingOverride effectBufferOverride,
        TimingOverride postEffectBufferOverride
) {
    public SmartControl getSmartControl(BlockState state) {
        Vec3 center = slot.slotPositionProvider().apply(state);
        return new SmartControl(
                center,
                pointingMargin,
                configurator,
                effect,
                effectBufferOverride,
                postEffectBufferOverride
        );
    }

    public int applyHighlightBufferOverride(int original) {
        return TimingOverride.getHighest(highlightBufferOverride, original);
    }

    public int applyEffectBufferOverride(int original) {
        return TimingOverride.getHighest(effectBufferOverride, original);
    }

    public int applyPostEffectBufferOverride(int original) {
        return TimingOverride.getHighest(postEffectBufferOverride, original);
    }
}
