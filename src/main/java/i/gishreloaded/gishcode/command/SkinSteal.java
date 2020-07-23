package i.gishreloaded.gishcode.command;

import com.mojang.authlib.minecraft.MinecraftProfileTexture.Type;

import i.gishreloaded.gishcode.managers.SkinChangerManager;
import i.gishreloaded.gishcode.utils.LoginUtils;
import i.gishreloaded.gishcode.utils.visual.ChatUtils;
import i.gishreloaded.gishcode.wrappers.Wrapper;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.CPacketChatMessage;
import net.minecraft.util.ResourceLocation;

public class SkinSteal extends Command
{
	public SkinSteal()
	{
		super("skinsteal");
	}

	@Override
	public void runCommand(String s, String[] args)
	{
		try
		{
			SkinChangerManager.addTexture(Type.SKIN, args[0]);
			//if(args[0].equalsIgnoreCase("add")) {
				//SkinChangerManager.setTexture(Type.valueOf(args[1].toUpperCase()), args[2]);
//			} else 
//				if(args[0].equalsIgnoreCase("remove")) {
//					SkinChangerManager.removeTexture(Type.valueOf(args[1].toUpperCase()));
//				}
//				else 
//					if(args[0].equalsIgnoreCase("clear")) {
//						SkinChangerManager.clear();
//					}
		}
		catch(Exception e)
		{
			ChatUtils.error("Usage: " + getSyntax());
		}
	}

	@Override
	public String getDescription()
	{
		return "Steal skin for 'SkinChanger'.";
	}

	@Override
	public String getSyntax()
	{
		return "skinsteal <nickname/URL>";
	}
}