package com.finchy.pipeorgans.util;

import net.createmod.catnip.math.Pointing;
import net.minecraft.core.Direction;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public abstract class MathUtils {

    public static float map(float value, float start1, float stop1, float start2, float stop2) {
        return start2 + (stop2 - start2) * ((value - start1) / (stop1 - start1));
        // thank you, processing!
    }

    public static AABB inflateAsPerpendicularPlane(AABB box, Direction.Axis axis, float amount) {
        switch (axis) {
            case X -> {
                return box.inflate(0, amount, amount);
            }
            case Y -> {
                return box.inflate(amount, 0, amount);
            }
            case Z -> {
                return box.inflate(amount, amount, 0);
            }
        }
        return box;
    }

    public static Vec3 computeDirectedOffset(Vec3 base, Direction direction, float distance) {
        return base.add(Vec3.atLowerCornerOf(direction.getNormal()).scale(distance));
    }

    public static Vec3 computeDirectedOffset(Vec3 base, Pointing pointingTarget, float distance) {
        return base.add(pointingNormal(pointingTarget).reverse().scale(distance));
    }

    public static Vec3 pointingNormal(Pointing pointing) {
        return switch (pointing) {
            case UP -> new Vec3(0, 1, 0);
            case DOWN -> new Vec3(0, -1, 0);
            case LEFT -> new Vec3(1, 0, 0);
            case RIGHT -> new Vec3(-1, 0, 0);
        };
    }
}
