package com.finchy.pipeorgans.content.noteLink.scanningPlacementAssist.scanners.base;

import com.finchy.pipeorgans.content.noteLink.NoteLinkBlock;
import com.finchy.pipeorgans.infrastructure.MusicalBlock;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.BlockState;

import java.util.List;
import java.util.Optional;

public abstract class BaseNeighboringAttachedToScanner extends BaseScanner {
    public BaseNeighboringAttachedToScanner(String scannerName, String... descriptionLines) {
        super(scannerName, descriptionLines);
    }

    @Override
    protected List<MusicalBlock> scanImpl(ClientLevel level, BlockPos origin, BlockState originState) {
        Direction attached = originState.getValue(NoteLinkBlock.FACING).getOpposite();
        BlockPos attachedTo = origin.relative(attached);
        List<MusicalBlock> foundMusicalBlocks = new java.util.ArrayList<>();
        for (Direction dir : getDirectionsToScanFromAttachedToBlock(level, attachedTo, level.getBlockState(attachedTo))) {
            if (dir == attached) continue;
            BlockPos scanPos = attachedTo.relative(dir);
            Optional<MusicalBlock> mb = MusicalBlock.getMusicalBlockAt(level, scanPos);
            if (mb.isPresent()) {
                if (mb.get().isAttachedIn(level, scanPos, dir.getOpposite())) {
                    foundMusicalBlocks.add(mb.get());
                }
            }
        }
        return foundMusicalBlocks;
    }

    protected abstract List<Direction> getDirectionsToScanFromAttachedToBlock(ClientLevel level, BlockPos pos, BlockState state);
}
