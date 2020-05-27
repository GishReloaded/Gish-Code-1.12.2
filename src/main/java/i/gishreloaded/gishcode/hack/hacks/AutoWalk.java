package i.gishreloaded.gishcode.hack.hacks;

import java.lang.reflect.Field;

import i.gishreloaded.gishcode.hack.Hack;
import i.gishreloaded.gishcode.hack.HackCategory;
import i.gishreloaded.gishcode.utils.system.Mapping;
import i.gishreloaded.gishcode.wrappers.Wrapper;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.Entity;
import net.minecraftforge.fml.common.gameevent.TickEvent.ClientTickEvent;

public class AutoWalk extends Hack{
	
	public AutoWalk() {
		super("AutoWalk", HackCategory.PLAYER);
	}
	
	@Override
	public String getDescription() {
		return "Automatic walking.";
	}
	
	@Override
	public void onDisable() {
		KeyBinding.setKeyBindState(Wrapper.INSTANCE.mcSettings().keyBindForward.getKeyCode(), false);
		super.onDisable();
	}
	
	@Override
	public void onClientTick(ClientTickEvent event) {
		KeyBinding.setKeyBindState(Wrapper.INSTANCE.mcSettings().keyBindForward.getKeyCode(), true);
		super.onClientTick(event);
	}
	
}
