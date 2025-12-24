package com.finchy.pipeorgans.content.plaques;

import com.finchy.pipeorgans.PipeOrgans;
import com.finchy.pipeorgans.data.AssetLookup;
import com.finchy.pipeorgans.util.PipePitch;
import com.simibubi.create.foundation.data.CreateRegistrate;
import com.tterrag.registrate.util.entry.ItemEntry;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.data.event.GatherDataEvent;

import java.util.ArrayList;
import java.util.List;

public class PlaqueItems {
    public static String itemNameForPitch(PipePitch pitch) {
        return "plaque_" + pitch.getNormalizedName();
    }

    public static ResourceLocation itemResourceForPitch(PipePitch pitch) {
        return PipeOrgans.asResource(itemNameForPitch(pitch));
    }

    public static List<ItemEntry<Item>> register(CreateRegistrate registrate) {
        List<ItemEntry<Item>> entries = new ArrayList<>(PipePitch.totalPitchCount());
        for (PipePitch pitch : PipePitch.allPipePitches()) {
            entries.add(
                registrate.item(itemNameForPitch(pitch), Item::new)
                    .model(AssetLookup.existingItemModel())
                    .register()
            );
        }
        return entries;
    }

    public static void gatherPlaqueData(GatherDataEvent event) {
        DataGenerator gen = event.getGenerator();
        gen.addProvider(
                event.includeClient(),
                (DataProvider.Factory<PlaqueModelProvider>) (packOutput) -> new PlaqueModelProvider(packOutput, event.getExistingFileHelper())
        );
    }
}
