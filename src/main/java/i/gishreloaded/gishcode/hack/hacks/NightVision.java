package i.gishreloaded.gishcode.hack.hacks;

import i.gishreloaded.gishcode.hack.Hack;
import i.gishreloaded.gishcode.hack.HackCategory;
import i.gishreloaded.gishcode.wrappers.Wrapper;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.gameevent.TickEvent.ClientTickEvent;

public class NightVision extends Hack{

	public NightVision() {
		super("NightVision", HackCategory.VISUAL);
	}
	
	@Override
	public String getDescription() {
		return "Gets you night vision.";
	}
	
	@Override
	public void onEnable() {
		super.onEnable();
	}
	@Override
	public void onDisable() {
		Wrapper.INSTANCE.mcSettings().gammaSetting = 1;
		super.onDisable();
	}
	
	@Override
	public void onClientTick(ClientTickEvent event) {
		Wrapper.INSTANCE.mcSettings().gammaSetting = 5;
		super.onClientTick(event);
	}
}
