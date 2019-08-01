package i.gishreloaded.gishcode.hack.hacks;

import java.lang.reflect.Field;

import i.gishreloaded.gishcode.hack.Hack;
import i.gishreloaded.gishcode.hack.HackCategory;
import i.gishreloaded.gishcode.utils.system.Mapping;
import i.gishreloaded.gishcode.utils.system.Wrapper;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.entity.Entity;
import net.minecraftforge.fml.common.gameevent.TickEvent.ClientTickEvent;

public class AntiWeb extends Hack{
	
	public AntiWeb() {
		super("AntiWeb", HackCategory.PLAYER);
	}
	
	@Override
	public void onClientTick(ClientTickEvent event) {
		try {
			Field isInWeb = Entity.class.getDeclaredField(Mapping.isInWeb);
			isInWeb.setAccessible(true);
			isInWeb.setBoolean(Wrapper.INSTANCE.player(), false);
		} catch (Exception ex) {
			this.setToggled(false);
		}
		super.onClientTick(event);
	}
	
}
