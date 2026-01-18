package com.finchy.pipeorgans.content.noteLink.scanningPlacementAssist.scanners;

import com.finchy.pipeorgans.content.noteLink.scanningPlacementAssist.scanners.base.BaseBothAttachedScanner;
import com.simibubi.create.AllBlocks;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.BlockState;

import java.util.List;

public class BoilerBothAttachedScanner extends BaseBothAttachedScanner {
    public BoilerBothAttachedScanner() {
        super("boiler_attached", "Scans blocks attached to a boiler that the Note Link is attached to.", "In its current implementation, this only checks if the block is a Fluid Tank, as I have yet to find out how to tell if it is actually a boiler.");
    }

    @Override
    protected List<Direction> getDirectionsToScanFromAttachedToBlock(ClientLevel level, BlockPos pos, BlockState state) {
        if (!state.is(AllBlocks.FLUID_TANK.get())) {
            return List.of();
        }
        return List.of(Direction.UP, Direction.NORTH, Direction.SOUTH, Direction.EAST, Direction.WEST);
    }
}
