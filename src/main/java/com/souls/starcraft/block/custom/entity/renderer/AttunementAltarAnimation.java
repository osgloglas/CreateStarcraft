package com.souls.starcraft.block.custom.entity.renderer;

public class AttunementAltarAnimation {
    public record RotationKeyframe(float time, float x, float y, float z) {}

    public record PositionKeyframe(float time, float x, float y, float z) {}

    private static final RotationKeyframe[] ORB_ROTATION = {
        new RotationKeyframe(0.0F, 0, 0, 0),
        new RotationKeyframe(1.0F, 0, 180, 0),
        new RotationKeyframe(2.0F, 0, 360, 0),
        new RotationKeyframe(3.0F, 0, 540, 0),
        new RotationKeyframe(4.0F, 0, 720, 0),
        new RotationKeyframe(5.0F, 0, 900, 0),
        new RotationKeyframe(6.0F, 0, 1080, 0),
        new RotationKeyframe(6.5F, 0, 1260, 0),
        new RotationKeyframe(7.0F, 0, 1440, 0),
        new RotationKeyframe(7.5F, 0, 1620, 0),
        new RotationKeyframe(8.0F, 0, 1800, 0),
        new RotationKeyframe(8.5F, 0, 1980, 0),
        new RotationKeyframe(9.0F, 0, 2160, 0),
        new RotationKeyframe(9.5F, 0, 2340, 0),
        new RotationKeyframe(10.0F, 0, 2520, 0),
        new RotationKeyframe(10.5F, 0, 2700, 0),
        new RotationKeyframe(11.0F, 0, 2880, 0),
        new RotationKeyframe(11.5F, 0, 3060, 0),
        new RotationKeyframe(12.0F, 0, 3240, 0),
        new RotationKeyframe(13.0F, 0, 3420, 0),
        new RotationKeyframe(14.0F, 0, 3600, 0),
        new RotationKeyframe(15.0F, 0, 3780, 0),
        new RotationKeyframe(16.0F, 0, 3960, 0),
        new RotationKeyframe(17.0F, 0, 4140, 0),
        new RotationKeyframe(18.0F, 0, 4320, 0)
    };

    private static final PositionKeyframe[] STRAIGHT_POSITION = {
        new PositionKeyframe(9.0F, 0, 0, 0),
        new PositionKeyframe(10.5F, 0, 5, 0),
        new PositionKeyframe(14.0F, 0, 5, 0),
        new PositionKeyframe(17.0F, 0, 0, 0)
    };

    private static final PositionKeyframe[] DIAGONAL_POSITION = {
        new PositionKeyframe(3.0F, 0, 0, 0),
        new PositionKeyframe(6.0F, 0, 5, 0),
        new PositionKeyframe(9.0F, 0, 10, 0),
        new PositionKeyframe(14.0F, 0, 10, 0),
        new PositionKeyframe(16.0F, 0, 5, 0),
        new PositionKeyframe(18.0F, 0, 0, 0)
    };

    public static float[] sampleRotation(RotationKeyframe[] keys, float time) {
        if (time <= keys[0].time()) {
            return rotation(keys[0]);
        }

        for (int i = 0; i < keys.length - 1; i++) {
            RotationKeyframe a = keys[i];
            RotationKeyframe b = keys[i + 1];

            if (time <= b.time()) {
                float t = (time - a.time()) / (b.time() - a.time());

                return new float[]{
                    lerp(a.x(), b.x(), t),
                    lerp(a.y(), b.y(), t),
                    lerp(a.z(), b.z(), t)
                };
            }
        }

        return rotation(keys[keys.length - 1]);
    }

    public static float[] samplePosition(PositionKeyframe[] keys, float time) {
        if (time <= keys[0].time()) {
            return position(keys[0]);
        }

        for (int i = 0; i < keys.length - 1; i++) {
            PositionKeyframe a = keys[i];
            PositionKeyframe b = keys[i + 1];

            if (time <= b.time()) {
                float t = (time - a.time()) / (b.time() - a.time());

                return new float[]{
                    lerp(a.x(), b.x(), t),
                    lerp(a.y(), b.y(), t),
                    lerp(a.z(), b.z(), t)
                };
            }
        }

        return position(keys[keys.length - 1]);
    }

    private static float lerp(float a, float b, float t) {
        return a + (b - a) * t;
    }

    private static float[] rotation(RotationKeyframe key) {
        return new float[]{key.x(), key.y(), key.z()};
    }

    private static float[] position(PositionKeyframe key) {
        return new float[]{key.x(), key.y(), key.z()};
    }

    public static float[] getOrbRotation(float time) {
        return sampleRotation(ORB_ROTATION, time);
    }

    public static float[] getStraightPosition(float time) {
        return samplePosition(STRAIGHT_POSITION, time);
    }

    public static float[] getDiagonalPosition(float time) {
        return samplePosition(DIAGONAL_POSITION, time);
    }
}
