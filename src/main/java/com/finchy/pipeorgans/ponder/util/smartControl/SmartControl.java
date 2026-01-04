package com.finchy.pipeorgans.ponder.util.smartControl;

import com.finchy.pipeorgans.ponder.util.actionBuilder.timing.override.TimingOverride;
import com.finchy.pipeorgans.util.MathUtils;
import net.createmod.catnip.math.Pointing;
import net.createmod.ponder.api.element.InputElementBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.Vec3;

import java.util.function.Consumer;

public class SmartControl {
    protected final Vec3 visualCenter;
    protected final float controlPointingMargin;
    protected final Consumer<InputElementBuilder> configurator;
    protected final ControlEffect effect;
    protected final TimingOverride effectBufferOverride;
    protected final TimingOverride postEffectBufferOverride;

    public SmartControl(Vec3 visualCenter, float controlPointingMargin, Consumer<InputElementBuilder> configurator, ControlEffect effect, TimingOverride effectBufferOverride, TimingOverride postEffectBufferOverride) {
        this.visualCenter = visualCenter;
        this.controlPointingMargin = controlPointingMargin;
        this.configurator = configurator;
        this.effect = effect;
        this.effectBufferOverride = effectBufferOverride;
        this.postEffectBufferOverride = postEffectBufferOverride;
    }
    public SmartControl(Vec3 visualCenter, float controlPointingMargin, Consumer<InputElementBuilder> configurator, ControlEffect effect) {
        this(visualCenter, controlPointingMargin, configurator, effect, TimingOverride.NONE, TimingOverride.NONE);
    }

    public Consumer<InputElementBuilder> configurator() {
        return configurator;
    }

    public ControlEffect effect() {
        return effect;
    }

    public int applyEffectBufferOverride(int original) {
        return TimingOverride.getHighest(effectBufferOverride, original);
    }

    public int applyPostEffectBufferOverride(int original) {
        return TimingOverride.getHighest(postEffectBufferOverride, original);
    }

    public Vec3 centerScenePosition(BlockPos pos) {
        return new Vec3(pos.getX(), pos.getY(), pos.getZ()).add(visualCenter);
    }

    public Vec3 controlPointingScenePosition(BlockPos pos, Pointing pointing) {
        return MathUtils.computeDirectedOffset(visualCenter, pointing, controlPointingMargin).add(pos.getX(), pos.getY(), pos.getZ());
    }
}
