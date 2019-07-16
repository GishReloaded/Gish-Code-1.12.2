package i.gishreloaded.gishcode.utils;

import net.minecraft.util.math.BlockPos;

public class XRayData {
	
	private int id;
	private int red;
	private int green;
	private int blue;
	
	public XRayData(int id, int red, int green, int blue) {
		this.id = id;
		this.red = red;
		this.green = green;
		this.blue = blue;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getRed() {
		return red;
	}
	public void setRed(int red) {
		this.red = red;
	}
	public int getGreen() {
		return green;
	}
	public void setGreen(int green) {
		this.green = green;
	}
	public int getBlue() {
		return blue;
	}
	public void setBlue(int blue) {
		this.blue = blue;
	}
}
