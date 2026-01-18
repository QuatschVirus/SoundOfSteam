package com.finchy.pipeorgans.content.noteLink.scanningPlacementAssist.scanners.base;

import com.finchy.pipeorgans.infrastructure.MusicalBlock;
import com.finchy.pipeorgans.init.AllBlocks;
import com.finchy.pipeorgans.util.holders.IntHolder;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.ForgeConfigSpec;

import java.util.List;
import java.util.Optional;

public abstract class BaseScanner {
    protected String scannerName;
    protected String[] descriptionLines;

    private int defaultPriority = 0;

    protected ForgeConfigSpec.BooleanValue enabledConfig;
    protected ForgeConfigSpec.IntValue priorityConfig;

    public BaseScanner(String scannerName, String... descriptionLines) {
        this.scannerName = scannerName;
        this.descriptionLines = descriptionLines;
    }


    public String getScannerName() {
        return scannerName;
    }

    @OnlyIn(Dist.CLIENT)
    protected abstract List<MusicalBlock> scanImpl(ClientLevel level, BlockPos origin, BlockState originState);

    protected void buildConfigHook(ForgeConfigSpec.Builder builder) {
        // Optional hook for subclasses to add config options
    }

    public void buildConfig(ForgeConfigSpec.Builder builder) {
        builder.push(scannerName);

        enabledConfig = builder
                .comment("Enable or disable the " + scannerName + " scanner for scanning placement assist.")
                .comment(descriptionLines)
                .define("enabled", true);

        priorityConfig = builder
                .comment("Priority of the " + scannerName + " scanner. Lower values mean higher priority.")
                .defineInRange("priority", defaultPriority, Integer.MIN_VALUE, Integer.MAX_VALUE);

        buildConfigHook(builder);
        builder.pop();
    }

    public void setChainedDefaultPriority(IntHolder priority) {
        this.defaultPriority = priority.get();
        priority.increment();
    }

    public boolean isEnabled() {
        if (enabledConfig == null) {
            return false;
        }
        return enabledConfig.get();
    }

    public int getPriority() {
        if (priorityConfig == null) {
            return Integer.MAX_VALUE;
        }
        return priorityConfig.get();
    }

    public List<MusicalBlock> scan(Level level, BlockPos origin) {
        if (!(level instanceof ClientLevel clientLevel)) {
            throw new IllegalArgumentException("BaseScanner.scan can only be called on the client side.");
        }
        if (!isEnabled()) {
            return List.of();
        }
        BlockState bs = clientLevel.getBlockState(origin);
        if (!bs.is(AllBlocks.NOTE_LINK.get())) {
            return List.of();
        }
        return scanImpl(clientLevel, origin, bs);
    }
}
