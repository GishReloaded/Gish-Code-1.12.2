package i.gishreloaded.gishcode.hack.hacks;

import i.gishreloaded.gishcode.Wrapper;
import i.gishreloaded.gishcode.hack.Hack;
import i.gishreloaded.gishcode.hack.HackCategory;
import i.gishreloaded.gishcode.managers.HackManager;
import i.gishreloaded.gishcode.value.BooleanValue;
import net.minecraftforge.fml.common.gameevent.TickEvent.ClientTickEvent;

public class DisableAtDeath extends Hack{

	public BooleanValue killauraAAC;
	public BooleanValue killaura;
	public BooleanValue aimbot;
	public BooleanValue trigger;
	 
	public DisableAtDeath() {
		super("DisableAtDeath", HackCategory.OTHER);
		killauraAAC = new BooleanValue("KillAuraAAC", true);
		killaura = new BooleanValue("KillAura", true);
		aimbot = new BooleanValue("Aimbot", true);
		trigger = new BooleanValue("Trigger", true);
		
		this.addValue(killauraAAC, killaura, aimbot, trigger);
	}
	
	@Override
	public void onClientTick(ClientTickEvent event) {
		if(!Wrapper.INSTANCE.player().isEntityAlive() || Wrapper.INSTANCE.player().isSpectator()) {
			if(killauraAAC.getValue()) {
				Hack hack = HackManager.getHack("KillAuraAAC");
				if(hack.isToggled()) {
					hack.setToggled(false);
				}
			}
			if(killaura.getValue()) {
				Hack hack = HackManager.getHack("KillAura");
				if(hack.isToggled()) {
					hack.setToggled(false);
				}
			}
			if(aimbot.getValue()) {
				Hack hack = HackManager.getHack("Aimbot");
				if(hack.isToggled()) {
					hack.setToggled(false);
				}
			}
			if(trigger.getValue()) {
				Hack hack = HackManager.getHack("Trigger");
				if(hack.isToggled()) {
					hack.setToggled(false);
				}
			}
		}
		super.onClientTick(event);
	}
	
	
}
