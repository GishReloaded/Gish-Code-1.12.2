package i.gishreloaded.gishcode.hack.hacks;

import java.lang.reflect.Field;

import i.gishreloaded.gishcode.hack.Hack;
import i.gishreloaded.gishcode.hack.HackCategory;
import i.gishreloaded.gishcode.utils.BlockUtils;
import i.gishreloaded.gishcode.utils.PlayerControllerUtils;
import i.gishreloaded.gishcode.utils.system.Mapping;
import i.gishreloaded.gishcode.wrappers.Wrapper;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.network.play.client.CPacketPlayerDigging;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.LeftClickBlock;
import net.minecraftforge.fml.common.gameevent.TickEvent.ClientTickEvent;

public class FastBreak extends Hack{
	
	public FastBreak() {
		super("FastBreak", HackCategory.PLAYER);
	}
	
	@Override
	public String getDescription() {
		return "Allows you to break blocks faster.";
	}
	
	@Override
	public void onClientTick(ClientTickEvent event) {
		PlayerControllerUtils.setBlockHitDelay(0);
		super.onClientTick(event);
	}
	
    @Override
    public void onLeftClickBlock(LeftClickBlock event) {
    	float progress = PlayerControllerUtils.getCurBlockDamageMP() + BlockUtils.getHardness(event.getPos());	
    	if(progress >= 1) return;
    	Wrapper.INSTANCE.sendPacket(new CPacketPlayerDigging(
    			CPacketPlayerDigging.Action.STOP_DESTROY_BLOCK, event.getPos(),
    			Wrapper.INSTANCE.mc().objectMouseOver.sideHit));
    	super.onLeftClickBlock(event);
    }
	
}
