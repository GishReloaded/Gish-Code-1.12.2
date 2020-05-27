package i.gishreloaded.gishcode.gui;

import java.awt.Point;

import org.lwjgl.opengl.GL11;

import i.gishreloaded.gishcode.hack.hacks.ClickGui;
import i.gishreloaded.gishcode.utils.visual.ColorUtils;
import i.gishreloaded.gishcode.utils.visual.RenderUtils;
import net.minecraft.client.gui.FontRenderer;

public class Tooltip {
	
	private FontRenderer fontRenderer;
	private String text;
	private int x;
	private int y;
	
	public Tooltip(String text, int x, int y, FontRenderer fontRenderer) {
		this.text = text;
		this.x = x;
		this.y = y;
		this.fontRenderer = fontRenderer;
	}
	
	public String getText() {
		return text;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
    public void render() {
        int textColor = ClickGui.isLight ? ColorUtils.color(0, 0, 0, 255) : ColorUtils.color(200, 200, 200, 255);
        int rectColor = ClickGui.isLight ? ColorUtils.color(155, 155, 155, 155) : ColorUtils.color(100, 100, 100, 100);

        //GL11.glPushMatrix();
        //GL11.glDisable(GL11.GL_SCISSOR_TEST);
        int aboveMouse = 8;
        int width = fontRenderer.getStringWidth(text);
          
        RenderUtils.drawStringWithRect(getText(), getX() + 2, getY() - aboveMouse + 2, textColor, rectColor, rectColor);
        //GL11.glEnable(GL11.GL_SCISSOR_TEST);
        //GL11.glPopMatrix();
      }
}
