package i.gishreloaded.gishcode.hack.hacks;

import java.util.ArrayList;

import i.gishreloaded.gishcode.hack.Hack;
import i.gishreloaded.gishcode.hack.HackCategory;
import i.gishreloaded.gishcode.managers.HackManager;
import i.gishreloaded.gishcode.managers.PickupFilterManager;
import i.gishreloaded.gishcode.utils.Utils;
import i.gishreloaded.gishcode.utils.system.Connection.Side;
import i.gishreloaded.gishcode.utils.visual.ChatUtils;
import i.gishreloaded.gishcode.utils.visual.RenderUtils;
import i.gishreloaded.gishcode.value.BooleanValue;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;

public class PickupFilter extends Hack{
	
	public BooleanValue all;
	
	public PickupFilter() {
		super("PickupFilter", HackCategory.ANOTHER);
		
		this.all = new BooleanValue("IgnoreAll", false);
		this.addValue(all);
	}
	
	@Override
	public String getDescription() {
		return "Filters item picking.";
	}
	
	@Override
	public void onItemPickup(EntityItemPickupEvent event) {
		if(this.all.getValue()) {
			event.setCanceled(true);
			return;
		}
		for(int itemId : PickupFilterManager.items) {
			Item item = Item.getItemById(itemId);
			if(item == null) continue;
			if(event.getItem().getItem().getItem() == item)
				event.setCanceled(true);
		}
		super.onItemPickup(event);
	}
}
