package i.gishreloaded.gishcode.gui.click;

import java.io.IOException;
import java.util.ArrayList;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import i.gishreloaded.gishcode.Main;
import i.gishreloaded.gishcode.command.Command;
import i.gishreloaded.gishcode.gui.GuiTextField;
import i.gishreloaded.gishcode.gui.Tooltip;
import i.gishreloaded.gishcode.hack.Hack;
import i.gishreloaded.gishcode.managers.CommandManager;
import i.gishreloaded.gishcode.managers.FileManager;
import i.gishreloaded.gishcode.managers.HackManager;
import i.gishreloaded.gishcode.utils.Utils;
import i.gishreloaded.gishcode.utils.visual.ChatUtils;
import i.gishreloaded.gishcode.utils.visual.ColorUtils;
import i.gishreloaded.gishcode.wrappers.Wrapper;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;

public class ClickGuiScreen extends GuiScreen {

    public static ClickGui clickGui;
    public static int[] mouse = new int[2];
    public static Tooltip tooltip = null;

	public ClickGuiScreen() {}
   
	@Override
	protected void mouseClicked(int x, int y, int button) throws IOException { super.mouseClicked(x, y, button); }
   
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
	   	tooltip = null;
       	clickGui.render();
       	if(tooltip != null) tooltip.render();
       	drawRect(0, 0, 0, 0, 0); // TODO WTF ?
		super.drawScreen(mouseX, mouseY, partialTicks);
	}
   
   @Override
   public void initGui() { Keyboard.enableRepeatEvents(true); super.initGui(); }
    
    @Override
	public void updateScreen() {
		clickGui.onUpdate();
		super.updateScreen();
	}
	
	@Override
	public void onGuiClosed() {
		Keyboard.enableRepeatEvents(false);
		super.onGuiClosed();
	}
	
	private boolean handleKeyScroll(int key) { // TODO Fix Scroll
		if(Utils.isMoving(Wrapper.INSTANCE.player())) return false;
		if (key == Keyboard.KEY_W) return clickGui.onMouseScroll(3); else 
        if (key == Keyboard.KEY_S) return clickGui.onMouseScroll(-3);
		return false;
	}
	
	private void handleKeyboard() {
		if (Keyboard.isCreated()) {
            Keyboard.enableRepeatEvents(true);
            while (Keyboard.next()) {
                if (Keyboard.getEventKeyState()) {
                	if(!this.handleKeyScroll(Keyboard.getEventKey()))
                    if (Keyboard.getEventKey() == Keyboard.KEY_RETURN) {
                        mc.displayGuiScreen((GuiScreen)null);
                        FileManager.saveHacks();
                        FileManager.saveClickGui();
                    } else
                    if (Keyboard.getEventKey() == Keyboard.KEY_ESCAPE) {
                        mc.displayGuiScreen(null);
                        FileManager.saveHacks();
                        FileManager.saveClickGui();
                    } else {
                        clickGui.onkeyPressed(Keyboard.getEventKey(), Keyboard.getEventCharacter());
                    }
                } else {
                    clickGui.onKeyRelease(Keyboard.getEventKey(), Keyboard.getEventCharacter());
                }
            }
        }
	}
	
	private void handleMouse() {
		if (Mouse.isCreated()) {
            while (Mouse.next()) {
                ScaledResolution scaledResolution = new ScaledResolution(mc);
                int mouseX = Mouse.getEventX() * scaledResolution.getScaledWidth() / mc.displayWidth;
                int mouseY = scaledResolution.getScaledHeight() - Mouse.getEventY() * scaledResolution.getScaledHeight() / mc.displayHeight - 1;
                if (Mouse.getEventButton() == -1) {
                    clickGui.onMouseScroll((Mouse.getEventDWheel() / 100) * 3);
                    clickGui.onMouseUpdate(mouseX, mouseY);
                    mouse[0] = mouseX;
                    mouse[1] = mouseY;
                } else if (Mouse.getEventButtonState()) {
                    clickGui.onMouseClick(mouseX, mouseY, Mouse.getEventButton());
                } else {
                    clickGui.onMouseRelease(mouseX, mouseY);
                }
            }
        }
	}
	
    @Override
    public void handleInput() throws IOException {
    	try {
	        int scale = mc.gameSettings.guiScale;
	        mc.gameSettings.guiScale = 2;
	        this.handleKeyboard();
	        this.handleMouse();
	        mc.gameSettings.guiScale = scale;
	        super.handleInput();
    	} catch(Exception ex) {
    		ex.printStackTrace();
    		ChatUtils.error("Exception: handleInput");
    		ChatUtils.error(ex.toString());
    		Utils.copy(ex.toString());
    	}
    }
}