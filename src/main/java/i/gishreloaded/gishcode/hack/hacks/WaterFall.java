//package i.gishreloaded.gishcode.hack.hacks;
//
//import i.gishreloaded.gishcode.hack.Hack;
//import i.gishreloaded.gishcode.hack.HackCategory;
//import i.gishreloaded.gishcode.utils.BlockData;
//import i.gishreloaded.gishcode.utils.BlockUtils;
//
//import i.gishreloaded.gishcode.utils.RobotUtils;
//import i.gishreloaded.gishcode.utils.TimerUtils;
//import i.gishreloaded.gishcode.utils.Utils;
//import i.gishreloaded.gishcode.utils.visual.ChatUtils;
//import i.gishreloaded.gishcode.value.Mode;
//import i.gishreloaded.gishcode.value.ModeValue;
//import i.gishreloaded.gishcode.wrappers.Wrapper;
//import net.minecraft.block.Block;
//import net.minecraft.init.Blocks;
//import net.minecraft.init.Items;
//import net.minecraft.item.ItemBlock;
//import net.minecraft.item.ItemBucket;
//import net.minecraft.item.ItemStack;
//import net.minecraft.network.play.client.CPacketPlayer;
//import net.minecraft.util.EnumHand;
//import net.minecraft.util.math.BlockPos;
//import net.minecraft.util.math.RayTraceResult;
//import net.minecraftforge.fml.common.gameevent.TickEvent.ClientTickEvent;
//
//public class WaterFall extends Hack{
//	
//	public WaterFall() {
//		super("WaterFall", HackCategory.PLAYER);
//	}
//	
//	boolean isFalling() {
//		BlockPos blockPos = new BlockPos(Wrapper.INSTANCE.player()).down(); 
//		return (!Wrapper.INSTANCE.player().onGround && Wrapper.INSTANCE.player().fallDistance > 2 && BlockUtils.getBlock(blockPos) != Blocks.AIR 
//				|| BlockUtils.getBlock(blockPos.down()) != Blocks.AIR  || BlockUtils.getBlock(blockPos.down().down()) != Blocks.AIR);
//	}
//	
//	public void switchAndPut() {
//		int newSlot = -1;
//        for (int i = 0; i < 9; ++i) {
//            ItemStack stack = Wrapper.INSTANCE.inventory().getStackInSlot(i);
//            if(stack != null && stack.getItem() == Items.WATER_BUCKET) { newSlot = i; break; }
//        }
//        if (newSlot == -1) return;
//        Wrapper.INSTANCE.player().rotationPitch = Utils.updateRotation(120F, Wrapper.INSTANCE.player().rotationYaw, 30.0F);
//        Wrapper.INSTANCE.inventory().currentItem = newSlot;
//        RobotUtils.clickMouse(1);
//		Wrapper.INSTANCE.swingArm();
//	}
//	
//	@Override
//	public void onClientTick(ClientTickEvent event) {
//		if(!isFalling() || !Utils.screenCheck()) return; 
//		switchAndPut();
//		super.onClientTick(event);
//	}
//}
