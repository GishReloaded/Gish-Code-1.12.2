package i.gishreloaded.gishcode.command;

import i.gishreloaded.gishcode.managers.EnemyManager;
import i.gishreloaded.gishcode.managers.FriendManager;
import i.gishreloaded.gishcode.utils.LoginUtils;
import i.gishreloaded.gishcode.utils.system.Wrapper;
import i.gishreloaded.gishcode.utils.visual.ChatUtils;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;

public class Effect extends Command
{
	public Effect()
	{
		super("effect");
	}

	@Override
	public void runCommand(String s, String[] args)
	{
		try
		{
			if(args[0].equalsIgnoreCase("add")) {
				int id = Integer.parseInt(args[1]);
				int duration = Integer.parseInt(args[2]);
				int amplifier = Integer.parseInt(args[3]);
				if(Potion.getPotionById(id) == null) {
					ChatUtils.error("Potion is null");
					return;
				}
				addEffect(id, duration, amplifier);
			}
			else
			if(args[0].equalsIgnoreCase("remove")) {
				int id = Integer.parseInt(args[1]);
				if(Potion.getPotionById(id) == null) {
					ChatUtils.error("Potion is null");
					return;
				}
				removeEffect(id);
			}
			else
			if(args[0].equalsIgnoreCase("clear")) {
				clearEffects();
			}
		}
		catch(Exception e)
		{
			ChatUtils.error("Usage: " + getSyntax());
		}
	}
	
	void addEffect(int id, int duration, int amplifier) {
		Wrapper.INSTANCE.player().addPotionEffect(new PotionEffect(Potion.getPotionById(id), duration, amplifier));
	}
	
	void removeEffect(int id) {
		Wrapper.INSTANCE.player().removePotionEffect(Potion.getPotionById(id));
	}
	
	void clearEffects() {
		for(PotionEffect effect : Wrapper.INSTANCE.player().getActivePotionEffects()) {
			Wrapper.INSTANCE.player().removePotionEffect(effect.getPotion());
		}
	}

	@Override
	public String getDescription()
	{
		return "Effect manager.";
	}

	@Override
	public String getSyntax()
	{
		return "effect <add/remove/clear> <id> <duration> <amplifier>";
	}
}