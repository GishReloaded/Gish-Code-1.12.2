package i.gishreloaded.gishcode.command;

import i.gishreloaded.gishcode.Wrapper;
import i.gishreloaded.gishcode.managers.FriendManager;
import i.gishreloaded.gishcode.managers.PickupFilterManager;
import i.gishreloaded.gishcode.utils.ChatUtils;
import i.gishreloaded.gishcode.utils.LoginUtils;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class PickupFilter extends Command
{
	public PickupFilter()
	{
		super("pfilter");
	}

	@Override
	public void runCommand(String s, String[] args)
	{
		try
		{
			if(args[0].equalsIgnoreCase("add")) {
				int id = Integer.parseInt(args[1]);
				Item item = Item.getItemById(id);
				if(PickupFilterManager.isItemNull(item)) {
					return;
				}
				PickupFilterManager.addItem(id);
			}
			else
			if(args[0].equalsIgnoreCase("remove")) {
				int id = Integer.parseInt(args[1]);
				Item item = Item.getItemById(id);
				if(PickupFilterManager.isItemNull(item)) {
					return;
				}
				PickupFilterManager.removeItem(id);
			}
			else
			if(args[0].equalsIgnoreCase("clear")) {
				PickupFilterManager.clear();
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
		return "PickupFilter manager.";
	}

	@Override
	public String getSyntax()
	{
		return "pfilter <add/remove/clear> <id>";
	}
}