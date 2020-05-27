package i.gishreloaded.gishcode.hack.hacks;

import org.lwjgl.opengl.GL11;

import i.gishreloaded.gishcode.hack.Hack;
import i.gishreloaded.gishcode.hack.HackCategory;
import i.gishreloaded.gishcode.managers.EnemyManager;
import i.gishreloaded.gishcode.managers.FriendManager;
import i.gishreloaded.gishcode.managers.HackManager;

import i.gishreloaded.gishcode.utils.Utils;
import i.gishreloaded.gishcode.utils.visual.RenderUtils;
import i.gishreloaded.gishcode.value.BooleanValue;
import i.gishreloaded.gishcode.wrappers.Wrapper;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;

public class Glowing extends Hack{
    
	public Glowing() {
		super("Glowing", HackCategory.VISUAL);
	}
	
	@Override
	public String getDescription() {
		return "Glows all entities around you.";
	}
	
	@Override
	public void onDisable() {
		for (Object object : Utils.getEntityList()) {
	    	  Entity entity = (Entity)object;
	    	  if(entity.isGlowing()) {
	    		  entity.setGlowing(false);
	    	  }
		}
		super.onDisable();
	}
	
	@Override
	public void onRenderWorldLast(RenderWorldLastEvent event) {
		for (Object object : Utils.getEntityList()) {
	    	  Entity entity = (Entity)object;
	    	  if(checkEntity(entity) != null && entity != Wrapper.INSTANCE.player()) { 
	    		  if(!entity.isGlowing()) {
	    			  entity.setGlowing(true);
	    		  }
	      	}
		}
		super.onRenderWorldLast(event);
	}
	
	Entity checkEntity(Entity e) {
		Entity entity = null;
		Hack targets = HackManager.getHack("Targets");
		if(targets.isToggledValue("Players") && e instanceof EntityPlayer) {
			entity = e;
		} else if(targets.isToggledValue("Mobs") && e instanceof EntityLiving) {
			entity = e;
		} else if(e instanceof EntityItem) {
			entity = e;
		} else if(e instanceof EntityArrow) {
			entity = e;
		}
		return entity;	
	}
}
