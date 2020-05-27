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

public class ItemESP extends Hack{
	
	public ItemESP() {
		super("ItemESP", HackCategory.VISUAL);
	}
	
	@Override
	public String getDescription() {
		return "Highlights nearby items.";
	}
	
	@Override
	public void onRenderWorldLast(RenderWorldLastEvent event) {
		for (Object object : Utils.getEntityList()) {
			if(object instanceof EntityItem || object instanceof EntityArrow) {
				Entity item = (Entity)object;
				RenderUtils.drawESP(item, 1.0f, 1.0f, 1.0f, 1.0f, event.getPartialTicks());
			}
		}
		super.onRenderWorldLast(event);
	}
}
