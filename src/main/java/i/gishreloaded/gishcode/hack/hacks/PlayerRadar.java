package i.gishreloaded.gishcode.hack.hacks;

import i.gishreloaded.gishcode.hack.Hack;
import i.gishreloaded.gishcode.hack.HackCategory;

import i.gishreloaded.gishcode.utils.Utils;
import i.gishreloaded.gishcode.utils.visual.ColorUtils;
import i.gishreloaded.gishcode.utils.visual.RenderUtils;
import i.gishreloaded.gishcode.wrappers.Wrapper;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.client.event.RenderGameOverlayEvent.Text;

public class PlayerRadar extends Hack{

	public PlayerRadar() {
		super("PlayerRadar", HackCategory.VISUAL);
	}
	
	@Override
    public String getDescription() {
        return "Show all players around you.";
    }
	
	@Override
	public void onRenderGameOverlay(Text event) {
		int y = 2;
		ScaledResolution sr = new ScaledResolution(Wrapper.INSTANCE.mc());
		
		for(Object o : Utils.getEntityList()) {
			if(o instanceof EntityPlayer) {
				
				EntityPlayer e = (EntityPlayer) o;
				float range = Wrapper.INSTANCE.player().getDistance(e);
        		float health = ((EntityPlayer) e).getHealth();
        		
        		String heal = " \u00a72[" + RenderUtils.DF(health, 0) + "] ";
                if (health >= 12.0) {
                	heal = " \u00a72[" + RenderUtils.DF(health, 0) + "] ";
                }
                else if (health >= 4.0) {
                	heal = " \u00a76[" + RenderUtils.DF(health, 0) + "] ";
                }
                else {
                	heal = " \u00a74[" + RenderUtils.DF(health, 0) + "] ";
                }
                
        		String name = e.getGameProfile().getName();
        		String str = name + heal + "\u00a77" + "[" + RenderUtils.DF(range, 0) + "]";
        		
        		int color;
        		if(e.isInvisible()) {
        			color = ColorUtils.color(155, 155, 155, 255);
        		} else {
        			color = ColorUtils.color(255, 255, 255, 255);
        		}
        		
        		Wrapper.INSTANCE.fontRenderer().drawString(str, sr.getScaledWidth() - Wrapper.INSTANCE.fontRenderer().getStringWidth(str), y, color);
        		
        		y += 12;
			}
		}
		super.onRenderGameOverlay(event);
	}

}
