package i.gishreloaded.gishcode.hack.hacks;

import java.util.Random;

import i.gishreloaded.gishcode.hack.Hack;
import i.gishreloaded.gishcode.hack.HackCategory;

import i.gishreloaded.gishcode.wrappers.Wrapper;
import net.minecraft.network.play.client.CPacketEntityAction;
import net.minecraft.network.play.client.CPacketEntityAction.Action;
import net.minecraftforge.fml.common.gameevent.TickEvent.ClientTickEvent;

public class FastLadder extends Hack{
    
	public FastLadder() {
		super("FastLadder", HackCategory.PLAYER);
	}
	
	@Override
	public String getDescription() {
		return "Allows you to climb up ladders faster.";
	}
    
	@Override
	public void onClientTick(ClientTickEvent event) {
		if(!Wrapper.INSTANCE.player().isOnLadder() || Wrapper.INSTANCE.player().moveForward == 0 && Wrapper.INSTANCE.player().moveStrafing == 0) return;
		Wrapper.INSTANCE.player().motionY = 0.169;
		super.onClientTick(event);
	}
	
}
