package i.gishreloaded.gishcode.command;

import java.math.BigInteger;


import i.gishreloaded.gishcode.utils.visual.ChatUtils;
import i.gishreloaded.gishcode.wrappers.Wrapper;

import net.minecraft.item.ItemStack;
import net.minecraft.util.math.Vec3d;
import net.minecraft.entity.Entity;
import net.minecraft.command.CommandSource;

public class HClip extends Command {
    public HClip() {
        super("hclip");
    }
    @Override
    double blocks = context.getArgument("blocks", Double.class);
    Vec3d forward = Vec3d.fromPolar(0, Wrapper.INSTANCE.player.getYaw()).normalize();
    public void runCommand(String s, String[] args)
    {
		try
		{
        Wrapper.INSTANCE.player().setPosition(Wrapper.INSTANCE.player().getX() + forward.x * blocks, Wrapper.INSTANCE.player().getY(), Wrapper.INSTANCE.player().getZ() + forward.z * blocks);
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
		return "Teleports you horizontal.";
	}

	@Override
	public String getSyntax()
	{
		return "hclip <distance>";
	}
}
