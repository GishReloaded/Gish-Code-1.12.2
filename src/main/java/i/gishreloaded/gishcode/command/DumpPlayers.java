package i.gishreloaded.gishcode.command;

import java.util.ArrayList;

import i.gishreloaded.gishcode.utils.Utils;
import i.gishreloaded.gishcode.utils.visual.ChatUtils;
import i.gishreloaded.gishcode.wrappers.Wrapper;
import net.minecraft.client.network.NetworkPlayerInfo;

public class DumpPlayers extends Command
{
	public DumpPlayers()
	{
		super("dumpplayers");
	}

	@Override
	public void runCommand(String s, String[] args)
	{
		try
		{
			ArrayList<String> list = new ArrayList<String>();
			
			if(args[0].equalsIgnoreCase("all")) {
				for(NetworkPlayerInfo npi : Wrapper.INSTANCE.mc().getConnection().getPlayerInfoMap()) {
					list.add("\n" + npi.getGameProfile().getName());
				}
			}
			else
			if(args[0].equalsIgnoreCase("creatives")) {
				for(NetworkPlayerInfo npi : Wrapper.INSTANCE.mc().getConnection().getPlayerInfoMap()) {
					if(npi.getGameType().isCreative()) {
						list.add("\n" + npi.getGameProfile().getName());
					}
				}	
			}
			
			if(list.isEmpty()) {
				ChatUtils.error("List is empty.");
			}
			else
			{
				Utils.copy(list.toString());
				ChatUtils.message("List copied to clipboard.");
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
		return "Get list of players.";
	}

	@Override
	public String getSyntax()
	{
		return "dumpplayers <all/creatives>";
	}
}