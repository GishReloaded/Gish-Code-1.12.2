package i.gishreloaded.gishcode.hack.hacks;

import i.gishreloaded.gishcode.hack.Hack;
import i.gishreloaded.gishcode.hack.HackCategory;
import i.gishreloaded.gishcode.utils.TimerUtils;
import i.gishreloaded.gishcode.value.types.DoubleValue;
import i.gishreloaded.gishcode.wrappers.Wrapper;
import net.minecraftforge.fml.common.gameevent.TickEvent.ClientTickEvent;

public class AntiAfk extends Hack{
	
	public DoubleValue delay;
	public TimerUtils timer;
	
	public AntiAfk() {
		super("AntiAfk", HackCategory.ANOTHER);
		
		this.timer = new TimerUtils();
		this.delay = new DoubleValue("DelaySec", 10.0D, 1.0D, 100.0D);
		
		this.addValue(delay);
	}
	
	@Override
	public String getDescription() {
		return "Prevents from being kicked for AFK.";
	}
	
	@Override
	public void onClientTick(ClientTickEvent event) { 
		if(timer.isDelay((long)(1000 * delay.getValue().longValue()))) {
			Wrapper.INSTANCE.player().jump();
			timer.setLastMS();
		}
		super.onClientTick(event); 
	}
}
