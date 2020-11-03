package i.gishreloaded.gishcode.command;

import net.minecraft.client.Minecraft;

public abstract class Command {
	
	private String command;
	private String execute;
	private int key;
	
	public Command(String command) {
		this.command = command;
		this.key = -1;
	}

	public abstract void runCommand(String s, String[] args);
	public abstract String getDescription();
	public abstract String getSyntax();

	public String getCommand() {
		return command;
	}
	
	public int getKey() {
		return key;
	}
	
	public void setKey(int key) {
		this.key = key;
	}
	
	public String getExecute() {
		return execute;
	}
	
	public void setExecute(String execute) {
		this.execute = execute;
	}
}