package i.gishreloaded.gishcode.hack.hacks;

import i.gishreloaded.gishcode.hack.Hack;
import i.gishreloaded.gishcode.hack.HackCategory;

import i.gishreloaded.gishcode.utils.Utils;
import i.gishreloaded.gishcode.value.Mode;
import i.gishreloaded.gishcode.value.ModeValue;
import i.gishreloaded.gishcode.wrappers.Wrapper;
import net.minecraft.init.MobEffects;
import net.minecraftforge.fml.common.gameevent.TickEvent.ClientTickEvent;

public class AutoSwim extends Hack{

	public ModeValue mode;
	public AutoSwim() {
		super("AutoSwim", HackCategory.PLAYER);
		
		this.mode = new ModeValue("Mode", new Mode("Jump", true), new Mode("Dolphin", false), new Mode("Fish", false));
		this.addValue(mode);
	}
	
	@Override
    public String getDescription() {
        return "Jumps automatically when you in water.";
    }
	
	@Override
	public void onClientTick(ClientTickEvent event) {
		if(!Wrapper.INSTANCE.player().isInWater() && !Wrapper.INSTANCE.player().isInLava()) {
			return;
		}
		if(Wrapper.INSTANCE.player().isSneaking() || Wrapper.INSTANCE.mcSettings().keyBindJump.isKeyDown()) {
			return;
		}
		if(mode.getMode("Jump").isToggled()) {
			Wrapper.INSTANCE.player().jump();
		} 
		else if(mode.getMode("Dolphin").isToggled()) {
			Wrapper.INSTANCE.player().motionY += 0.04f;
		} 
		else if(mode.getMode("Fish").isToggled()) {
			Wrapper.INSTANCE.player().motionY += 0.02f;
		}
		super.onClientTick(event);
	}
}
