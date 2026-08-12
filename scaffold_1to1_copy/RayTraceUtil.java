/*
 * Decompiled with CFR 0.152.
 */
package modification.utilities;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import java.util.List;
import modification.extenders.Rotation;
import modification.interfaces.MCHook;
import net.minecraft.entity.Entity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import optifine.Reflector;
import optifine.ReflectorMethod;

public final class RayTraceUtil
implements MCHook {
    public final MovingObjectPosition rayTraceBlock(float f, float f2) {
        double d = RayTraceUtil.MC.playerController.getBlockReachDistance();
        Vec3 vec3 = RayTraceUtil.MC.thePlayer.getPositionEyes(1.0f);
        Vec3 vec32 = RayTraceUtil.MC.thePlayer.getVectorForRotation(f2, f);
        Vec3 vec33 = vec3.addVector(vec32.xCoord * d, vec32.yCoord * d, vec32.zCoord * d);
        return RayTraceUtil.MC.thePlayer.worldObj.rayTraceBlocks(vec3, vec33, false, false, true);
    }

    public final Entity rayTracedEntity(float f, Rotation rotation) {
        double d;
        double d2 = d = (double)f;
        Vec3 vec3 = RayTraceUtil.MC.thePlayer.getPositionEyes(1.0f);
        boolean bl = false;
        boolean bl2 = true;
        if (d > 3.0) {
            bl = true;
        }
        Vec3 vec32 = RayTraceUtil.MC.thePlayer.getVectorForRotation(rotation.pitch, rotation.yaw);
        Vec3 vec33 = vec3.addVector(vec32.xCoord * d, vec32.yCoord * d, vec32.zCoord * d);
        Entity entity = null;
        Vec3 vec34 = null;
        float f2 = 1.0f;
        List list = RayTraceUtil.MC.theWorld.getEntitiesInAABBexcluding(MC.getRenderViewEntity(), MC.getRenderViewEntity().getEntityBoundingBox().addCoord(vec32.xCoord * d, vec32.yCoord * d, vec32.zCoord * d).expand((double)f2, (double)f2, (double)f2), Predicates.and((Predicate)EntitySelectors.NOT_SPECTATING, Entity::canBeCollidedWith));
        double d3 = d2;
        for (int i = 0; i < list.size(); ++i) {
            double d4;
            Entity entity2 = (Entity)list.get(i);
            float f3 = entity2.getCollisionBorderSize();
            AxisAlignedBB axisAlignedBB = entity2.getEntityBoundingBox().expand((double)f3, (double)f3, (double)f3);
            MovingObjectPosition movingObjectPosition = axisAlignedBB.calculateIntercept(vec3, vec33);
            if (axisAlignedBB.isVecInside(vec3)) {
                if (!(d3 >= 0.0)) continue;
                entity = entity2;
                vec34 = movingObjectPosition == null ? vec3 : movingObjectPosition.hitVec;
                d3 = 0.0;
                continue;
            }
            if (movingObjectPosition == null || !((d4 = vec3.distanceTo(movingObjectPosition.hitVec)) < d3) && d3 != 0.0) continue;
            boolean bl3 = false;
            if (Reflector.ForgeEntity_canRiderInteract.exists()) {
                bl3 = Reflector.callBoolean((Object)entity2, (ReflectorMethod)Reflector.ForgeEntity_canRiderInteract, (Object[])new Object[0]);
            }
            if (entity2 == RayTraceUtil.MC.getRenderViewEntity().ridingEntity && !bl3) {
                if (d3 != 0.0) continue;
                entity = entity2;
                vec34 = movingObjectPosition.hitVec;
                continue;
            }
            entity = entity2;
            vec34 = movingObjectPosition.hitVec;
            d3 = d4;
        }
        return entity;
    }

    public static Entity rayTracedEntity(Entity entity) {
        Entity entity2 = entity;
        for (Entity entity3 : RayTraceUtil.MC.theWorld.loadedEntityList) {
            if (entity3 == entity || RayTraceUtil.MC.thePlayer == entity3 || !entity3.isInvisible() || !(entity3.getDistanceToEntity(entity) <= 1.0f)) continue;
            entity2 = entity3;
        }
        return entity2;
    }
}
