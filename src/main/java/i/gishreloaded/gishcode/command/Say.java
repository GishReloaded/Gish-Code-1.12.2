package i.gishreloaded.gishcode.command;

import i.gishreloaded.gishcode.Wrapper;
import i.gishreloaded.gishcode.utils.ChatUtils;
import i.gishreloaded.gishcode.utils.LoginUtils;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.CPacketChatMessage;

public class Say extends Command
{
	public Say()
	{
		super("say");
	}

	@Override
	public void runCommand(String s, String[] args)
	{
		try
		{
			String content = "";
			for(int i = 0; i < args.length; i++) {
				content = content + " " + args[i];
			}
			Wrapper.INSTANCE.sendPacket(new CPacketChatMessage(content));
		}
		catch(Exception e)
		{
			ChatUtils.error("Usage: " + getSyntax());
		}
	}

	@Override
	public String getDescription()
	{
		return "Send message to chat.";
	}

	@Override
	public String getSyntax()
	{
		return "say <message>";
	}
}