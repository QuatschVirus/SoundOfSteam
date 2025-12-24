package com.finchy.pipeorgans.content.plaques;

import com.finchy.pipeorgans.PipeOrgans;
import com.finchy.pipeorgans.util.PipePitch;
import net.minecraft.data.PackOutput;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

public class PlaqueModelProvider extends ItemModelProvider {

    public PlaqueModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, PipeOrgans.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        for (PipePitch pitch : PipePitch.allPipePitches()) {
            PipeOrgans.LOGGER.debug("Generating plaque item model for pitch: {}", pitch.getNormalizedName());
            this.withExistingParent(PlaqueItems.itemNameForPitch(pitch),"item/generated")
                    .texture("layer0", pitch.octave().getTextureLocation())
                    .texture("layer1", pitch.pitchClass().getTextureLocation());
        }
    }
}
