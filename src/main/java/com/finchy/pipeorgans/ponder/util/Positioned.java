package com.finchy.pipeorgans.ponder.util;

import net.minecraft.core.BlockPos;

public record Positioned<T>(BlockPos pos, T value) {
}
