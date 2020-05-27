package i.gishreloaded.gishcode.hack.hacks;

import java.lang.reflect.Field;

import i.gishreloaded.gishcode.hack.Hack;
import i.gishreloaded.gishcode.hack.HackCategory;

import i.gishreloaded.gishcode.utils.system.Mapping;
import i.gishreloaded.gishcode.wrappers.Wrapper;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraftforge.fml.common.gameevent.TickEvent.ClientTickEvent;

public class Spider extends Hack{
	
	public Spider() {
		super("Spider", HackCategory.PLAYER);
	}
	
	@Override
	public String getDescription() {
		return "Allows you to climb up walls like a spider.";
	}
	
	@Override
	public void onClientTick(ClientTickEvent event) {
        if(!Wrapper.INSTANCE.player().isOnLadder() 
        		&& Wrapper.INSTANCE.player().collidedHorizontally 
        		&& Wrapper.INSTANCE.player().motionY < 0.2) {
        	Wrapper.INSTANCE.player().motionY = 0.2;
        }
		super.onClientTick(event);
	}
	
}
