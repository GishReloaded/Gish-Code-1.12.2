package i.gishreloaded.gishcode.managers;

import java.util.LinkedList;

import i.gishreloaded.gishcode.utils.visual.ChatUtils;
import i.gishreloaded.gishcode.xray.XRayData;
import net.minecraft.block.Block;

public class XRayManager {
	
	public static LinkedList<XRayData> xrayList = new LinkedList<XRayData>();
	
	public static void clear() {
		if(!xrayList.isEmpty()) {
			xrayList.clear();
			FileManager.saveXRayData();
			ChatUtils.message("\u00a7dXRay \u00a77list clear.");
		}
	}
	
	public static LinkedList<XRayData> getDataById(int id) {
		LinkedList<XRayData> list = new LinkedList<XRayData>();
		for(XRayData x : xrayList) {
        	if(x.getId() == id) {
        		list.add(x);
        	}
        }
		return list;
	}
	
	public static XRayData getDataByMeta(int meta) {
		XRayData data = null;
		for(XRayData x : xrayList) {
        	if(x.getMeta() == meta) {
        		data = x;
        	}
        }
		return data;
	}
	
	public static void add(XRayData data) {
		if(Block.getBlockById(data.getId()) == null) {
			ChatUtils.error("Block is null.");
			return;
		}
		LinkedList<XRayData> list = getDataById(data.getId());
		if(list.isEmpty()) {
			addData(data);
			return;
		}
		boolean isId = false;
		boolean isMeta = false;
		for(XRayData currentData : list) {
			if(currentData.getId() == data.getId()) {
				isId = true;
			}
			if(currentData.getMeta() == data.getMeta()) {
				isMeta = true;
			}
		}
		if(isId && isMeta) {
			return;
		}
		addData(data);
	}
	
	public static void addData(i.gishreloaded.gishcode.xray.XRayData data) {
		xrayList.add(data);
		FileManager.saveXRayData();
		ChatUtils.message(String.format("\u00a77ID: \u00a73%s \u00a77META: \u00a73%s \u00a77NAME: \u00a73%s \u00a77RGB: \u00a7c%s\u00a77, \u00a7a%s\u00a77, \u00a79%s \u00a77- ADDED.", 
				data.getId(), data.getMeta(), Block.getBlockById(data.getId()).getLocalizedName(), data.getRed(), data.getGreen(), data.getBlue()));
	}
	
	public static void removeData(int id) {
		for(XRayData data : getDataById(id)) {
			if(xrayList.contains(data)) {
				xrayList.remove(data);
				FileManager.saveXRayData();
				ChatUtils.message(String.format("\u00a77ID: \u00a73%s \u00a77NAME: \u00a73%s \u00a77RGB: \u00a7c%s\u00a77, \u00a7a%s\u00a77, \u00a79%s \u00a77- REMOVED.", 
						data.getId(), Block.getBlockById(data.getId()).getLocalizedName(), data.getRed(), data.getGreen(), data.getBlue()));
			}
		}
	}
}
