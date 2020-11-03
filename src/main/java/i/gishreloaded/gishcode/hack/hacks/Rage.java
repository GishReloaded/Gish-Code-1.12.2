package i.gishreloaded.gishcode.hack.hacks;

import i.gishreloaded.gishcode.hack.Hack;
import i.gishreloaded.gishcode.hack.HackCategory;
import i.gishreloaded.gishcode.utils.TimerUtils;
import i.gishreloaded.gishcode.utils.Utils;
import i.gishreloaded.gishcode.value.types.IntegerValue;
import i.gishreloaded.gishcode.wrappers.Wrapper;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraftforge.fml.common.gameevent.TickEvent.ClientTickEvent;

public class Rage extends Hack{
	
	public TimerUtils timer;
	
	public IntegerValue delay;
	
	public Rage() {
		super("Rage", HackCategory.PLAYER);
		
		this.timer = new TimerUtils();
		delay = new IntegerValue("Delay", 0, 0, 1000);
		
		this.addValue(delay);
	}
	
	@Override
	public void onClientTick(ClientTickEvent event) {
		if(timer.isDelay(delay.getValue().longValue())) {
			Wrapper.INSTANCE.sendPacket(new CPacketPlayer.Rotation(Utils.random(-160, 160), Utils.random(-160, 160), true));
			timer.setLastMS();
		}
		super.onClientTick(event);
	}
	
//	@Override // TODO Added camera fix
//	public void onCameraSetup(CameraSetup event) {
//		// TODO Auto-generated method stub
//		super.onCameraSetup(event);
//	}
}
