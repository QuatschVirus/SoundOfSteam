package com.finchy.pipeorgans.content.noteLink.scanningPlacementAssist;

import com.finchy.pipeorgans.infrastructure.MusicalBlock;
import net.minecraft.core.BlockPos;

public record ScanResult(BlockPos pos, MusicalBlock musicalBlock, int combinedNote) {
    public static final ScanResult EMPTY = new ScanResult(null, null, -1);

    public boolean isValid() {
        return !(this.pos == null || this.musicalBlock == null || this.combinedNote < 0 || this.combinedNote > 127);
    }
}
