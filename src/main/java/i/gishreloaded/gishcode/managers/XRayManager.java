package i.gishreloaded.gishcode.managers;

import java.util.ArrayList;
import java.util.List;

import i.gishreloaded.gishcode.Wrapper;
import i.gishreloaded.gishcode.hack.Hack;
import i.gishreloaded.gishcode.utils.ChatUtils;
import i.gishreloaded.gishcode.utils.XRayData;
import net.minecraft.block.Block;

public class XRayManager {
	
	public static ArrayList<XRayData> xrayList = new ArrayList<XRayData>();
	
	public static void clear() {
		if(!xrayList.isEmpty()) {
			xrayList.clear();
			FileManager.saveXRayData();
			ChatUtils.message("\u00a7dXRay \u00a77list clear.");
		}
	}
	
	public static XRayData getData(int id) {
		XRayData data = null;
		for(XRayData x : xrayList) {
        	if(x.getId() == id) {
        		data = x;
        	}
        }
		return data;
	}
	
	public static void addData(XRayData data) {
		XRayData d = getData(data.getId());
		if(d == null) {
			xrayList.add(data);
			FileManager.saveXRayData();
			ChatUtils.message(String.format("\u00a77ID: \u00a73%s \u00a77NAME: \u00a73%s \u00a77RGB: \u00a7c%s\u00a77, \u00a7a%s\u00a77, \u00a79%s \u00a77- ADDED.", 
					data.getId(), Block.getBlockById(data.getId()).getLocalizedName(), data.getRed(), data.getGreen(), data.getBlue()));
		}
	}
	
	public static void removeData(int id) {
		XRayData data = getData(id);
		if(xrayList.contains(data)) {
			xrayList.remove(data);
			FileManager.saveXRayData();
			ChatUtils.message(String.format("\u00a77ID: \u00a73%s \u00a77NAME: \u00a73%s \u00a77RGB: \u00a7c%s\u00a77, \u00a7a%s\u00a77, \u00a79%s \u00a77- REMOVED.", 
					data.getId(), Block.getBlockById(data.getId()).getLocalizedName(), data.getRed(), data.getGreen(), data.getBlue()));
		}
	}
}
