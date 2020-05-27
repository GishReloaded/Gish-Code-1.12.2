package i.gishreloaded.gishcode.hack.hacks;

import org.lwjgl.input.Keyboard;

import i.gishreloaded.gishcode.hack.Hack;
import i.gishreloaded.gishcode.hack.HackCategory;

import i.gishreloaded.gishcode.utils.Utils;
import i.gishreloaded.gishcode.value.BooleanValue;
import i.gishreloaded.gishcode.value.Mode;
import i.gishreloaded.gishcode.value.ModeValue;
import i.gishreloaded.gishcode.wrappers.Wrapper;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraftforge.fml.common.gameevent.TickEvent.ClientTickEvent;

public class Flight extends Hack{

	public ModeValue mode;
	int ticks = 0;
	public Flight() {
		super("Flight", HackCategory.PLAYER);
		
		this.mode = new ModeValue("Mode", new Mode("Simple", true), new Mode("Dynamic", false), new Mode("Hypixel", false));
		
		this.addValue(mode);
	}
	
	@Override
	public String getDescription() {
		return "Allows you to you fly.";
	}
	
	@Override
	public void onEnable() {
		ticks = 0;
		super.onEnable();
	}
	
	@Override
	public void onClientTick(ClientTickEvent event) {
		EntityPlayerSP player = Wrapper.INSTANCE.player();
		if(mode.getMode("Hypixel").isToggled()) {
			player.motionY = 0.0;
			player.setSprinting(true);
			player.onGround = true;
		    ticks++;
		    if(ticks == 2 || ticks == 4 || ticks == 6 || ticks == 8 || ticks == 10 || ticks == 12 || ticks == 14 || ticks == 16 || ticks == 18 || ticks == 20) {
		    player.setPosition(player.posX, player.posY + 0.00000000128, player.posZ);
		    } if(ticks == 20) {
		    	ticks = 0;
		    }
		}
		else if(mode.getMode("Simple").isToggled())
		{
			player.capabilities.isFlying = true;
		}
		else if(mode.getMode("Dynamic").isToggled())
		{
			float flyspeed = 1.0f;
			player.jumpMovementFactor = 0.4f;
			player.motionX = 0.0;
			player.motionY = 0.0;
			player.motionZ = 0.0;
			player.jumpMovementFactor *= (float) flyspeed * 3f;
	        if (Wrapper.INSTANCE.mcSettings().keyBindJump.isKeyDown()) {
	        	player.motionY += flyspeed;
	        }
	        if (Wrapper.INSTANCE.mcSettings().keyBindSneak.isKeyDown()) {
	        	player.motionY -= flyspeed;
	        }
		}
		super.onClientTick(event);
	}
	
	@Override
	public void onDisable() {
		if(mode.getMode("Simple").isToggled()) {
			Wrapper.INSTANCE.player().capabilities.isFlying = false;
		}
		super.onDisable();
	}
}
