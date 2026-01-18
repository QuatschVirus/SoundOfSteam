package com.finchy.pipeorgans.content.noteLink.scanningPlacementAssist.scanners;

import com.finchy.pipeorgans.content.noteLink.scanningPlacementAssist.scanners.base.BaseNeighboringAttachedToScanner;
import com.finchy.pipeorgans.init.AllBlocks;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.BlockState;

import java.util.List;

public class NeighboringAttachedWindchestScanner extends BaseNeighboringAttachedToScanner {

    public NeighboringAttachedWindchestScanner() {
        super("windchest_neighboring", "Scans blocks neighboring a windchest that the Note Link is attached to.", "This ignores the MusicalBlock's attachment state.");
    }

    @Override
    protected List<Direction> getDirectionsToScanFromAttachedToBlock(ClientLevel level, BlockPos pos, BlockState state) {
        if (!state.is(AllBlocks.WINDCHEST.get())) {
            return List.of();
        }

        return List.of(Direction.values());
    }
}
