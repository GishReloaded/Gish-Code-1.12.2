package i.gishreloaded.gishcode.command;

import net.minecraft.client.Minecraft;

public abstract class Command {
	
	private String command;
	
	public Command(String command) {
		this.command = command;
	}

	public abstract void runCommand(String s, String[] args);
	public abstract String getDescription();
	public abstract String getSyntax();

	public String getCommand() {
		return command;
	}
}