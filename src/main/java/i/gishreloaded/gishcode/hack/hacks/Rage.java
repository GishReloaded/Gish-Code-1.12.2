package i.gishreloaded.gishcode.hack.hacks;

import i.gishreloaded.gishcode.hack.Hack;
import i.gishreloaded.gishcode.hack.HackCategory;
import i.gishreloaded.gishcode.utils.RobotUtils;
import i.gishreloaded.gishcode.utils.TimerUtils;
import i.gishreloaded.gishcode.utils.Utils;
import i.gishreloaded.gishcode.value.Mode;
import i.gishreloaded.gishcode.value.ModeValue;
import i.gishreloaded.gishcode.value.NumberValue;
import i.gishreloaded.gishcode.wrappers.Wrapper;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraftforge.client.event.EntityViewRenderEvent.CameraSetup;
import net.minecraftforge.fml.common.gameevent.TickEvent.ClientTickEvent;
import i.gishreloaded.gishcode.utils.system.Connection.Side;

public class Rage extends Hack{
	
	public TimerUtils timer;
	
	public NumberValue delay;
	
	public Rage() {
		super("Rage", HackCategory.PLAYER);
		
		this.timer = new TimerUtils();
		delay = new NumberValue("Delay", 0.0D, 0.0D, 1000.0D);
		
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
