package i.gishreloaded.gishcode.hack.hacks;

import i.gishreloaded.gishcode.Wrapper;
import i.gishreloaded.gishcode.Connection.Side;
import i.gishreloaded.gishcode.hack.Hack;
import i.gishreloaded.gishcode.hack.HackCategory;
import i.gishreloaded.gishcode.managers.HackManager;
import i.gishreloaded.gishcode.utils.ChatUtils;
import i.gishreloaded.gishcode.utils.Utils;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.init.Blocks;
import net.minecraft.network.play.client.CPacketEntityAction;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.gameevent.TickEvent.ClientTickEvent;

public class TestHack extends Hack{
	
	//int ticks = 0;
	public TestHack() {
		super("TestHack", HackCategory.OTHER);
	}
	
	@Override
	public void onClientTick(ClientTickEvent event) {
		//boolean boost = Math.abs(Wrapper.INSTANCE.player().rotationYawHead - Wrapper.INSTANCE.player().rotationYaw) < 90;
		//EntityPlayerSP player = Wrapper.INSTANCE.player();
		
		//ticks++;
		//KeyBinding.setKeyBindState(Wrapper.INSTANCE.mcSettings().keyBindSneak.getKeyCode(), true);
		//player.setSneaking(true);
			//player.motionX *= 1.25;
			//player.motionZ *= 1.25;
			
			//player.motionX *= 1.2848;
			//player.motionZ *= 1.2848;
			//if(ticks == 2 || ticks == 4 || ticks == 6 || ticks == 8 || ticks == 10 || ticks == 12 || ticks == 14 || ticks == 16 || ticks == 18 || ticks == 20) {
				//player.setSneaking(false);
			//KeyBinding.setKeyBindState(Wrapper.INSTANCE.mcSettings().keyBindSneak.getKeyCode(), false);
			//}
			//player.setPosition(player.posX * 1.2, player.posY, player.posZ * 1.2);
		//}
	    //if(ticks >= 20) {
	    //	ticks = 0;
	    //}
	
		//player.motionY = 0.0;
		//player.setSprinting(true);
		//player.onGround = true;
		//player.motionX *= 1.0;
		//player.motionZ *= 1.0;
		//if(player.isSneaking()) {
		//	player.motionX *= 1.2;
		//	player.motionZ *= 1.2;
		//}
		//if(player.motionY != 0.0) {
		//	player.motionY += 15.0F;
		//}
		//player.jump();
		//player.addExhaustion(1.2F);
		//ticks++;
	   // if(ticks == 2 || ticks == 4 || ticks == 6 || ticks == 8 || ticks == 10 || ticks == 12 || ticks == 14 || ticks == 16 || ticks == 18 || ticks == 20) {
			//player.motionX *= 1.0192843;
			//player.motionZ *= 1.0192843;
	    //} if(ticks == 20) {
	    //	ticks = 0;
	    //}
		super.onClientTick(event);
	}
}
