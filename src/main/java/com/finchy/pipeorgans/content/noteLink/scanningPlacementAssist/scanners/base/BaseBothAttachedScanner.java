package com.finchy.pipeorgans.content.noteLink.scanningPlacementAssist.scanners.base;

import com.finchy.pipeorgans.content.noteLink.NoteLinkBlock;
import com.finchy.pipeorgans.infrastructure.MusicalBlock;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.BlockState;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

public abstract class BaseBothAttachedScanner extends BaseScanner {
    public BaseBothAttachedScanner(String scannerName, String... descriptionLines) {
        super(scannerName, descriptionLines);
    }

    protected abstract List<Direction> getDirectionsToScanFromAttachedToBlock(ClientLevel level, BlockPos pos, BlockState state);

    @Override
    protected List<MusicalBlock> scanImpl(ClientLevel level, BlockPos origin, BlockState originState) {
        List<MusicalBlock> foundMusicalBlocks = new java.util.ArrayList<>();
        Direction attached = originState.getValue(NoteLinkBlock.FACING).getOpposite();
        BlockPos attachedTo = origin.relative(attached);
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
}
