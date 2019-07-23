package i.gishreloaded.gishcode.xray;

public class XRayData {
	
	private int id;
	private int meta;
	private int red;
	private int green;
	private int blue;
	
	public XRayData(int id, int meta, int red, int green, int blue) {
		this.id = id;
		this.meta = meta;
		this.red = red;
		this.green = green;
		this.blue = blue;
	}
	
	public int getId() {
		return id;
	}
	public int getMeta() {
		return meta;
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
