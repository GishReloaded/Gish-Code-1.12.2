package i.gishreloaded.gishcode.command;

import i.gishreloaded.gishcode.managers.XRayManager;
import i.gishreloaded.gishcode.utils.ChatUtils;
import i.gishreloaded.gishcode.utils.XRayData;

public class XRay extends Command
{
	public XRay()
	{
		super("xray");
	}

	@Override
	public void runCommand(String s, String[] args)
	{
		try
		{
			if(args[0].equalsIgnoreCase("add")) {
				XRayManager.addData(new XRayData(
						Integer.parseInt(args[1]),
						Integer.parseInt(args[2]),
						Integer.parseInt(args[3]),
						Integer.parseInt(args[4])));
			}
			else
			if(args[0].equalsIgnoreCase("remove")) {
				XRayManager.removeData(Integer.parseInt(args[1]));
			}
			else
			if(args[0].equalsIgnoreCase("clear")) {
				XRayManager.clear();
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
		return "XRay manager.";
	}

	@Override
	public String getSyntax()
	{
		return "xray <add/remove/clear> <id> <red> <green> <blue>";
	}
}