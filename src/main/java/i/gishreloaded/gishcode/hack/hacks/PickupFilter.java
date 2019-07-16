package i.gishreloaded.gishcode.hack.hacks;

import i.gishreloaded.gishcode.Wrapper;
import i.gishreloaded.gishcode.hack.Hack;
import i.gishreloaded.gishcode.hack.HackCategory;
import i.gishreloaded.gishcode.managers.PickupFilterManager;
import i.gishreloaded.gishcode.utils.ChatUtils;
import i.gishreloaded.gishcode.utils.Utils;
import i.gishreloaded.gishcode.value.BooleanValue;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.ProjectileImpactEvent;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.ClientTickEvent;

public class PickupFilter extends Hack{
	
	public BooleanValue allItems;
	
	public PickupFilter() {
		super("PickupFilter", HackCategory.PLAYER);
		
		allItems = new BooleanValue("AllItems", false);
		this.addValue(allItems);
	}
	
	@Override
	public void onEnable() {
		super.onEnable();
	}
	
	@Override
	public void onDisable() {
		super.onDisable();
	}
    
	@Override
	public void onItemPickup(EntityItemPickupEvent event) {
		if(Utils.isPlayer(event.getEntity())) {
			if(allItems.getValue()) {
				Wrapper.INSTANCE.player().dropItem(true);
				return;
			}
			ItemStack stack = event.getItem().getItem();
			if(stack == null) {
				return;
			}
			Item item = stack.getItem();
			if(item == null) {
				return;
			}
			if(PickupFilterManager.isInList(Item.getIdFromItem(item))){
				Wrapper.INSTANCE.player().dropItem(item, stack.getMaxStackSize());
			}
		}
		super.onItemPickup(event);
	}
	
}
