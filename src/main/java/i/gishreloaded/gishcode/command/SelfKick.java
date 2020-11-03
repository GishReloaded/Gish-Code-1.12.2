package i.gishreloaded.gishcode.command;

import i.gishreloaded.gishcode.utils.LoginUtils;
import i.gishreloaded.gishcode.utils.visual.ChatUtils;
import i.gishreloaded.gishcode.wrappers.Wrapper;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.CPacketChatMessage;
import net.minecraft.network.play.client.CPacketPlayer;

public class SelfKick extends Command
{
	public SelfKick()
	{
		super("selfkick");
	}

	@Override
	public void runCommand(String s, String[] args)
	{
		try
		{
			Wrapper.INSTANCE.sendPacket(new CPacketPlayer.Rotation(Float.NEGATIVE_INFINITY, Float.NEGATIVE_INFINITY, false));
		}
		catch(Exception e)
		{
			ChatUtils.error("Usage: " + getSyntax());
		}
	}

	@Override
	public String getDescription()
	{
		return "Kick you from Server.";
	}

	@Override
	public String getSyntax()
	{
		return "selfkick";
	}
}