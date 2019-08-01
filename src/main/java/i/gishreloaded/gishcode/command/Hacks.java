package i.gishreloaded.gishcode.command;

import i.gishreloaded.gishcode.hack.Hack;
import i.gishreloaded.gishcode.managers.CommandManager;
import i.gishreloaded.gishcode.managers.HackManager;
import i.gishreloaded.gishcode.utils.visual.ChatUtils;

public class Hacks extends Command
{
	public Hacks()
	{
		super("hacks");
	}

	@Override
	public void runCommand(String s, String[] args)
	{
		for(Hack hack : HackManager.getHacks()) {
			ChatUtils.message(String.format("%s \u00a79| \u00a7f%s \u00a79| \u00a7f%s \u00a79| \u00a7f%s", hack.getName(), hack.getCategory(), hack.getKey(), hack.isToggled()));	
		}
		ChatUtils.message("Loaded " + HackManager.getHacks().size() + " Hacks.");
	}

	@Override
	public String getDescription()
	{
		return "Lists all hacks.";
	}

	@Override
	public String getSyntax()
	{
		return "hacks";
	}
}