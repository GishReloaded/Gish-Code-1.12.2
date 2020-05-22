package i.gishreloaded.gishcode.command;

import i.gishreloaded.gishcode.utils.LoginUtils;
import i.gishreloaded.gishcode.utils.visual.ChatUtils;
import i.gishreloaded.gishcode.wrappers.Wrapper;
import net.minecraft.item.ItemStack;

public class Login extends Command
{
	public Login()
	{
		super("login");
	}

	@Override
	public void runCommand(String s, String[] args)
	{
		try
		{
			if(args.length > 1 || args[0].contains(":")) {
				String email = "";
				String password = "";
				if(args[0].contains(":")) {
					String[] split = args[0].split(":", 2);
					email = split[0];
					password = split[1];
				}
				else
				{
					email = args[0];
					password = args[1];
				}
				String log = LoginUtils.loginAlt(email, password);
				ChatUtils.warning(log);
			} 
			else 
			{
				LoginUtils.changeCrackedName(args[0]);
				ChatUtils.warning("Logged [Cracked]: " + Wrapper.INSTANCE.mc().getSession().getUsername());
			}
		}
		catch(Exception e)
		{
			ChatUtils.error("Usage: " + getSyntax());
		}
	}

	@Override
	public String getDescription()
	{
		return "Change session.";
	}

	@Override
	public String getSyntax()
	{
		return "login <email> <password>/<nick>";
	}
}