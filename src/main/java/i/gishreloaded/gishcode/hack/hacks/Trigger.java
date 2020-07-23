package i.gishreloaded.gishcode.hack.hacks;

import java.util.Random;

import i.gishreloaded.gishcode.hack.Hack;
import i.gishreloaded.gishcode.hack.HackCategory;
import i.gishreloaded.gishcode.managers.EnemyManager;
import i.gishreloaded.gishcode.managers.FriendManager;
import i.gishreloaded.gishcode.managers.HackManager;
import i.gishreloaded.gishcode.utils.Utils;
import i.gishreloaded.gishcode.utils.ValidUtils;

import i.gishreloaded.gishcode.utils.RobotUtils;
import i.gishreloaded.gishcode.utils.TimerUtils;
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
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.ClientTickEvent;
import net.minecraft.network.play.client.CPacketUseEntity;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.util.EnumHand;

public class Trigger extends Hack{

    public BooleanValue autoDelay;
    public BooleanValue advanced;
    public ModeValue mode;
    
    public NumberValue minCPS;
    public NumberValue maxCPS;
    
    public EntityLivingBase target;
    
    public TimerUtils timer;
    
	public Trigger() {
		super("Trigger", HackCategory.COMBAT);
		
		autoDelay = new BooleanValue("AutoDelay", true);
		advanced = new BooleanValue("Advanced", false);

		this.mode = new ModeValue("Mode", new Mode("Click", true), new Mode("Attack", false));
		
		minCPS = new NumberValue("MinCPS", 4D, 1D, 20D);
		maxCPS = new NumberValue("MaxCPS", 8D, 1D, 20D);
		
		this.addValue(mode, advanced, autoDelay, minCPS, maxCPS);
		
		timer = new TimerUtils();
	}
	
	@Override
	public String getDescription() {
		return "Automatically attacks the entity you're looking at.";
	}
	
	@Override
	public void onDisable() {
		this.target = null;
		AutoShield.block(false);
		super.onDisable();
	}
	
	@Override
	public void onClientTick(ClientTickEvent event) {
		updateTarget();
		attackTarget(target);
		super.onClientTick(event);
	}
	
	void attackTarget(EntityLivingBase target) {
        if (check(target)) {
        	if(this.autoDelay.getValue()) {
        		if (Wrapper.INSTANCE.player().getCooledAttackStrength(0) == 1)
        			processAttack(target, false);
        	}
        	else
        	{
        		int currentCPS = Utils.random(
        				(int)(minCPS.getValue().intValue()),
        				(int)(maxCPS.getValue().intValue()));
        		if(timer.isDelay(1000 / currentCPS)) {
        			processAttack(target, true);
            		timer.setLastMS();
            	}
        	}
        	return;
        }
        AutoShield.block(false);
    }
	
	public void processAttack(EntityLivingBase entity, boolean packet) {
		AutoShield.block(false);
		float sharpLevel = EnchantmentHelper.getModifierForCreature(
				Wrapper.INSTANCE.player().getHeldItemMainhand(),
				target.getCreatureAttribute());
		if(mode.getMode("Click").isToggled()){
			RobotUtils.clickMouse(0);
		} else {
			if(packet)
				Wrapper.INSTANCE.sendPacket(new CPacketUseEntity(target));
			else
				Utils.attack(target);
			Utils.swingMainHand();
			if (sharpLevel > 0.0f) {
				Wrapper.INSTANCE.player().onEnchantmentCritical(target);
		 	}
		}
		AutoShield.block(true);
	}
	
	void updateTarget() {
		RayTraceResult object = Wrapper.INSTANCE.mc().objectMouseOver;
		if(object == null) {
			return;
		}
		EntityLivingBase entity = null;
		if(this.target != entity) {
			this.target = null;
		}
		if(object.typeOfHit == RayTraceResult.Type.ENTITY) {
			if(object.entityHit instanceof EntityLivingBase) {
				entity = (EntityLivingBase) object.entityHit;
				this.target = entity;
			}
		} else if(object.typeOfHit != RayTraceResult.Type.ENTITY && advanced.getValue()) {
			entity = getClosestEntity();
		}
		if (entity != null) {
            this.target = entity;
        }
	}
	
	EntityLivingBase getClosestEntity(){
		EntityLivingBase closestEntity = null;
 		for (Object o : Utils.getEntityList()) {
 			if(o instanceof EntityLivingBase && !(o instanceof EntityArmorStand)) {
 				EntityLivingBase entity = (EntityLivingBase)o;
 				if(check(entity)) {
 					if(closestEntity == null || Wrapper.INSTANCE.player().getDistance(entity)
 							< Wrapper.INSTANCE.player().getDistance(closestEntity)) {
 						closestEntity = entity;
 					}
 				}
 			}
 		}
 		return closestEntity;
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
		if(advanced.getValue()) {
			if(!ValidUtils.isInAttackFOV(entity, 50)) { return false; }
			if(!ValidUtils.isInAttackRange(entity, 4.7F)) { return false; }
		}
		if(!ValidUtils.isTeam(entity)) { return false; }
    	if(!ValidUtils.pingCheck(entity)) { return false; }
		if(!Wrapper.INSTANCE.player().canEntityBeSeen(entity)){ return false; }
		return true;
    }
}
