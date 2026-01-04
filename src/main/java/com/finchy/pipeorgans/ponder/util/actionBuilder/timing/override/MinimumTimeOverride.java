package com.finchy.pipeorgans.ponder.util.actionBuilder.timing.override;

public record MinimumTimeOverride(int minimum) implements TimingOverride {
    @Override
    public int getMinimum(int originalMinimum) {
        return minimum;
    }

    @Override
    public int getFixed(int originalFixed) {
        return originalFixed;
    }
}
