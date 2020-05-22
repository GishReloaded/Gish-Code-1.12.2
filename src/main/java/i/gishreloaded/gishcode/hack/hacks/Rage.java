package i.gishreloaded.gishcode.hack.hacks;

import i.gishreloaded.gishcode.hack.Hack;
import i.gishreloaded.gishcode.hack.HackCategory;
import i.gishreloaded.gishcode.utils.Utils;
import i.gishreloaded.gishcode.value.Mode;
import i.gishreloaded.gishcode.value.ModeValue;
import i.gishreloaded.gishcode.wrappers.Wrapper;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraftforge.client.event.EntityViewRenderEvent.CameraSetup;
import net.minecraftforge.fml.common.gameevent.TickEvent.ClientTickEvent;
import i.gishreloaded.gishcode.utils.system.Connection.Side;

public class Rage extends Hack{
	
	public Rage() {
		super("Rage", HackCategory.PLAYER);
	}
	
	@Override
	public void onClientTick(ClientTickEvent event) {
		Wrapper.INSTANCE.sendPacket(new CPacketPlayer.Rotation(Utils.random(-160, 160), Utils.random(-160, 160), true));
		super.onClientTick(event);
	}
}
