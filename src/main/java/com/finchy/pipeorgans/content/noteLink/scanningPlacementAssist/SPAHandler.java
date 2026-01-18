package com.finchy.pipeorgans.content.noteLink.scanningPlacementAssist;

import com.finchy.pipeorgans.content.noteLink.NoteLinkBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class SPAHandler {
    @SubscribeEvent
    public static void onBlockPlace(BlockEvent.EntityPlaceEvent event) {
        if (!(event.getLevel() instanceof ServerLevel level)) return;
        if (!(event.getEntity() instanceof Player player)) return;
        BlockPos pos = event.getPos();

        if (!(level.getBlockEntity(pos) instanceof NoteLinkBlockEntity)) return;

    }
}
