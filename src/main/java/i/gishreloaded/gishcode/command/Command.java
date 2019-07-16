package i.gishreloaded.gishcode.command;

import net.minecraft.client.Minecraft;

/**
 * Main Command Class. All Commands should extends it.
 * @author ReesZRB
 *
 */
public abstract class Command {
	public static Minecraft mc = Minecraft.getMinecraft();
	
	/**
	 * Unlocalized Command
	 */
	private String command;

	/**
     * Main constructor. Defines all things that a command needs
     */
	public Command(String command) {
		this.command = command;
	}

	/**
	 * Runs Command
	 */
	public abstract void runCommand(String s, String[] args);
	
	/**
	 * Gets Description of a Command
	 */
	public abstract String getDescription();
	
	/**
	 * Gets Syntax of a Command
	 */
	public abstract String getSyntax();
	
	/**
	 * Gets Command
	 */
	public String getCommand() {
		return command;
	}
}