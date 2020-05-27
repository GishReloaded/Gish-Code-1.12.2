package i.gishreloaded.gishcode.hack.hacks;

import java.lang.reflect.Field;
import java.util.Random;

import i.gishreloaded.gishcode.hack.Hack;
import i.gishreloaded.gishcode.hack.HackCategory;
import i.gishreloaded.gishcode.utils.Utils;
import i.gishreloaded.gishcode.utils.system.Mapping;
import i.gishreloaded.gishcode.utils.system.Connection.Side;
import i.gishreloaded.gishcode.utils.visual.ChatUtils;

import i.gishreloaded.gishcode.utils.TimerUtils;
import i.gishreloaded.gishcode.value.BooleanValue;
import i.gishreloaded.gishcode.value.Mode;
import i.gishreloaded.gishcode.value.ModeValue;
import i.gishreloaded.gishcode.value.NumberValue;
import i.gishreloaded.gishcode.wrappers.Wrapper;
import io.netty.buffer.Unpooled;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.client.CPacketCustomPayload;
import net.minecraft.network.play.client.CPacketUseEntity;
import net.minecraft.network.play.server.SPacketEntityVelocity;
import net.minecraft.util.EnumHand;
import net.minecraft.util.Session;
import net.minecraftforge.client.event.EntityViewRenderEvent.CameraSetup;
import net.minecraftforge.fml.common.gameevent.TickEvent.ClientTickEvent;

public class Velocity extends Hack{
	
	public ModeValue mode;
	
	//public NumberValue percentage;
	
	public Velocity() {
		super("Velocity", HackCategory.COMBAT);
		
		this.mode = new ModeValue("Mode", new Mode("AAC", false), new Mode("Simple", true));
		
		this.addValue(mode);
	}
	
	@Override
	public String getDescription() {
		return "Prevents you from getting pushed by players, mobs and flowing water.";
	}
	
	@Override
	public void onClientTick(ClientTickEvent event) {
		if(mode.getMode("AAC").isToggled()) {
			EntityPlayerSP player = Wrapper.INSTANCE.player();
			if(player.hurtTime > 0 && player.hurtTime <= 7) {
				player.motionX *= 0.5;
						player.motionZ *= 0.5;
		      }
		      if(player.hurtTime > 0 && player.hurtTime < 6) {
		    	  player.motionX = 0.0;
		    			  player.motionZ = 0.0;
		      }
		}
		super.onClientTick(event);
	}
	
	@Override
	public boolean onPacket(Object packet, Side side) {
		if(packet instanceof SPacketEntityVelocity && mode.getMode("Simple").isToggled()) {
			SPacketEntityVelocity p = (SPacketEntityVelocity)packet;
			if(p.getEntityID() == Wrapper.INSTANCE.player().getEntityId()) {
				return false;
				/*
				double x = p.getMotionX() / 8000.0D;
			    double y = p.getMotionY() / 8000.0D;
			    double z = p.getMotionZ() / 8000.0D;  
			    double percent = this.percentage.getValue().intValue() / 100.0D;      
			    x *= percent;
			    y *= percent;
			    z *= percent;
			    if(this.percentage.getValue().intValue() > 0) {
			    	try {
						ChatUtils.warning("x" + x + " y" + y + " z" + z);
			        	Field mX = SPacketEntityVelocity.class.getDeclaredField("motionX");
			        	Field mY = SPacketEntityVelocity.class.getDeclaredField("motionY");
			        	Field mZ = SPacketEntityVelocity.class.getDeclaredField("motionZ");  	
			        	mX.setAccessible(true);
			        	mY.setAccessible(true);
			        	mZ.setAccessible(true);
			        	mX.set(p, (int)x);
			        	mY.set(p, (int)y);
			        	mZ.set(p, (int)z);
					    //return true;
					} catch (Exception e) {
						e.printStackTrace();
					}
								  
			    }
			    */
			}
		}
		return true;
	}
}
