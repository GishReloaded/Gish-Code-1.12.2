package i.gishreloaded.gishcode.command;

import i.gishreloaded.gishcode.utils.LoginUtils;
import i.gishreloaded.gishcode.utils.Utils;
import i.gishreloaded.gishcode.utils.visual.ChatUtils;
import i.gishreloaded.gishcode.wrappers.Wrapper;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.CPacketChatMessage;

public class SelfDamage extends Command
{
	public SelfDamage()
	{
		super("selfdamage");
	}

	@Override
	public void runCommand(String s, String[] args)
	{
		try
		{   //0.0625D
			double damage = Double.parseDouble(args[0]);
			Utils.selfDamage(damage);
		}
		catch(Exception e)
		{
			ChatUtils.error("Usage: " + getSyntax());
		}
	}

	@Override
	public String getDescription()
	{
		return "Deals damage to you (useful for bypassing AC).";
	}

	@Override
	public String getSyntax()
	{
		return "selfdamage <damage>";
	}
}