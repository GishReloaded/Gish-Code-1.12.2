package i.gishreloaded.gishcode.hack.hacks;

import i.gishreloaded.gishcode.hack.Hack;
import i.gishreloaded.gishcode.hack.HackCategory;
import i.gishreloaded.gishcode.utils.BlockData;
import i.gishreloaded.gishcode.utils.system.Wrapper;
import net.minecraft.init.Blocks;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.gameevent.TickEvent.ClientTickEvent;

public class AntiFall extends Hack{

	public AntiFall() {
		super("AntiFall", HackCategory.PLAYER);
	}
	
	@Override
	public void onClientTick(ClientTickEvent event) {
		if(Wrapper.INSTANCE.player().fallDistance > 2) {
			Wrapper.INSTANCE.sendPacket(new CPacketPlayer(true));
    	}
		super.onClientTick(event);
	}
}
