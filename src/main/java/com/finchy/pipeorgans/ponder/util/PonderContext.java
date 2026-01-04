package com.finchy.pipeorgans.ponder.util;

import com.finchy.pipeorgans.ponder.util.smartText.SmartTextDisplay;
import com.simibubi.create.foundation.ponder.CreateSceneBuilder;
import net.createmod.ponder.api.scene.SceneBuildingUtil;

public record PonderContext(
        CreateSceneBuilder sceneBuilder,
        SceneBuildingUtil util,
        SmartTextDisplay textDisplay
) {
}
