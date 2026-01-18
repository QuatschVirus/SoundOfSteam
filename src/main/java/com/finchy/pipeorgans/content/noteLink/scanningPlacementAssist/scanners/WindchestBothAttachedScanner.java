package com.finchy.pipeorgans.content.noteLink.scanningPlacementAssist.scanners;

import com.finchy.pipeorgans.content.noteLink.scanningPlacementAssist.scanners.base.BaseBothAttachedScanner;
import com.finchy.pipeorgans.init.AllBlocks;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.BlockState;

import java.util.List;

public class WindchestBothAttachedScanner extends BaseBothAttachedScanner {
    public WindchestBothAttachedScanner() {
        super("windchest_attached", "Scans blocks attached to a windchest that the Note Link is attached to.");
    }

    @Override
    protected List<Direction> getDirectionsToScanFromAttachedToBlock(ClientLevel level, BlockPos pos, BlockState state) {
        if (!state.is(AllBlocks.WINDCHEST.get())) {
            return List.of();
        }
        return List.of(Direction.UP, Direction.NORTH, Direction.SOUTH, Direction.EAST, Direction.WEST);
    }
}
