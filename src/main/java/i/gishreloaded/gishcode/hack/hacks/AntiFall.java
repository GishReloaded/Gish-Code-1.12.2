package i.gishreloaded.gishcode.hack.hacks;

import i.gishreloaded.gishcode.hack.Hack;
import i.gishreloaded.gishcode.hack.HackCategory;

import i.gishreloaded.gishcode.wrappers.Wrapper;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraftforge.fml.common.gameevent.TickEvent.ClientTickEvent;

public class AntiFall extends Hack{

	public AntiFall() {
		super("AntiFall", HackCategory.PLAYER);
	}
	
	@Override
	public String getDescription() {
		return "Gives you zero damage on fall.";
	}
	
	@Override
	public void onClientTick(ClientTickEvent event) { 
		if(Wrapper.INSTANCE.player().fallDistance > 2) 
			Wrapper.INSTANCE.sendPacket(new CPacketPlayer(true)); 
		super.onClientTick(event); 
	}
}
