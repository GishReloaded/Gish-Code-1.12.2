package i.gishreloaded.gishcode.hack.hacks;

import java.io.IOException;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import i.gishreloaded.gishcode.gui.click.ClickGuiScreen;
import i.gishreloaded.gishcode.hack.Hack;
import i.gishreloaded.gishcode.hack.HackCategory;
import i.gishreloaded.gishcode.managers.EnemyManager;
import i.gishreloaded.gishcode.managers.FriendManager;
import i.gishreloaded.gishcode.managers.HackManager;
import i.gishreloaded.gishcode.managers.XRayManager;

import i.gishreloaded.gishcode.utils.Utils;
import i.gishreloaded.gishcode.wrappers.Wrapper;
import net.minecraft.block.Block;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.PlayerCapabilities;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.ClientTickEvent;

public class GuiWalk extends Hack{

	public GuiWalk() {
		super("GuiWalk", HackCategory.PLAYER);
	}
	
	@Override
	public String getDescription() {
		return "Allows you to walk while the gui is open.";
	}
	
	@Override
	public void onClientTick(ClientTickEvent event) {
		if (!(Wrapper.INSTANCE.mc().currentScreen instanceof GuiContainer) 
				&& !(Wrapper.INSTANCE.mc().currentScreen instanceof ClickGuiScreen))
			return;
		
		double speed = 0.05;
		
		if(!Wrapper.INSTANCE.player().onGround)
			speed /= 4.0;
		
		handleJump();
		handleForward(speed);
		
		if(!Wrapper.INSTANCE.player().onGround)
			speed /= 2.0;
		
		handleBack(speed);
		handleLeft(speed);
		handleRight(speed);
		
		super.onClientTick(event);
	}
	
	void moveForward(double speed) {
        float direction = Utils.getDirection();
        Wrapper.INSTANCE.player().motionX -= (double)(MathHelper.sin(direction) * speed);
        Wrapper.INSTANCE.player().motionZ += (double)(MathHelper.cos(direction) * speed);
	}
	
	void moveBack(double speed) {
        float direction = Utils.getDirection();
        Wrapper.INSTANCE.player().motionX += (double)(MathHelper.sin(direction) * speed);
        Wrapper.INSTANCE.player().motionZ -= (double)(MathHelper.cos(direction) * speed);
	}
	
	void moveLeft(double speed) {
        float direction = Utils.getDirection();
        Wrapper.INSTANCE.player().motionZ += (double)(MathHelper.sin(direction) * speed);
        Wrapper.INSTANCE.player().motionX += (double)(MathHelper.cos(direction) * speed);
	}
	
	void moveRight(double speed) {
        float direction = Utils.getDirection();
        Wrapper.INSTANCE.player().motionZ -= (double)(MathHelper.sin(direction) * speed);
        Wrapper.INSTANCE.player().motionX -= (double)(MathHelper.cos(direction) * speed);
	}
	
	void handleForward(double speed) {
		if(!Keyboard.isKeyDown(Wrapper.INSTANCE.mcSettings().keyBindForward.getKeyCode())) 
			return;
		moveForward(speed);
	}
	
	void handleBack(double speed) {
		if(!Keyboard.isKeyDown(Wrapper.INSTANCE.mcSettings().keyBindBack.getKeyCode())) 
			return;
		moveBack(speed);
	}
	
	void handleLeft(double speed) {
		if(!Keyboard.isKeyDown(Wrapper.INSTANCE.mcSettings().keyBindLeft.getKeyCode())) 
			return;
		moveLeft(speed);
	}
	
	void handleRight(double speed) {
		if(!Keyboard.isKeyDown(Wrapper.INSTANCE.mcSettings().keyBindRight.getKeyCode())) 
			return;
		moveRight(speed);
	}
	
	void handleJump() {
		if(Wrapper.INSTANCE.player().onGround && 
    			Keyboard.isKeyDown(Wrapper.INSTANCE.mcSettings().keyBindJump.getKeyCode())) 
    		Wrapper.INSTANCE.player().jump();
	}

}
