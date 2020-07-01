package i.gishreloaded.gishcode.hack.hacks;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

import org.lwjgl.opengl.GL11;

import i.gishreloaded.gishcode.Main;
import i.gishreloaded.gishcode.hack.Hack;
import i.gishreloaded.gishcode.hack.HackCategory;
import i.gishreloaded.gishcode.managers.HackManager;

import i.gishreloaded.gishcode.utils.visual.ChatUtils;
import i.gishreloaded.gishcode.utils.visual.ColorUtils;
import i.gishreloaded.gishcode.utils.visual.RenderUtils;
import i.gishreloaded.gishcode.value.BooleanValue;
import i.gishreloaded.gishcode.value.Mode;
import i.gishreloaded.gishcode.value.ModeValue;
import i.gishreloaded.gishcode.value.Value;
import i.gishreloaded.gishcode.wrappers.Wrapper;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraftforge.client.event.RenderGameOverlayEvent.Text;

public class HUD extends Hack{
	
	public BooleanValue effects;
	
	public HUD() {
		super("HUD", HackCategory.VISUAL);
		this.setToggled(true);
		this.setShow(false);
		
		effects = new BooleanValue("Effects", false);
		this.addValue(effects);
	}
	
	@Override
	public String getDescription() {
		return "Heads-Up Display.";
	}
	
	@Override
	public void onRenderGameOverlay(Text event) {
		//if(Wrapper.INSTANCE.mc().getLanguageManager().getCurrentLanguage() == Wrapper.INSTANCE.mc().getLanguageManager().getLanguage("ru_ru")) {}
		GL11.glPushMatrix();
		GL11.glScalef(1.5f, 1.5f, 1.5f);
		Wrapper.INSTANCE.fontRenderer().drawStringWithShadow(Main.NAME, 4, 4, ClickGui.getColor());
		GL11.glScalef(0.6f, 0.6f, 0.6f);
		Wrapper.INSTANCE.fontRenderer().drawStringWithShadow(Main.MCVERSION, 84, 4, ClickGui.getColor());
		Wrapper.INSTANCE.fontRenderer().drawStringWithShadow("v" + Main.VERSION, 84, 14, ClickGui.getColor());
		GL11.glPopMatrix();
		
		double x = Wrapper.INSTANCE.player().posX;
		double y = Wrapper.INSTANCE.player().posY;
		double z = Wrapper.INSTANCE.player().posZ;
		
		ScaledResolution sr = new ScaledResolution(Wrapper.INSTANCE.mc());
		String coords = String.format("\u00a77X: \u00a7f%s \u00a77Y: \u00a7f%s \u00a77Z: \u00a7f%s", RenderUtils.DF((float)x, 1), RenderUtils.DF((float)y, 1), RenderUtils.DF((float)z, 1));
		boolean isChatOpen = Wrapper.INSTANCE.mc().currentScreen instanceof GuiChat;
		
		int heightFPS = isChatOpen ? sr.getScaledHeight() - 37 : sr.getScaledHeight() - 22;
		int heightCoords = isChatOpen ? sr.getScaledHeight() - 25 : sr.getScaledHeight() - 10;
		
		int colorRect = ColorUtils.color(0.0F, 0.0F, 0.0F, 0.0F);
		int colorRect2 = ColorUtils.color(0.0F, 0.0F, 0.0F, 0.5F);
		
		RenderUtils.drawStringWithRect(coords, 4, heightCoords, ClickGui.getColor(), 
				colorRect, colorRect2);
		
		RenderUtils.drawStringWithRect("\u00a77FPS: \u00a7f" + Wrapper.INSTANCE.mc().getDebugFPS(), 4, heightFPS, ClickGui.getColor(), 
				colorRect, colorRect2);
		
		int yPos = 24;
		int xPos = 4;
		for(Hack hack : HackManager.getSortedHacks()) {
			String modeName = "";
			for(Value value : hack.getValues()) {
				if(value instanceof ModeValue) {
					ModeValue modeValue = (ModeValue)value;
					if(!modeValue.getModeName().equals("Priority")) {
						for(Mode mode : modeValue.getModes()) {
							if(mode.isToggled()) {
								modeName = modeName + " \u00a77" + mode.getName();
							}
						}
					}
				}
			}
			if(effects.getValue()) {
				xPos = 6;
				RenderUtils.drawBorderedRect(xPos - 2, yPos - 2, xPos + Wrapper.INSTANCE.fontRenderer().getStringWidth(hack.getName() + modeName) + 2, yPos + 10, 1, colorRect, ClickGui.getColor());
			} else {
				xPos = 4;
			}
			RenderUtils.drawStringWithRect(hack.getName() + modeName, xPos, yPos, ClickGui.getColor(), 
					colorRect, colorRect2);
			if(effects.getValue()) {
				RenderUtils.drawBorderedRect(xPos - 2, yPos - 2, xPos - 6, yPos + 10, 1, ClickGui.getColor(), ClickGui.getColor());
			}
			yPos += 12;
		}
		Hack toggleHack = HackManager.getToggleHack();
		if(toggleHack != null) {
			RenderUtils.drawSplash(toggleHack.isToggled()  ? 
					toggleHack.getName() + " - Enabled" : 
						 "\u00a77" + toggleHack.getName() + " - Disabled");
		}
		super.onRenderGameOverlay(event);
	}
}
