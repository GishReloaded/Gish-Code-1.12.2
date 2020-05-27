package i.gishreloaded.gishcode.hack.hacks;

import i.gishreloaded.gishcode.hack.Hack;
import i.gishreloaded.gishcode.hack.HackCategory;

import i.gishreloaded.gishcode.utils.system.Connection;
import i.gishreloaded.gishcode.utils.system.Connection.Side;
import i.gishreloaded.gishcode.value.BooleanValue;
import i.gishreloaded.gishcode.value.NumberValue;
import i.gishreloaded.gishcode.wrappers.Wrapper;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.ClientTickEvent;

public class Ghost extends Hack{
	
	public BooleanValue noWalls;
	
	public Ghost() {
		super("Ghost", HackCategory.PLAYER);
		
		noWalls = new BooleanValue("NoWalls", true);
		
		this.addValue(noWalls);
	}
	
	@Override
	public String getDescription() {
		return "Allows you to pass through walls.";
	}
	
	@Override
	public boolean onPacket(Object packet, Side side) {
		boolean skip = true;
		if(noWalls.getValue()) {
			if(side == Connection.Side.OUT && packet instanceof CPacketPlayer) {
				skip = false;
			}
		}
		return skip;
	}
	
	@Override
	public void onDisable() {
		Wrapper.INSTANCE.player().noClip = false;
		
		if(noWalls.getValue()) {
		Wrapper.INSTANCE.sendPacket(new CPacketPlayer.PositionRotation(
				Wrapper.INSTANCE.player().posX, Wrapper.INSTANCE.player().getEntityBoundingBox().minY,
				Wrapper.INSTANCE.player().posZ, Wrapper.INSTANCE.player().cameraYaw, Wrapper.INSTANCE.player().cameraPitch,
				Wrapper.INSTANCE.player().onGround));
		}
		
		super.onDisable();
	}

	@Override
	public void onLivingUpdate(LivingUpdateEvent event) {
		Wrapper.INSTANCE.player().noClip = true;
		Wrapper.INSTANCE.player().fallDistance = 0;
		Wrapper.INSTANCE.player().onGround = true;
		
		Wrapper.INSTANCE.player().capabilities.isFlying = false;
		Wrapper.INSTANCE.player().motionX = 0;
		Wrapper.INSTANCE.player().motionY = 0;
		Wrapper.INSTANCE.player().motionZ = 0;
		
		float speed = 0.2f;
		Wrapper.INSTANCE.player().jumpMovementFactor = speed;
		if(Wrapper.INSTANCE.mcSettings().keyBindJump.isKeyDown()) {
			Wrapper.INSTANCE.player().motionY += speed;
		}
		if(Wrapper.INSTANCE.mcSettings().keyBindSneak.isKeyDown()) {
			Wrapper.INSTANCE.player().motionY -= speed;
		}
		
		super.onLivingUpdate(event);
	}
}
