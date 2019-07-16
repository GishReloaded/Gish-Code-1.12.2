package i.gishreloaded.gishcode.utils;

import com.mojang.authlib.GameProfile;

import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.entity.MoverType;
import net.minecraft.util.MovementInput;
import net.minecraft.world.World;

public class Entity301 extends EntityOtherPlayerMP {

	private static MovementInput movementInput = null;
	
    public Entity301(World worldIn, GameProfile gameProfileIn) {
		super(worldIn, gameProfileIn);
    }

    public void setMovementInput(MovementInput movementInput) {
    	Entity301.movementInput = movementInput;
        if (movementInput.jump && this.onGround) {
            super.jump();
        }
        super.moveRelative(movementInput.moveStrafe, this.moveVertical, movementInput.moveForward, this.movedDistance);
    }
    
    
    @Override
    public void move(MoverType type, double x, double y, double z) {
    	this.onGround = true;
    	super.move(type, x, y, z);
    	this.onGround = true;
    }

    @Override
    public boolean isSneaking() {
        return false;
    }

    @Override
    public void onLivingUpdate() {
        super.onLivingUpdate();
        this.noClip = true;
        this.motionX = 0.0;
        this.motionY = 0.0;
        this.motionZ = 0.0;
        this.noClip = false;
    }

}
