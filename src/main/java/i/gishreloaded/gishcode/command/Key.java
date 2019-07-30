package i.gishreloaded.gishcode.command;

import org.lwjgl.input.Keyboard;

import i.gishreloaded.gishcode.hack.Hack;
import i.gishreloaded.gishcode.managers.CommandManager;
import i.gishreloaded.gishcode.managers.HackManager;
import i.gishreloaded.gishcode.utils.visual.ChatUtils;

public class Key extends Command
{
	public Key()
	{
		super("key");
	}

	@Override
	public void runCommand(String s, String[] args)
	{
		try
		{
			for(Hack hack : HackManager.getHacks()) {
				if(hack.getName().equalsIgnoreCase(args[1])) {
					hack.setKey(Keyboard.getKeyIndex((args[0].toUpperCase())));
			 		ChatUtils.message(hack.getName() + " key changed to \u00a79" + Keyboard.getKeyName(hack.getKey()));
				}
			}
		}
		catch(Exception e)
		{
			ChatUtils.error("Usage: " + getSyntax());
		}
	}

	@Override
	public String getDescription()
	{
		return "Change key for hack.";
	}

	@Override
	public String getSyntax()
	{
		return "key <key> <hack>";
	}
}