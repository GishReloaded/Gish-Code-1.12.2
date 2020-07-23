package i.gishreloaded.gishcode.hack.hacks;

import i.gishreloaded.gishcode.hack.Hack;
import i.gishreloaded.gishcode.hack.HackCategory;
import i.gishreloaded.gishcode.managers.EnemyManager;
import i.gishreloaded.gishcode.managers.FriendManager;
import i.gishreloaded.gishcode.managers.HackManager;

import i.gishreloaded.gishcode.utils.Utils;
import i.gishreloaded.gishcode.utils.ValidUtils;
import i.gishreloaded.gishcode.value.BooleanValue;
import i.gishreloaded.gishcode.value.Mode;
import i.gishreloaded.gishcode.value.ModeValue;
import i.gishreloaded.gishcode.value.NumberValue;
import i.gishreloaded.gishcode.wrappers.Wrapper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBow;
import net.minecraft.util.math.RayTraceResult;
import net.minecraftforge.fml.common.gameevent.TickEvent.ClientTickEvent;

public class AimBot extends Hack{

	public ModeValue priority;
    public BooleanValue walls;
    
    public NumberValue yaw;
    public NumberValue pitch;
    public NumberValue range;
    public NumberValue FOV;
    
    public EntityLivingBase target;
    
	public AimBot() {
		super("AimBot", HackCategory.COMBAT);
		this.priority = new ModeValue("Priority", new Mode("Closest", true), new Mode("Health", false));

		walls = new BooleanValue("ThroughWalls", false);
		
		yaw = new NumberValue("Yaw", 15.0D, 0D, 50D);
		pitch = new NumberValue("Pitch", 15.0D, 0D, 50D);
		range = new NumberValue("Range", 4.7D, 0.1D, 10D);
		FOV = new NumberValue("FOV", 90D, 1D, 180D);
		
		this.addValue(priority, walls, yaw, pitch, range, FOV);
	}
	
	@Override
	public String getDescription() {
		return "Automatically points towards player.";
	}
	
	@Override
	public void onDisable() {
		this.target = null;
		super.onDisable();
	}
	
	@Override
	public void onClientTick(ClientTickEvent event) {
		updateTarget();
		Utils.assistFaceEntity(
				this.target, 
				this.yaw.getValue().floatValue(),
				this.pitch.getValue().floatValue());
		this.target = null;
		super.onClientTick(event);
	}

	void updateTarget(){
		for (Object object : Utils.getEntityList()) {
			if(!(object instanceof EntityLivingBase)) continue;
			EntityLivingBase entity = (EntityLivingBase) object;
			if(!check(entity)) continue;
			this.target = entity;
		}
	}
	
	public boolean check(EntityLivingBase entity) {
		if(entity instanceof EntityArmorStand) { return false; }
		if(ValidUtils.isValidEntity(entity)){ return false; }
		if(!ValidUtils.isNoScreen()) { return false; }
		if(entity == Wrapper.INSTANCE.player()) { return false; }
		if(entity.isDead) { return false; }
		if(ValidUtils.isBot(entity)) { return false; }
		if(!ValidUtils.isFriendEnemy(entity)) { return false; }
    	if(!ValidUtils.isInvisible(entity)) { return false; }
    	if(!ValidUtils.isInAttackFOV(entity, FOV.getValue().intValue())) { return false; }
		if(!ValidUtils.isInAttackRange(entity, range.getValue().floatValue())) { return false; }
		if(!ValidUtils.isTeam(entity)) { return false; }
    	if(!ValidUtils.pingCheck(entity)) { return false; }
    	if(!isPriority(entity)) { return false; }
		if(!this.walls.getValue()) { if(!Wrapper.INSTANCE.player().canEntityBeSeen(entity)) { return false; } }
		return true;
    }

	boolean isPriority(EntityLivingBase entity) {
		return priority.getMode("Closest").isToggled() && ValidUtils.isClosest(entity, target) 
				|| priority.getMode("Health").isToggled() && ValidUtils.isLowHealth(entity, target);
	}

}
