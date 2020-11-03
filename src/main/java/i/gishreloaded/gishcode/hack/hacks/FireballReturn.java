package i.gishreloaded.gishcode.hack.hacks;

import i.gishreloaded.gishcode.hack.Hack;
import i.gishreloaded.gishcode.hack.HackCategory;
import i.gishreloaded.gishcode.utils.RobotUtils;
import i.gishreloaded.gishcode.utils.TimerUtils;
import i.gishreloaded.gishcode.utils.Utils;
import i.gishreloaded.gishcode.value.types.DoubleValue;
import i.gishreloaded.gishcode.wrappers.Wrapper;
import net.minecraft.entity.projectile.EntityFireball;
import net.minecraftforge.fml.common.gameevent.TickEvent.ClientTickEvent;

public class FireballReturn extends Hack{
	
    public DoubleValue yaw;
    public DoubleValue pitch;
    public DoubleValue range;
    
	public EntityFireball target;
	public TimerUtils timer;
	
	public FireballReturn() {
		super("FireballReturn", HackCategory.COMBAT);
		yaw = new DoubleValue("Yaw", 25.0D, 0D, 50D);
		pitch = new DoubleValue("Pitch", 25.0D, 0D, 50D);
		range = new DoubleValue("Range", 10.0D, 0.1D, 10D);
		
		this.addValue(yaw, pitch, range);
		
		this.timer = new TimerUtils();
	}
	
	@Override
	public String getDescription() {
		return "Beats fireballs when they fly at you.";
	}

	@Override
	public void onClientTick(ClientTickEvent event) {
		updateTarget();
		attackTarget();
		super.onClientTick(event);
	}

	void updateTarget(){
		for (Object object : Utils.getEntityList()) {
			if(object instanceof EntityFireball) {
				EntityFireball entity = (EntityFireball) object;
				if(isInAttackRange(entity) && !entity.isDead && !entity.onGround && entity.canBeAttackedWithItem()) {
					target = entity;
				}
			}
		}
	}
	
	void attackTarget() {
		if(target == null) {
			return;
		}
		Utils.assistFaceEntity(this.target, this.yaw.getValue().floatValue(), this.pitch.getValue().floatValue());
		int currentCPS = Utils.random(4, 7);
		if(timer.isDelay(1000 / currentCPS)) {
			RobotUtils.clickMouse(0);
			timer.setLastMS();
			target = null;
		}
	}
	
	public boolean isInAttackRange(EntityFireball entity) {
        return entity.getDistance(Wrapper.INSTANCE.player()) <= this.range.getValue().floatValue();
    }
	
}
