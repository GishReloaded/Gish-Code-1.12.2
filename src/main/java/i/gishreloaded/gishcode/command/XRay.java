package i.gishreloaded.gishcode.command;

import org.lwjgl.input.Mouse;

import i.gishreloaded.gishcode.managers.XRayManager;
import i.gishreloaded.gishcode.utils.Utils;
import i.gishreloaded.gishcode.utils.visual.ChatUtils;
import i.gishreloaded.gishcode.wrappers.Wrapper;
import i.gishreloaded.gishcode.xray.XRayData;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;

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
				if(args[1].equalsIgnoreCase("mouse") && Wrapper.INSTANCE.mc().objectMouseOver != null) {
					RayTraceResult object = Wrapper.INSTANCE.mc().objectMouseOver;
				
					if(object.typeOfHit == RayTraceResult.Type.BLOCK) {
						
						BlockPos block = object.getBlockPos();
						IBlockState state = Wrapper.INSTANCE.world().getBlockState(block);
						int id = Block.getIdFromBlock(state.getBlock());
						int meta =  state.getBlock().getMetaFromState(state);
						
						XRayData data = new XRayData(id, meta, Utils.random(0, 254), Utils.random(0, 254), Utils.random(0, 254));
						XRayManager.addData(data);
					}
				} else {
					if(args[1].contains(":")) {
						String[] split = args[1].split(":");
						XRayManager.add(new XRayData(
								Integer.parseInt(split[0]),
								Integer.parseInt(split[1]),
								Integer.parseInt(args[2]),
								Integer.parseInt(args[3]),
								Integer.parseInt(args[4])));
						} else {
							XRayManager.add(new XRayData(
									Integer.parseInt(args[1]),
									0,
									Integer.parseInt(args[2]),
									Integer.parseInt(args[3]),
									Integer.parseInt(args[4])));
					}
				}
				
				
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
		return "xray add <id:meta> <red> <green> <blue> | add mouse | remove <id> | clear";
	}
}