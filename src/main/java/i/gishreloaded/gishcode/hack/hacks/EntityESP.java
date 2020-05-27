package i.gishreloaded.gishcode.hack.hacks;

import i.gishreloaded.gishcode.hack.Hack;
import i.gishreloaded.gishcode.hack.HackCategory;
import i.gishreloaded.gishcode.managers.EnemyManager;
import i.gishreloaded.gishcode.managers.FriendManager;
import i.gishreloaded.gishcode.managers.HackManager;

import i.gishreloaded.gishcode.utils.Utils;
import i.gishreloaded.gishcode.utils.ValidUtils;
import i.gishreloaded.gishcode.utils.visual.RenderUtils;
import i.gishreloaded.gishcode.value.BooleanValue;
import i.gishreloaded.gishcode.wrappers.Wrapper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;

public class EntityESP extends Hack{
	
	public EntityESP() {
		super("EntityESP", HackCategory.VISUAL);
	}
	
	@Override
    public String getDescription() {
        return "Allows you to see all of the entities around you.";
    }
	
	@Override
	public void onRenderWorldLast(RenderWorldLastEvent event) {
		for (Object object : Utils.getEntityList()) {
			if(object instanceof EntityLivingBase && !(object instanceof EntityArmorStand)) {
				EntityLivingBase entity = (EntityLivingBase)object;
				this.render(entity, event.getPartialTicks());
			}
		}
		super.onRenderWorldLast(event);
	}
	
	void render(EntityLivingBase entity, float ticks) {
    	if(ValidUtils.isValidEntity(entity) || entity == Wrapper.INSTANCE.player()) { 
			return;
    	}
    	if(entity instanceof EntityPlayer) {
    		EntityPlayer player = (EntityPlayer)entity;
    		String ID = Utils.getPlayerName(player);
    		if(EnemyManager.enemysList.contains(ID)) {
    			RenderUtils.drawESP(entity, 0.8f, 0.3f, 0.0f, 1.0f, ticks);
    			return;
    		}
    		if(FriendManager.friendsList.contains(ID)) {
    			RenderUtils.drawESP(entity, 0.0f, 0.7f, 1.0f, 1.0f, ticks);
    			return;
    		}
    	}
    	if(HackManager.getHack("Targets").isToggledValue("Murder")) {
    		if(Utils.isMurder(entity)) {
    			RenderUtils.drawESP(entity, 1.0f, 0.0f, 0.8f, 1.0f, ticks);
    			return;
    		}
    		if(Utils.isDetect(entity)) {
    			RenderUtils.drawESP(entity, 0.0f, 0.0f, 1.0f, 1.0f, ticks);
    			return;
    		}
		}
    	if(entity.isInvisible()) {
			RenderUtils.drawESP(entity, 0.0f, 0.0f, 0.0f, 1.0f, ticks);
			return;
    	}
    	if(entity.hurtTime > 0) {
    		RenderUtils.drawESP(entity, 1.0f, 0.0f, 0.0f, 1.0f, ticks);
    		return;
    	}
    	RenderUtils.drawESP(entity, 1.0f, 1.0f, 1.0f, 1.0f, ticks);
    }
}
