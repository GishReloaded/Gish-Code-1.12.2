package i.gishreloaded.gishcode.hack.hacks;

import i.gishreloaded.gishcode.hack.Hack;
import i.gishreloaded.gishcode.hack.HackCategory;
import i.gishreloaded.gishcode.utils.BlockUtils;
import i.gishreloaded.gishcode.utils.TimerUtils;
import i.gishreloaded.gishcode.utils.Utils;
import i.gishreloaded.gishcode.value.BooleanValue;
import i.gishreloaded.gishcode.value.NumberValue;
import i.gishreloaded.gishcode.wrappers.Wrapper;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.BlockWorkbench;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.gameevent.TickEvent.ClientTickEvent;

public class Parkour extends Hack{
	
	public Parkour() {
		super("Parkour", HackCategory.PLAYER);
	}
	
	@Override
	public String getDescription() {
		return "Jump when reaching a block's edge.";
	}
	
	@Override
	public void onClientTick(ClientTickEvent event) {
		if(Utils.isBlockEdge(Wrapper.INSTANCE.player()) 
				&& !Wrapper.INSTANCE.player().isSneaking()) 
			Wrapper.INSTANCE.player().jump();
		super.onClientTick(event);
	}
	
}
