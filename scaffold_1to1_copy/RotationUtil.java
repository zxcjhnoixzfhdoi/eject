/*
 * Decompiled with CFR 0.152.
 */
package modification.utilities;

import com.google.common.collect.Lists;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import modification.events.EventMoveFlying;
import modification.extenders.Rotation;
import modification.interfaces.MCHook;
import net.minecraft.entity.Entity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;

public final class RotationUtil
implements MCHook {
    public static final List<Float> YAW_SPEEDS = Lists.newArrayList();
    public static Rotation currentRotation;
    public static Rotation lastRotation;
    public static boolean moveToRotation;
    public static boolean jumpFix;
    private float field_76336_a;
    private float field_76334_b;
    private float field_76335_c;

    public static Rotation fixedRotations(double d, double d2) {
        float f = RotationUtil.MC.gameSettings.mouseSensitivity * 0.6f + 0.2f;
        float f2 = f * f * f * 8.0f;
        float f3 = (float)(d - (double)RotationUtil.lastRotation.yaw);
        float f4 = (float)(d2 - (double)RotationUtil.lastRotation.pitch);
        f3 = (float)((double)f3 - (double)f3 % ((double)f2 * 0.15));
        f4 = (float)((double)f4 - (double)f4 % ((double)f2 * 0.15));
        return new Rotation(RotationUtil.lastRotation.yaw + f3, MathHelper.clamp_float((float)(RotationUtil.lastRotation.pitch + f4), (float)-90.0f, (float)90.0f));
    }

    public static float[] calculateSilentStrafe(EventMoveFlying eventMoveFlying) {
        int n = (int)((MathHelper.wrapAngleTo180_float((float)(RotationUtil.MC.thePlayer.rotationYaw - RotationUtil.currentRotation.yaw)) + 180.0f) / 45.0f);
        float f = eventMoveFlying.strafe;
        float f2 = eventMoveFlying.forward;
        float f3 = 0.0f;
        float f4 = 0.0f;
        switch (n) {
            case 0: {
                f3 = f2;
                f4 = f;
                break;
            }
            case 1: {
                f3 += f2;
                f4 -= f2;
                f3 += f;
                f4 += f;
                break;
            }
            case 2: {
                f3 = f;
                f4 = -f2;
                break;
            }
            case 3: {
                f3 -= f2;
                f4 -= f2;
                f3 += f;
                f4 -= f;
                break;
            }
            case 4: {
                f3 = -f2;
                f4 = -f;
                break;
            }
            case 5: {
                f3 -= f2;
                f4 += f2;
                f3 -= f;
                f4 -= f;
                break;
            }
            case 6: {
                f3 = -f;
                f4 = f2;
                break;
            }
            case 7: {
                f3 += f2;
                f4 += f2;
                f3 -= f;
                f4 += f;
            }
        }
        if (f3 > 1.0f || f3 < 0.9f && f3 > 0.3f || f3 < -1.0f || f3 > -0.9f && f3 < -0.3f) {
            f3 *= 0.5f;
        }
        if (f4 > 1.0f || f4 < 0.9f && f4 > 0.3f || f4 < -1.0f || f4 > -0.9f && f4 < -0.3f) {
            f4 *= 0.5f;
        }
        return new float[]{f4, f3};
    }

    public static void updateRotations(float f, float f2) {
        RotationUtil.MC.thePlayer.rotationYawHead = f;
        RotationUtil.MC.thePlayer.rotationPitchHead = f2;
        while (RotationUtil.MC.thePlayer.rotationYawHead - RotationUtil.MC.thePlayer.prevRotationYawHead < -180.0f) {
            RotationUtil.MC.thePlayer.prevRotationYawHead -= 360.0f;
        }
        while (RotationUtil.MC.thePlayer.rotationYawHead - RotationUtil.MC.thePlayer.prevRotationYawHead >= 180.0f) {
            RotationUtil.MC.thePlayer.prevRotationYawHead += 360.0f;
        }
    }

    public final float updateRotation(float f, float f2, float f3) {
        float f4 = MathHelper.wrapAngleTo180_float((float)(f2 - f));
        if (f4 > f3) {
            f4 = f3;
        }
        if (f4 < -f3) {
            f4 = -f3;
        }
        return f + f4;
    }

    public final float calcRot(float f, float f2, float f3, float f4) {
        float f5 = MathHelper.wrapAngleTo180_float((float)(f2 - f));
        f4 = Math.abs(f5) > 90.0f ? (f4 += f3) : (f4 > 20.0f ? (f4 -= f3) : (f4 += f3));
        return MathHelper.clamp_float((float)f4, (float)0.0f, (float)180.0f);
    }

    public final float[] rotationsToEntity(Entity entity) {
        double d = entity.posX + (entity.posX - entity.prevPosX) * 2.0 - RotationUtil.MC.thePlayer.posX - RotationUtil.MC.thePlayer.motionX * 2.0;
        double d2 = entity.posY + (double)entity.getEyeHeight() - RotationUtil.MC.thePlayer.posY - (double)RotationUtil.MC.thePlayer.getEyeHeight();
        double d3 = entity.posZ + (entity.posZ - entity.prevPosZ) * 2.0 - RotationUtil.MC.thePlayer.posZ - RotationUtil.MC.thePlayer.motionZ * 2.0;
        return new float[]{MathHelper.wrapAngleTo180_float((float)((float)Math.toDegrees(Math.atan2(d3, d)) - 90.0f)), (float)(-Math.toDegrees(Math.atan2(d2, Math.hypot(d, d3))))};
    }

    public final float[] rotationsToVector(Vec3 vec3) {
        Vec3 vec32 = RotationUtil.MC.thePlayer.getPositionEyes(1.0f);
        Vec3 vec33 = vec3.subtract(vec32);
        return new float[]{(float)Math.toDegrees(Math.atan2(vec33.zCoord, vec33.xCoord)) - 90.0f, (float)(-Math.toDegrees(Math.atan2(vec33.yCoord, Math.hypot(vec33.xCoord, vec33.zCoord))))};
    }

    public final float[] rotationsToEntityWithBow(Entity entity) {
        double d = Math.sqrt(RotationUtil.MC.thePlayer.getDistanceToEntity(entity) * RotationUtil.MC.thePlayer.getDistanceToEntity(entity)) / 1.5;
        double d2 = entity.posX + (entity.posX - entity.prevPosX) * d - RotationUtil.MC.thePlayer.posX;
        double d3 = entity.posZ + (entity.posZ - entity.prevPosZ) * d - RotationUtil.MC.thePlayer.posZ;
        double d4 = entity.posY + (entity.posY - entity.prevPosY) + (double)(RotationUtil.MC.thePlayer.getDistanceToEntity(entity) * RotationUtil.MC.thePlayer.getDistanceToEntity(entity) / 300.0f) + (double)entity.getEyeHeight() - RotationUtil.MC.thePlayer.posY - (double)RotationUtil.MC.thePlayer.getEyeHeight() - RotationUtil.MC.thePlayer.motionY;
        return new float[]{(float)Math.toDegrees(Math.atan2(d3, d2)) - 90.0f, (float)(-Math.toDegrees(Math.atan2(d4, Math.hypot(d2, d3))))};
    }

    public static void resetRotations() {
        currentRotation = null;
        moveToRotation = false;
    }

    public final float calculateRotation(float f, float f2, float f3, float f4, float f5) {
        float f6 = MathHelper.wrapAngleTo180_float((float)(f2 - f));
        if (f6 < -f4 || f6 > f5) {
            return f + MathHelper.clamp_float((float)f6, (float)(-f3), (float)f3);
        }
        return f;
    }

    public final void collect(float f) {
        if (f < 5.0f) {
            return;
        }
        if (YAW_SPEEDS.size() > 50) {
            YAW_SPEEDS.remove(Collections.min(YAW_SPEEDS));
            return;
        }
        YAW_SPEEDS.add(Float.valueOf(f));
    }

    public final float readSpeed() {
        if (YAW_SPEEDS.isEmpty()) {
            return 20.0f;
        }
        return YAW_SPEEDS.get(ThreadLocalRandom.current().nextInt(0, YAW_SPEEDS.size() - 1)).floatValue();
    }

    public final float[] rotationsToPos(BlockPos blockPos) {
        double d = (double)blockPos.getX() + 0.4 - RotationUtil.MC.thePlayer.posX;
        double d2 = (double)blockPos.getY() + 0.5 - RotationUtil.MC.thePlayer.posY - (double)RotationUtil.MC.thePlayer.getEyeHeight();
        double d3 = (double)blockPos.getZ() + 0.4 - RotationUtil.MC.thePlayer.posZ;
        return new float[]{(float)Math.toDegrees(Math.atan2(d3, d)) - 90.0f, (float)(-Math.toDegrees(Math.atan2(d2, Math.hypot(d, d3))))};
    }

    static {
        lastRotation = new Rotation(0.0f, 0.0f);
    }
}
