package i.gishreloaded.gishcode.hack.hacks;

import i.gishreloaded.gishcode.hack.Hack;
import i.gishreloaded.gishcode.hack.HackCategory;
import i.gishreloaded.gishcode.utils.Utils;
import i.gishreloaded.gishcode.utils.system.Wrapper;
import i.gishreloaded.gishcode.utils.visual.ChatUtils;
import i.gishreloaded.gishcode.value.BooleanValue;
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

public class Step extends Hack{
	
	int ticks = 0;
	public Step() {
		super("Step", HackCategory.PLAYER);
	}
	
	@Override
	public void onEnable() {
		ticks = 0;
		super.onEnable();
	}
	
	@Override
	public void onDisable() {
		super.onDisable();
	}
	
	@Override
	public void onClientTick(ClientTickEvent event) {
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
		super.onClientTick(event);
	}
	
}
