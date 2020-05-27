package i.gishreloaded.gishcode.hack.hacks;

import i.gishreloaded.gishcode.hack.Hack;
import i.gishreloaded.gishcode.hack.HackCategory;
import i.gishreloaded.gishcode.utils.Entity301;

import i.gishreloaded.gishcode.utils.system.Connection.Side;
import i.gishreloaded.gishcode.wrappers.Wrapper;
import net.minecraft.entity.Entity;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.world.World;

public class FreeCam extends Hack{

	public Entity301 entity301 = null;
	
	public FreeCam() {
		super("FreeCam", HackCategory.VISUAL);
	}
	
	@Override
	public String getDescription() {
		return "Allows you to move the camera without moving your character.";
	}
	
	@Override
	public boolean onPacket(Object packet, Side side) {
		return !(side == i.gishreloaded.gishcode.utils.system.Connection.Side.OUT && (packet instanceof CPacketPlayer
                || packet instanceof CPacketPlayer.Position
                || packet instanceof CPacketPlayer.Rotation
                || packet instanceof CPacketPlayer.PositionRotation));
	}
	
	@Override
	public void onEnable() {
		if (Wrapper.INSTANCE.player() != null && Wrapper.INSTANCE.world() != null) {
            this.entity301 = new Entity301(Wrapper.INSTANCE.world(), Wrapper.INSTANCE.player().getGameProfile());
            this.entity301.setPosition(Wrapper.INSTANCE.player().posX, Wrapper.INSTANCE.player().posY, Wrapper.INSTANCE.player().posZ);
            this.entity301.inventory = Wrapper.INSTANCE.inventory();
            //this.entity301.yOffset = Wrapper.INSTANCE.player().yOffset;
            //this.entity301.ySize = Wrapper.INSTANCE.player().ySize;
            this.entity301.rotationPitch = Wrapper.INSTANCE.player().rotationPitch;
            this.entity301.rotationYaw = Wrapper.INSTANCE.player().rotationYaw;
            this.entity301.rotationYawHead = Wrapper.INSTANCE.player().rotationYawHead;
            Wrapper.INSTANCE.world().spawnEntity(this.entity301);
        }
		super.onEnable();
	}
	
	@Override
	public void onDisable() {
		if (this.entity301 != null && Wrapper.INSTANCE.world() != null) {
            Wrapper.INSTANCE.player().setPosition(this.entity301.posX, this.entity301.posY, this.entity301.posZ);
            //Wrapper.INSTANCE.player().yOffset = this.entity301.yOffset;
            //Wrapper.INSTANCE.player().ySize = this.entity301.ySize;
            Wrapper.INSTANCE.player().rotationPitch = this.entity301.rotationPitch;
            Wrapper.INSTANCE.player().rotationYaw = this.entity301.rotationYaw;
            Wrapper.INSTANCE.player().rotationYawHead = this.entity301.rotationYawHead;
            Wrapper.INSTANCE.world().removeEntity(this.entity301);
            this.entity301 = null;
        }
		super.onDisable();
	}

}
