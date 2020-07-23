package i.gishreloaded.gishcode.hack.hacks;

import i.gishreloaded.gishcode.hack.Hack;
import i.gishreloaded.gishcode.hack.HackCategory;
import i.gishreloaded.gishcode.utils.RobotUtils;
import i.gishreloaded.gishcode.utils.TimerUtils;
import i.gishreloaded.gishcode.utils.Utils;
import i.gishreloaded.gishcode.utils.ValidUtils;
import i.gishreloaded.gishcode.value.Mode;
import i.gishreloaded.gishcode.value.ModeValue;
import i.gishreloaded.gishcode.value.NumberValue;
import i.gishreloaded.gishcode.wrappers.Wrapper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraftforge.client.event.EntityViewRenderEvent.CameraSetup;
import net.minecraftforge.fml.common.gameevent.TickEvent.ClientTickEvent;
import i.gishreloaded.gishcode.utils.system.Connection.Side;

public class Hitbox extends Hack{
	
	public NumberValue width;
	public NumberValue height;
	
	public Hitbox() {
		super("Hitbox", HackCategory.COMBAT);
		
		width = new NumberValue("Width", 1.0D, 0.6D, 5.0D);
		height = new NumberValue("Height", 2.2D, 1.8D, 5.0D);
		
		this.addValue(width, height);
	}
	
	@Override
	public String getDescription() {
		return "Change size hit box of entity.";
	}
	
	@Override
	public void onClientTick(ClientTickEvent event) {
		for(Object object : Utils.getEntityList()) {
			if(!(object instanceof EntityLivingBase)) continue;
			EntityLivingBase entity = (EntityLivingBase)object;
			if(!check(entity)) continue;
			Utils.setEntityBoundingBoxSize(entity, 
					width.getValue().floatValue(), height.getValue().floatValue());
		}
		super.onClientTick(event);
	}
	
	@Override
	public void onDisable() {
		for(Object object : Utils.getEntityList()) {
			if(!(object instanceof EntityLivingBase)) continue;
			EntityLivingBase entity = (EntityLivingBase)object;
			Utils.setEntityBoundingBoxSize(entity, 
					0.6F, 1.8F);
		}
		super.onDisable();
	}
	
	public boolean check(EntityLivingBase entity) {
		if(entity instanceof EntityArmorStand) { return false; }
		if(ValidUtils.isValidEntity(entity)){ return false; }
		if(entity == Wrapper.INSTANCE.player()) { return false; }
		if(entity.isDead) { return false; }
		if(!ValidUtils.isFriendEnemy(entity)) { return false; }
		if(!ValidUtils.isTeam(entity)) { return false; }
		return true;
    }

}
