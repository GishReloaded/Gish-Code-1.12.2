package i.gishreloaded.gishcode.hack.hacks;

import i.gishreloaded.gishcode.hack.Hack;
import i.gishreloaded.gishcode.hack.HackCategory;
import i.gishreloaded.gishcode.utils.system.Wrapper;
import net.minecraftforge.fml.common.gameevent.TickEvent.ClientTickEvent;

public class NoRain extends Hack{

	public NoRain() {
		super("NoRain", HackCategory.VISUAL);
	}
	
	@Override
	public void onClientTick(ClientTickEvent event) {
        Wrapper.INSTANCE.world().setRainStrength(0.0f);
		super.onClientTick(event);
	}

}
