package i.gishreloaded.gishcode.hack.hacks;

import i.gishreloaded.gishcode.hack.Hack;
import i.gishreloaded.gishcode.hack.HackCategory;

import i.gishreloaded.gishcode.utils.Utils;
import i.gishreloaded.gishcode.utils.visual.ChatUtils;
import i.gishreloaded.gishcode.value.BooleanValue;
import i.gishreloaded.gishcode.value.Mode;
import i.gishreloaded.gishcode.value.ModeValue;
import i.gishreloaded.gishcode.value.NumberValue;
import i.gishreloaded.gishcode.wrappers.Wrapper;
import net.minecraft.block.BlockAir;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.event.entity.ProjectileImpactEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.ClientTickEvent;

public class AutoStep extends Hack{

	
	public ModeValue mode;
	public NumberValue height;
	public float tempHeight;
	public int ticks = 0;
	
	public AutoStep() {
		super("AutoStep", HackCategory.PLAYER);
		
		this.mode = new ModeValue("Mode", new Mode("Simple", true), new Mode("AAC", false));
		height = new NumberValue("Height", 0.5D, 0D, 10D);
		
		this.addValue(mode, height);
	}
	
	@Override
    public String getDescription() {
        return "Allows you to walk on value blocks height.";
    }
	
	@Override
	public void onEnable() {
		ticks = 0;
		super.onEnable();
	}
	
	@Override
	public void onDisable() {
		Wrapper.INSTANCE.player().stepHeight = 0.5f;
		super.onDisable();
	}
	
	@Override
	public void onClientTick(ClientTickEvent event) {
		if(mode.getMode("AAC").isToggled()) {
			EntityPlayerSP player = Wrapper.INSTANCE.player();
			if(player.collidedHorizontally) {
				switch(ticks) {
					case 0:
					if(player.onGround)
						player.jump();
						break;
					case 7:
						player.motionY = 0;
						break;
					case 8:
					if(!player.onGround)
						player.setPosition(player.posX, player.posY + 1, player.posZ);
						break;
				}
				ticks++;
			} else {
				ticks = 0;
			}
		} else if(mode.getMode("Simple").isToggled()) {
			Wrapper.INSTANCE.player().stepHeight = height.getValue().floatValue();
		}
		
		super.onClientTick(event);
	}
	
}
