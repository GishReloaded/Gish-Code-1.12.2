package i.gishreloaded.gishcode.managers;

import java.util.LinkedList;

import i.gishreloaded.gishcode.utils.visual.ChatUtils;
import net.minecraft.item.Item;

public class PickupFilterManager {

	public static LinkedList<Integer> items = new LinkedList<Integer>();
	
	public static void addItem(int id) {
		try {
			if(Item.getItemById(id) == null) {
				ChatUtils.error("Item is null.");
				return;
			}
			for(int itemId : items) {
				if(itemId == id) {
					ChatUtils.error("Item already added.");
					return;
				}
			}
			items.add(id);
			FileManager.savePickupFilter();
			ChatUtils.message(String.format("\u00a77ID: \u00a73%s \u00a77NAME: \u00a73%s \u00a77- ADDED.",
					id, Item.getItemById(id).getUnlocalizedName()));
		} catch (Exception ex) { ChatUtils.error(ex.getMessage()); };
	}
	
	public static void removeItem(int id) {
		for(int itemId : items) {
			if(itemId == id) {
				items.remove((Object)(id));
				ChatUtils.message(String.format("\u00a77ID: \u00a73%s \u00a77- REMOVED.", id));
				FileManager.savePickupFilter();
				return;
			}
		}
		ChatUtils.error("Item not found.");
	}
	
	public static void clear() {
		if(items.isEmpty()) 
			return;
		items.clear();
		FileManager.savePickupFilter();
		ChatUtils.message("\u00a7dPickupFilter \u00a77list clear.");
	}
}
