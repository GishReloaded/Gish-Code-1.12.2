package i.gishreloaded.gishcode.utils;

import java.util.List;

import org.codehaus.plexus.util.reflection.Reflector;

import com.google.common.base.Predicates;

import i.gishreloaded.gishcode.wrappers.Wrapper;
import net.minecraft.entity.Entity;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;

public class RayCastUtils {
	
	public static Entity rayCast(double range, float yaw, float pitch) {
        double d0 = range;
        double d1 = d0;
        Vec3d vec3 = Wrapper.INSTANCE.player().getPositionEyes(1.0f);
        boolean flag = false;
        boolean flag1 = true;

        if (d0 > 3.0D)
        {
            flag = true;
        }

        //if (Wrapper.INSTANCE.mc().objectMouseOver != null)
        //{
        //    d1 = Wrapper.INSTANCE.mc().objectMouseOver.hitVec.distanceTo(vec3);
        //}

        Vec3d vec31 = getVectorForRotation(pitch, yaw);
        Vec3d vec32 = vec3.addVector(vec31.x * d0, vec31.y * d0, vec31.z * d0);

        Entity pointedEntity = null;

        Vec3d vec33 = null;
        float f = 1.0F;
        List list = Wrapper.INSTANCE.world().getEntitiesInAABBexcluding(Wrapper.INSTANCE.mc().getRenderViewEntity(), Wrapper.INSTANCE.mc().getRenderViewEntity().getEntityBoundingBox().offset(vec31.x * d0, vec31.y * d0, vec31.z * d0).expand((double)f, (double)f, (double)f), Predicates.and(EntitySelectors.NOT_SPECTATING, Entity::canBeCollidedWith));
        double d2 = d1;

        for (int i = 0; i < list.size(); ++i)
        {
            Entity entity1 = (Entity)list.get(i);
            float f1 = entity1.getCollisionBorderSize();
            AxisAlignedBB axisalignedbb = entity1.getEntityBoundingBox().expand((double)f1, (double)f1, (double)f1);
            RayTraceResult movingobjectposition = axisalignedbb.calculateIntercept(vec3, vec32);

            if (axisalignedbb.contains(vec3))
            {
                if (d2 >= 0.0D)
                {
                    pointedEntity = entity1;
                    vec33 = movingobjectposition == null ? vec3 : movingobjectposition.hitVec;
                    d2 = 0.0D;
                }
            }
            else if (movingobjectposition != null)
            {
                double d3 = vec3.distanceTo(movingobjectposition.hitVec);

                if (d3 < d2 || d2 == 0.0D)
                {
                    boolean flag2 = false;

                    /*if (Reflector.ForgeEntity_canRiderInteract.exists())
                    {
                        flag2 = Reflector.callBoolean(entity1, Reflector.ForgeEntity_canRiderInteract, new Object[0]);
                    }
                     */
                    if (entity1 == Wrapper.INSTANCE.mc().getRenderViewEntity().getRidingEntity() && !flag2)
                    {
                        if (d2 == 0.0D)
                        {
                            pointedEntity = entity1;
                            vec33 = movingobjectposition.hitVec;
                        }
                    }
                    else
                    {
                        pointedEntity = entity1;
                        vec33 = movingobjectposition.hitVec;
                        d2 = d3;
                    }
                }
            }
        }

        return pointedEntity;
    }

    public static Vec3d getVectorForRotation(float pitch, float yaw)
    {
        float f = MathHelper.cos(-yaw * 0.017453292F - (float)Math.PI);
        float f1 = MathHelper.sin(-yaw * 0.017453292F - (float)Math.PI);
        float f2 = -MathHelper.cos(-pitch * 0.017453292F);
        float f3 = MathHelper.sin(-pitch * 0.017453292F);
        return new Vec3d((double)(f1 * f2), (double)f3, (double)(f * f2));
    }
}
