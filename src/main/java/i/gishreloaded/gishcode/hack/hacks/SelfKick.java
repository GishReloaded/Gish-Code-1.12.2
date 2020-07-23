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

public class SelfKick extends Hack{
	
	public SelfKick() {
		super("SelfKick", HackCategory.ANOTHER);
	}
	
	@Override
	public String getDescription() {
		return "Kick you from Server.";
	}
	
	@Override
	public void onEnable() {
		Wrapper.INSTANCE.sendPacket(new CPacketPlayer.Rotation(Float.NEGATIVE_INFINITY, Float.NEGATIVE_INFINITY, false));
		super.onEnable();
	}
}
