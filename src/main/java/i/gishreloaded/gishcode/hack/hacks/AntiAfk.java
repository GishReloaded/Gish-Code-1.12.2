package i.gishreloaded.gishcode.hack.hacks;

import i.gishreloaded.gishcode.hack.Hack;
import i.gishreloaded.gishcode.hack.HackCategory;
import i.gishreloaded.gishcode.utils.TimerUtils;
import i.gishreloaded.gishcode.utils.Utils;
import i.gishreloaded.gishcode.value.BooleanValue;
import i.gishreloaded.gishcode.value.NumberValue;
import i.gishreloaded.gishcode.wrappers.Wrapper;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraftforge.fml.common.gameevent.TickEvent.ClientTickEvent;

public class AntiAfk extends Hack{
	
	public NumberValue delay;
	public TimerUtils timer;
	
	public AntiAfk() {
		super("AntiAfk", HackCategory.ANOTHER);
		
		this.timer = new TimerUtils();
		delay = new NumberValue("DelaySec", 10.0D, 1.0D, 100.0D);
		
		this.addValue(delay);
	}
	
	@Override
	public String getDescription() {
		return "Prevents from being kicked for AFK.";
	}
	
	@Override
	public void onClientTick(ClientTickEvent event) { 
		if(timer.isDelay((long)(1000 * delay.getValue()))) {
			Wrapper.INSTANCE.player().jump();
			timer.setLastMS();
		}
		super.onClientTick(event); 
	}
}
