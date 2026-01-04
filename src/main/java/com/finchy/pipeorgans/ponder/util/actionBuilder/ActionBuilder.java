package com.finchy.pipeorgans.ponder.util.actionBuilder;

import com.finchy.pipeorgans.ponder.PonderTimings;
import com.finchy.pipeorgans.ponder.util.PonderContext;
import com.finchy.pipeorgans.ponder.util.Positioned;
import com.finchy.pipeorgans.ponder.util.actionBuilder.timing.override.TimingOverride;
import com.finchy.pipeorgans.ponder.util.smartControl.SmartControl;
import com.finchy.pipeorgans.ponder.util.actionBuilder.actions.*;
import com.finchy.pipeorgans.ponder.util.actionBuilder.timing.keeper.ConstantTimeKeeper;
import com.finchy.pipeorgans.ponder.util.actionBuilder.timing.keeper.MinimumUntilClosedTimeKeeper;
import com.finchy.pipeorgans.ponder.util.actionBuilder.timing.keeper.TimeKeeper;
import com.finchy.pipeorgans.ponder.util.smartExternalSlot.SmartExternalSlot;
import com.finchy.pipeorgans.ponder.util.smartExternalSlot.SmartExternalSlotControl;
import com.finchy.pipeorgans.ponder.util.smartText.SmartText;
import net.createmod.catnip.math.Pointing;
import net.createmod.ponder.api.PonderPalette;
import net.minecraft.core.BlockPos;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class ActionBuilder {

    protected PonderContext context;

    protected List<PonderAction> completedActions = new LinkedList<>();

    protected HashMap<Object, HashMap<Object, PonderAction>> actionMap = new HashMap<>();
    protected List<PonderAction> actionsInOrder = new LinkedList<>();

    protected List<PonderAction> listeningActions = new LinkedList<>();

    public ActionBuilder(PonderContext context) {
        this.context = context;
    }

    /**
     * A single-shot text action that displays the given text and waits for its duration and its buffer.
     * Creates completed actions and does not set a tag.
     * @param text The text to display
     * @return this ActionBuilder
     */
    public ActionBuilder text(SmartText text) {
        int duration = context.textDisplay().getFixedDuration(text);
        addAction(new TextAction(context, new ConstantTimeKeeper(duration), text, null), true);
        addAction(new IdleAction(context, new ConstantTimeKeeper(duration)), true);
        return this;
    }

    public ActionBuilder texts(SmartText... texts) {
        for (SmartText text : texts) {
            text(text);
        }
        return this;
    }

    public ActionBuilder textStart(SmartText text, String tag) {
        addAction(new TextAction(context, new MinimumUntilClosedTimeKeeper(context.textDisplay().getMinDuration(text)), text, tag), false);
        return this;
    }

    public ActionBuilder textEnd(String tag) {
        completeOpenAction(TextAction.class, tag);
        return this;
    }

    public ActionBuilder controlAndEffect(BlockPos pos, SmartControl control, Pointing pointing) {
        int effectBuffer = control.applyEffectBufferOverride(PonderTimings.CONTEXT_INFO_BUFFER);
        int postEffectBuffer = control.applyPostEffectBufferOverride(PonderTimings.CONTEXT_INFO_BUFFER);
        addAction(new ShowControlAction(context, new ConstantTimeKeeper(effectBuffer + postEffectBuffer), control.controlPointingScenePosition(pos, pointing), pointing, control.configurator()), true);
        idle(effectBuffer);
        addAction(new ControlEffectAction(context, pos, control.effect()), true);
        idle(postEffectBuffer);
        return this;
    }

    public ActionBuilder controlAndEffect(Positioned<SmartControl> positionedControl, Pointing pointing) {
        return controlAndEffect(positionedControl.pos(), positionedControl.value(), pointing);
    }

    public ActionBuilder slotControlAndEffect(BlockPos pos, SmartExternalSlotControl control, PonderPalette highlightColor, Pointing pointing) {
        int highlightBuffer = control.applyHighlightBufferOverride(PonderTimings.CONTEXT_INFO_BUFFER);
        int effectBuffer = control.applyEffectBufferOverride(PonderTimings.CONTEXT_INFO_BUFFER);
        int postEffectBuffer = control.applyPostEffectBufferOverride(PonderTimings.CONTEXT_INFO_BUFFER);

        addAction(new HighlightSlotAction(context, new ConstantTimeKeeper(highlightBuffer + effectBuffer + postEffectBuffer), pos, control.slot(), highlightColor, null), true);
        idle(highlightBuffer);
        addAction(new BlockStateDependentPositionShowControlAction(context, new ConstantTimeKeeper(effectBuffer + postEffectBuffer), pos, control.slot().slotPositionProvider(), pointing, control.configurator()), true);
        idle(effectBuffer);
        addAction(new ControlEffectAction(context, pos, control.effect()), true);
        idle(postEffectBuffer);
        return this;
    }

    public ActionBuilder slotText(BlockPos pos, SmartExternalSlot slot, SmartText text, PonderPalette highlightColor, TimingOverride highlightOverride) {
        int highlightBuffer = TimingOverride.getHighest(highlightOverride, PonderTimings.CONTEXT_INFO_BUFFER);
        int duration = context.textDisplay().getFixedDuration(text);

        addAction(new HighlightSlotAction(context, new ConstantTimeKeeper(highlightBuffer + duration), pos, slot, highlightColor, null), true);
        idle(highlightBuffer);
        addAction(new BlockStateDependentPositionTextAction(context, new ConstantTimeKeeper(duration), pos, slot.slotPositionProvider(), text, null), true);
        idle(duration);
        return this;
    }

    public ActionBuilder slotTextStart(BlockPos pos, SmartExternalSlot slot, SmartText text, PonderPalette highlightColor, String tag, TimingOverride highlightOverride) {
        int highlightBuffer = TimingOverride.getHighest(highlightOverride, PonderTimings.CONTEXT_INFO_BUFFER);
        int minDuration = context.textDisplay().getMinDuration(text);

        addAction(new HighlightSlotAction(context, new MinimumUntilClosedTimeKeeper(highlightBuffer + minDuration), pos, slot, highlightColor, tag), false);
        idle(highlightBuffer);
        addAction(new BlockStateDependentPositionTextAction(context, new MinimumUntilClosedTimeKeeper(minDuration), pos, slot.slotPositionProvider(), text, tag), false);
        return this;
    }

    public ActionBuilder slotTextEnd(String tag) {
        completeOpenAction(HighlightSlotAction.class, tag);
        completeOpenAction(BlockStateDependentPositionTextAction.class, tag);
        return this;
    }

    public ActionBuilder slotControlAndEffect(Positioned<SmartExternalSlotControl> positionedControl, PonderPalette highlightColor, Pointing pointing) {
        return slotControlAndEffect(positionedControl.pos(), positionedControl.value(), highlightColor, pointing);
    }

    public ActionBuilder explainedSlotControlAndEffect(BlockPos pos, SmartExternalSlotControl control, SmartText explanation, PonderPalette highlightColor, Pointing pointing) {
        int highlightBuffer = control.applyHighlightBufferOverride(PonderTimings.CONTEXT_INFO_BUFFER);
        int effectBuffer = control.applyEffectBufferOverride(PonderTimings.CONTEXT_INFO_BUFFER);
        int postEffectBuffer = control.applyPostEffectBufferOverride(PonderTimings.CONTEXT_INFO_BUFFER);
        int textDuration = context.textDisplay().getFixedDuration(explanation);
        int textBuffer = context.textDisplay().getFixedBuffer(explanation);

        addAction(new HighlightSlotAction(context, new ConstantTimeKeeper(highlightBuffer + effectBuffer + postEffectBuffer + textDuration + textBuffer), pos, control.slot(), highlightColor, null), true);
        idle(highlightBuffer);
        addAction(new BlockStateDependentPositionTextAction(context, new ConstantTimeKeeper(textDuration), pos, control.slot().slotPositionProvider(), explanation, null), true);
        idle(textDuration + textBuffer);
        addAction(new BlockStateDependentPositionShowControlAction(context, new ConstantTimeKeeper(effectBuffer + postEffectBuffer), pos, control.slot().slotPositionProvider(), pointing, control.configurator()), true);
        idle(effectBuffer);
        addAction(new ControlEffectAction(context, pos, control.effect()), true);
        idle(postEffectBuffer);

        return this;
    }

    public ActionBuilder explainedSlotControlAndEffect(Positioned<SmartExternalSlotControl> positionedControl, SmartText explanation, PonderPalette highlightColor, Pointing pointing) {
        return explainedSlotControlAndEffect(positionedControl.pos(), positionedControl.value(), explanation, highlightColor, pointing);
    }

    public ActionBuilder idle(int duration) {
        addAction(new IdleAction(context, new ConstantTimeKeeper(duration)), true);
        return this;
    }

    public ActionBuilder addAction(PonderAction action, boolean completed) {
        Object typeKey = action.actionTypeKey();
        Object instanceKey = action.actionInstanceKey();
        actionMap.putIfAbsent(typeKey, new HashMap<>());
        actionMap.get(typeKey).put(instanceKey, action);
        actionsInOrder.add(action);
        if (completed)
            action.complete();
        onAddAction(action);
        if (!action.getTimeKeeper().isCompleted()) {
            listeningActions.add(action);
        }
        return this;
    }

    public void completeOpenAction(Object typeKey, Object instanceKey) {
        PonderAction action = actionMap.getOrDefault(typeKey, new HashMap<>()).get(instanceKey);
        if (action == null) return;
        action.complete();
    }

    public void completeOpenAction(PonderAction action) {
        completeOpenAction(action.actionTypeKey(), action.actionInstanceKey());
    }

    protected void onAddAction(PonderAction action) {
        for (PonderAction listener : listeningActions) {
            listener.consumeAction(action);
            if (action.isCompleted()) {
                listeningActions.remove(listener);
                completeOpenAction(action);
            }
        }
    }

//    public List<PonderAction> finish() {
//        List<PonderAction> complete = new LinkedList<>();
//        PonderAction lastNonIdle = null;
//        List<Integer> remainingIdleNeeds = new LinkedList<>();
//        int currentOpenIdleTime = 0;
//        for (int i = 0; i < actionsInOrder.size(); i++) {
//            PonderAction action = actionsInOrder.get(i);
//            if (action.idles()) {
//                currentOpenIdleTime += action.getTimeKeeper().getTime();
//                complete.add(action);
//                continue;
//            }
//            action.complete();
//            if (lastNonIdle == null) {
//                lastNonIdle = action;
//                int idleNeeded = lastNonIdle.getMinimumPreBuffer(null) - currentOpenIdleTime;
//                if (idleNeeded > 0) {
//                    IdleAction idleAction = new IdleAction(context, new ConstantTimeKeeper(idleNeeded));
//                    complete.add(idleAction);
//                }
//            }
//            int totalIdleNeeded = action.getTimeKeeper().getTime() + action.
//        }
//
//        return complete;
//    }
}
