package i.gishreloaded.gishcode.command;

import java.awt.Desktop;

import i.gishreloaded.gishcode.managers.FileManager;
import i.gishreloaded.gishcode.utils.LoginUtils;
import i.gishreloaded.gishcode.utils.visual.ChatUtils;
import i.gishreloaded.gishcode.wrappers.Wrapper;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.CPacketChatMessage;

public class OpenDir extends Command
{
	public OpenDir()
	{
		super("opendir");
	}

	@Override
	public void runCommand(String s, String[] args)
	{
		try
		{
			Desktop.getDesktop().open(FileManager.GISHCODE_DIR);
		}
		catch(Exception e)
		{
			ChatUtils.error("Usage: " + getSyntax());
		}
	}

	@Override
	public String getDescription()
	{
		return "Opening directory of config.";
	}

	@Override
	public String getSyntax()
	{
		return "opendir";
	}
}