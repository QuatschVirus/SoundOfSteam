package com.finchy.pipeorgans.content.noteLink.scanningPlacementAssist.scanners;

import com.finchy.pipeorgans.content.noteLink.scanningPlacementAssist.scanners.base.BaseScanner;
import com.finchy.pipeorgans.infrastructure.MusicalBlock;
import net.createmod.catnip.data.Iterate;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

import java.util.List;

public class NeighboringScanner extends BaseScanner {
    public NeighboringScanner() {
        super("neighbors", "Scans all directly neighboring blocks around the Note Link.", "This ignores the MusicalBlock's attachment state.");
    }

    @Override
    protected List<MusicalBlock> scanImpl(ClientLevel level, BlockPos origin, BlockState originState) {
        List<MusicalBlock> foundMusicalBlocks = new java.util.ArrayList<>();
        for (var dir : Iterate.directions) {
            BlockPos neighborPos = origin.relative(dir);
            MusicalBlock.getMusicalBlockAt(level, neighborPos).ifPresent(foundMusicalBlocks::add);
        }
        return foundMusicalBlocks;
    }
}
