package i.gishreloaded.gishcode.gui.click;

import java.io.IOException;
import java.util.ArrayList;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import i.gishreloaded.gishcode.Main;
import i.gishreloaded.gishcode.Wrapper;
import i.gishreloaded.gishcode.command.Command;
import i.gishreloaded.gishcode.gui.GuiTextField;
import i.gishreloaded.gishcode.hack.Hack;
import i.gishreloaded.gishcode.managers.CommandManager;
import i.gishreloaded.gishcode.managers.FileManager;
import i.gishreloaded.gishcode.managers.HackManager;
import i.gishreloaded.gishcode.utils.ColorUtils;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;

public class ClickGuiScreen extends GuiScreen {

	public static String title = "Coded by Gish_Reloaded";
    public static ClickGui clickGui;
    public static int[] mouse = new int[2];
    private static GuiTextField console;
	ArrayList cmds = new ArrayList();

	public ClickGuiScreen() {
		this.cmds.clear();
        for(Command c : CommandManager.commands){
        	this.cmds.add(c.getCommand() + " - " + c.getDescription());
        }
	}
   
   @Override
	protected void mouseClicked(int x, int y, int button) throws IOException {
		super.mouseClicked(x, y, button);
		 this.console.mouseClicked(x, y, button);
   }
   
   @Override
   public void drawScreen(int mouseX, int mouseY, float partialTicks) {
       	clickGui.render();
		this.console.drawTextBox(i.gishreloaded.gishcode.hack.hacks.ClickGui.color.getRGB(), ColorUtils.colorRGB(0.0F, 0.0F, 0.0F, 1.0F));
		this.console.setTextColor(i.gishreloaded.gishcode.hack.hacks.ClickGui.color.getRGB());
	super.drawScreen(mouseX, mouseY, partialTicks);
   }
   
   @Override
   public void initGui() {
	   Keyboard.enableRepeatEvents(true);
       this.console = new GuiTextField(0, this.fontRenderer, this.width / 2 - 100, 0, 200, 14);
       this.console.setMaxStringLength(100);
       this.console.setText(title);
       this.console.setFocused(true);
	super.initGui();
   }
    
    @Override
	public void updateScreen() {
		this.console.updateCursorCounter();
		clickGui.onUpdate();
		super.updateScreen();
	}
	
	@Override
	public void onGuiClosed() {
		Keyboard.enableRepeatEvents(false);
		super.onGuiClosed();
	}
	
	void setTitle() {
		if(!console.getText().equals("Coded by Gish_Reloaded")) {
			title = "";
        }
	}
	
    @Override
    public void handleInput() throws IOException {
        int scale = mc.gameSettings.guiScale;
        mc.gameSettings.guiScale = 2;
        if (Keyboard.isCreated()) {
            Keyboard.enableRepeatEvents(true);

            while (Keyboard.next()) {
                if (Keyboard.getEventKeyState()) {
                	console.textboxKeyTyped(Keyboard.getEventCharacter(), Keyboard.getEventKey());
                    if (Keyboard.getEventKey() == 28) {
                        setTitle();
                        CommandManager.getInstance().runCommands("." + console.getText());
                        mc.displayGuiScreen((GuiScreen)null);
                        FileManager.saveHacks();
                    } else
                    if (Keyboard.getEventKey() == 1) {
                    	setTitle();
                        mc.displayGuiScreen(null);
                        FileManager.saveHacks();
                    } else {
                        clickGui.onkeyPressed(Keyboard.getEventKey(), Keyboard.getEventCharacter());
                    }
                } else {
                    clickGui.onKeyRelease(Keyboard.getEventKey(), Keyboard.getEventCharacter());
                }


            }
        }

        if (Mouse.isCreated()) {
            while (Mouse.next()) {
                ScaledResolution scaledResolution = new ScaledResolution(mc);
                int mouseX = Mouse.getEventX() * scaledResolution.getScaledWidth() / mc.displayWidth;
                int mouseY = scaledResolution.getScaledHeight() - Mouse.getEventY() * scaledResolution.getScaledHeight() / mc.displayHeight - 1;

                if (Mouse.getEventButton() == -1) {
                    if (Mouse.getEventDWheel() != 0) {
                        int x = mouseX;
                        int y = mouseY;
                        clickGui.onMouseScroll((Mouse.getEventDWheel() / 100) * 3);
                    }

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

        mc.gameSettings.guiScale = scale;

        super.handleInput();
    }
}
