package i.gishreloaded.gishcode.command;

import i.gishreloaded.gishcode.managers.CommandManager;
import i.gishreloaded.gishcode.utils.visual.ChatUtils;

public class Help extends Command
{
	public Help()
	{
		super("help");
	}

	@Override
	public void runCommand(String s, String[] args)
	{
		for(Command cmd: CommandManager.commands)
		{
			if(cmd != this) {
				ChatUtils.message(cmd.getSyntax().replace("<", "<\2479").replace(">", "\2477>") + " - " + cmd.getDescription());
			}
		}
	}

	@Override
	public String getDescription()
	{
		return "Lists all commands.";
	}

	@Override
	public String getSyntax()
	{
		return "help";
	}
}