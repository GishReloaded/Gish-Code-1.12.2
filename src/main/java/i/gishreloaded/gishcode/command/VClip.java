package i.gishreloaded.gishcode.command;

import java.math.BigInteger;


import i.gishreloaded.gishcode.utils.visual.ChatUtils;
import i.gishreloaded.gishcode.wrappers.Wrapper;
import net.minecraft.item.ItemStack;

public class VClip extends Command
{
	public VClip()
	{
		super("vclip");
	}

	@Override
	public void runCommand(String s, String[] args)
	{
		try
		{
				Wrapper.INSTANCE.player().setPosition(Wrapper.INSTANCE.player().posX,
						Wrapper.INSTANCE.player().posY + new BigInteger(args[0]).longValue(), Wrapper.INSTANCE.player().posZ);
				//Wrapper.INSTANCE.player().setPosition(Wrapper.INSTANCE.player().posX,
						//Wrapper.INSTANCE.player().posY + Integer.valueOf(args[0]), Wrapper.INSTANCE.player().posZ);
			ChatUtils.message("Height teleported to " + new BigInteger(args[0]).longValue());
		}
		catch(Exception e)
		{
			ChatUtils.error("Usage: " + getSyntax());
		}
	}
	
	@Override
	public String getDescription()
	{
		return "Teleports you up/down.";
	}

	@Override
	public String getSyntax()
	{
		return "vclip <height>";
	}
}