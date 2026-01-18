package com.finchy.pipeorgans.infrastructure;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import java.util.Optional;

/**
 * An interface for blocks that can produce musical notes.
 *
 * Currently, this interface is used to get the pitch a Note Link should use when using the Scanning Placement Assist feature.
 */
public interface MusicalBlock {
    enum NoteKind {
        UNSET(false),
        INVALID(false),
        MIDI_RANGE(false),
        BEYOND_MIDI_RANGE(false),
        COMPOSITE(true)
        ;

        private final boolean usableForNoteLink;

        NoteKind(boolean usableForNoteLink) {
            this.usableForNoteLink = usableForNoteLink;
        }

        public boolean isUsableForNoteLink() {
            return usableForNoteLink;
        }
    }

    /**
     * Gets the MIDI note number of the note that would currently be produced by this block.
     * Midi note numbers range from 0 to 127, where 0 is C-1 and 127 is G9 (Middle C (C4) is 60).
     * @return the MIDI pitch value
     */
    int getMidiNote(Level level, BlockPos pos);

    /**
     * Gets the kind of note this block produces.
     * @param level The level where the block is located
     * @param pos The position of the block
     * @return the NoteKind of this block
     */
    NoteKind getNoteKind(Level level, BlockPos pos);

    /**
     * Gets an ItemStack representing the instrument of this musical block.
     * This representation is entirely arbitrary and up to the implementation. It should however make sense and be obtainable in survival mode.
     * @param level the level where the block is located
     * @param pos the position of the block
     * @return an ItemStack of the instrument
     */
    ItemStack getInstrumentItem(Level level, BlockPos pos);

    /**
     * Checks if this musical block is attached in the given direction.
     * Used for Scanning Placement Assist to determine attachment requirements.
     * @param level The level where the block is located
     * @param pos The position of the block
     * @param direction The direction to check attachment
     * @return true if attached in the given direction, false otherwise
     */
    boolean isAttachedIn(Level level, BlockPos pos, Direction direction);

    static Optional<MusicalBlock> getMusicalBlockAt(Level level, BlockPos pos) {
        if (level.getBlockState(pos).getBlock() instanceof MusicalBlock mb) {
            return Optional.of(mb);
        }
        return Optional.empty();
    }
}
