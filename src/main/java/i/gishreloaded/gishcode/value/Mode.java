package i.gishreloaded.gishcode.value;

public class Mode {
	
	private String name;
	private boolean toggled;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public boolean isToggled() {
		return toggled;
	}
	public void setToggled(boolean toggled) {
		this.toggled = toggled;
	}
	public Mode(String name, boolean toggled) {
		this.name = name;
		this.toggled = toggled;
	}
}
