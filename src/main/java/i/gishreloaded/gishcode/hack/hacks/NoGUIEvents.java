package i.gishreloaded.gishcode.hack.hacks;

import i.gishreloaded.gishcode.hack.Hack;
import i.gishreloaded.gishcode.hack.HackCategory;

public class NoGuiEvents extends Hack{

	public NoGuiEvents() {
		super("NoGUIEvents", HackCategory.ANOTHER);
	}
	
	@Override
	public String getDescription() {
		return "Disables events when the GUI is open.";
	}
}
