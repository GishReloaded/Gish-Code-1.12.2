package i.gishreloaded.gishcode.managers;

import java.util.ArrayList;
import java.util.List;

import i.gishreloaded.gishcode.Wrapper;
import i.gishreloaded.gishcode.hack.Hack;
import i.gishreloaded.gishcode.utils.ChatUtils;
import i.gishreloaded.gishcode.utils.XRayData;
import net.minecraft.block.Block;
import net.minecraft.item.Item;

public class PickupFilterManager {
	
	public static ArrayList<String> itemList = new ArrayList<String>();
	
	public static void clear() {
		if(!itemList.isEmpty()) {
			itemList.clear();
			FileManager.savePFilter();
			ChatUtils.message("\u00a73PickupFilter \u00a77list clear.");
		}
	}
	
	public static void add(String id) {
		itemList.add(id);
		FileManager.savePFilter();
	}
	
	public static void addItem(int id) {
		Item item = Item.getItemById(id);
		if(isItemNull(item)) {
			return;
		}
		String itemID = item.getUnlocalizedName() + ":" + id;
		if(!itemList.contains(itemID)) {
			itemList.add(itemID);
			FileManager.savePFilter();
			ChatUtils.message(item.getUnlocalizedName() + " Added to \u00a73PickupFilter \u00a77list.");
		}
	}
	
	public static void removeItem(int id) {
		Item item = Item.getItemById(id);
		if(isItemNull(item)) {
			return;
		}
		String itemID = item.getUnlocalizedName() + ":" + id;
		if(itemList.contains(itemID)) {
			itemList.remove(itemID);
			FileManager.savePFilter();
			ChatUtils.message(item.getUnlocalizedName() + " Removed from \u00a73PickupFilter \u00a77list.");
		}
	}
	
	public static boolean isItemNull(Item item) {
		if(item == null) {
			ChatUtils.error("Item is null");
			return true;
		}
		return false;
	}
	
	public static boolean isInList(int id) {
		Item item = Item.getItemById(id);
		if(item == null) {
			return false;
		}
		String itemID = item.getUnlocalizedName() + ":" + id;
		if(itemList.contains(itemID)) {
			return true;
		}
		return false;
	}
}
