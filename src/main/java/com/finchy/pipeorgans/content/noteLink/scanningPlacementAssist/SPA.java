package com.finchy.pipeorgans.content.noteLink.scanningPlacementAssist;

import com.finchy.pipeorgans.content.noteLink.scanningPlacementAssist.scanners.*;
import com.finchy.pipeorgans.content.noteLink.scanningPlacementAssist.scanners.base.BaseScanner;
import com.finchy.pipeorgans.infrastructure.MusicalBlock;
import com.finchy.pipeorgans.util.holders.IntHolder;
import net.createmod.catnip.data.Pair;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.ForgeConfigSpec;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class SPA {

    protected static final IntHolder chainingDefaultPriority = new IntHolder(0);
    protected static List<BaseScanner> scanners = new ArrayList<>();

    public static final BaseScanner DIRECT_ATTACHED = register(new DirectAttachedScanner(
            state -> true // Target predicate: match any block for now
    ));

    public static final BaseScanner WINDCHEST_ATTACHED = register(new WindchestBothAttachedScanner());
    public static final BaseScanner BOILER_ATTACHED = register(new BoilerBothAttachedScanner());

    public static final BaseScanner DIRECT_NEIGHBORS = register(new NeighboringScanner());
    public static final BaseScanner WINDCHEST_NEIGHBORS = register(new NeighboringAttachedWindchestScanner());
    public static final BaseScanner BOILER_NEIGHBORS = register(new NeighboringAttachedBoilerScanner());

    protected static BaseScanner register(BaseScanner scanner) {
        scanner.setChainedDefaultPriority(chainingDefaultPriority);
        scanners.add(scanner);
        return scanner;
    }

    public static ScanResult scan(Level level, BlockPos originPos) {
        List<BaseScanner> byPriority = scanners.stream()
                .filter(BaseScanner::isEnabled)
                .sorted(Comparator.comparingInt(BaseScanner::getPriority))
                .toList();
        for (BaseScanner scanner : byPriority) {
            var found = scanner.scan(level, originPos);
            if (found.isEmpty()) continue;
            int finalNote = 128;
            for (MusicalBlock mb : found) {

            }
        }
    }

    public static void buildConfig(ForgeConfigSpec.Builder builder) {
        builder.push("scanningPlacementAssist");
        builder.push("scanners");
        for (BaseScanner scanner : scanners) {
            scanner.buildConfig(builder);
        }
        builder.pop(2);
    }
}
