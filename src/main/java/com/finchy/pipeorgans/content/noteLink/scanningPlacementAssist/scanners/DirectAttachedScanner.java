package com.finchy.pipeorgans.content.noteLink.scanningPlacementAssist.scanners;

import com.finchy.pipeorgans.content.noteLink.NoteLinkBlock;
import com.finchy.pipeorgans.content.noteLink.scanningPlacementAssist.scanners.base.BaseScanner;
import com.finchy.pipeorgans.infrastructure.MusicalBlock;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.BlockState;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

public class DirectAttachedScanner extends BaseScanner {
    protected Predicate<BlockState> targetPredicate;

    public DirectAttachedScanner(Predicate<BlockState> targetPredicate) {
        super("attached", "Scans blocks the Note Link is directly attached to that match the target predicate.", "This ignores the MusicalBlock's attachment state.");
        this.targetPredicate = targetPredicate;
    }

    @Override
    protected List<MusicalBlock> scanImpl(ClientLevel level, BlockPos origin, BlockState originState) {
        Direction attached = originState.getValue(NoteLinkBlock.FACING).getOpposite();
        BlockPos attachedTo = origin.relative(attached);
        if (targetPredicate.test(level.getBlockState(attachedTo))) {
            Optional<MusicalBlock> mb = MusicalBlock.getMusicalBlockAt(level, attachedTo);
            if (mb.isPresent()) {
                return List.of(mb.get());
            }
        }
        return List.of();
    }
}
