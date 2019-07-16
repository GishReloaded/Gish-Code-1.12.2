package i.gishreloaded.gishcode.hack.hacks;

import java.util.Random;

import i.gishreloaded.gishcode.Wrapper;
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
		
		autoDelay = new BooleanValue("AutoDelay", false);
		advanced = new BooleanValue("Advanced", true);

		this.mode = new ModeValue("Mode", new Mode("Click", true), new Mode("Attack", false));
		
		minCPS = new NumberValue("MinCPS", 4D, 1D, 20D);
		maxCPS = new NumberValue("MaxCPS", 8D, 1D, 20D);
		
		this.addValue(mode, advanced, autoDelay, minCPS, maxCPS);
		
		timer = new TimerUtils();
	}
	
	@Override
	public void onDisable() {
		this.target = null;
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
        	float sharpLevel = EnchantmentHelper.getModifierForCreature(Wrapper.INSTANCE.player().getHeldItemMainhand(), target.getCreatureAttribute());
        	if(this.autoDelay.getValue()) {
        		if (Wrapper.INSTANCE.player().getCooledAttackStrength(0) == 1) {
        			if(mode.getMode("Click").isToggled()){
        				RobotUtils.clickMouse(0);
        			} else {
        				Wrapper.INSTANCE.mc().playerController.attackEntity(Wrapper.INSTANCE.player(), target);
        				Wrapper.INSTANCE.player().swingArm(EnumHand.MAIN_HAND);
						if (sharpLevel > 0.0f) {
							Wrapper.INSTANCE.player().onEnchantmentCritical(target);
       			 		}
        			}
        		}
        	}
        	else
        	{
        		int currentCPS = Utils.random(minCPS.getValue().intValue(), maxCPS.getValue().intValue());
        		if(timer.isDelay(1000 / currentCPS)) {
        			if(mode.getMode("Click").isToggled()){
        				RobotUtils.clickMouse(0);
        			} else {
        				Wrapper.INSTANCE.sendPacket(new CPacketUseEntity(target));
        				Wrapper.INSTANCE.player().swingArm(EnumHand.MAIN_HAND);
						if (sharpLevel > 0.0f) {
							Wrapper.INSTANCE.player().onEnchantmentCritical(target);
       			 		}
        			}
            		timer.setLastMS();
            	}
        	}
        }
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
 		for (Object o : Wrapper.INSTANCE.world().loadedEntityList) {
 			if(o instanceof EntityLivingBase && !(o instanceof EntityArmorStand)) {
 				EntityLivingBase entity = (EntityLivingBase)o;
 				if(check(entity)) {
 					if(closestEntity == null || Wrapper.INSTANCE.player().getDistance(entity) < Wrapper.INSTANCE.player().getDistance(closestEntity)) {
 						closestEntity = entity;
 					}
 				}
 			}
 		}
 		return closestEntity;
 	}
    
    public boolean isInAttackFOV(EntityLivingBase entity) {
        return Utils.getDistanceFromMouse(entity) <= 51.0F;
    }
    
    public boolean isInAttackRange(EntityLivingBase entity) {
        return entity.getDistance(Wrapper.INSTANCE.player()) <= 4.7F;
    }
	
	public boolean check(EntityLivingBase entity) {
		if(entity instanceof EntityArmorStand) {
			return false;
		}
		if(ValidUtils.isValidEntity(entity)){
			return false;
		}
		if(!ValidUtils.isNoScreen()) {
			return false;
		}
		if(entity == Wrapper.INSTANCE.player()) {
			return false;
		}
		if(entity.isDead) {
			return false;
		}
		if(ValidUtils.isBot(entity)) {
			return false;
		}
		if(!ValidUtils.isFriendEnemy(entity)) {
			return false;
		}
    	if(!ValidUtils.isInvisible(entity)) {
			return false;
		}
		if(advanced.getValue()) {
			if(!isInAttackFOV(entity)) {
				return false;
			}
			if(!isInAttackRange(entity)) {
				return false;
			}
		}
		if(!ValidUtils.isTeam(entity)) {
			return false;
		}
    	if(!ValidUtils.pingCheck(entity)) {
    		return false;
    	}
		if(!Wrapper.INSTANCE.player().canEntityBeSeen(entity)){
			return false;
		}
		
		return true;
    }
}
