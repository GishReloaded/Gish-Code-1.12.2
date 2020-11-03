package i.gishreloaded.gishcode.hack.hacks;

import org.lwjgl.input.Keyboard;

import i.gishreloaded.gishcode.Main;
import i.gishreloaded.gishcode.gui.GuiConsole;
import i.gishreloaded.gishcode.hack.Hack;
import i.gishreloaded.gishcode.hack.HackCategory;
import i.gishreloaded.gishcode.value.types.BooleanValue;
import i.gishreloaded.gishcode.wrappers.Wrapper;

public class Console extends Hack{

	public Console() {
		super("Console", HackCategory.VISUAL);
		this.setKey(Keyboard.KEY_Y);
		this.setShow(false);
	}
	
	@Override
	public String getDescription() {
		return "Console.";
	}
	
	@Override
	public void onEnable() {
		if(GhostMode.enabled) return;
		Wrapper.INSTANCE.mc().displayGuiScreen(new GuiConsole());
		super.onEnable();
	}
}
