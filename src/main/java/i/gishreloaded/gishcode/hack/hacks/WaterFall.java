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
//import net.minecraft.item.Item;
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
//	private int delay;
//	
//	public WaterFall() {
//		super("WaterFall", HackCategory.PLAYER);
//	}
//	
//	private boolean hasItem(Item item) {
//		for (int i = 0; i < 9; ++i) {
//            ItemStack stack = Wrapper.INSTANCE.inventory().getStackInSlot(i);
//            if(stack != null && stack.getItem() == item) { return true; }
//        }
//		return false;
//    }
//	
//	public void switchToItem(Item item) {
//		int newSlot = -1;
//        for (int i = 0; i < 9; ++i) {
//            ItemStack stack = Wrapper.INSTANCE.inventory().getStackInSlot(i);
//            if(stack != null && stack.getItem() == item) { newSlot = i; break; }
//        }
//        if (newSlot == -1) return;
//        Wrapper.INSTANCE.inventory().currentItem = newSlot;
//	}
//	
//	public void useItem() {
//        Wrapper.INSTANCE.sendPacket((new CPacketPlayer.Rotation(90.0f, 90.0f, false)));
//        Utils.swingMainHand();
//        RobotUtils.clickMouse(1);
//	}
//	
//	@Override
//	public void onClientTick(ClientTickEvent event) {
//		 if (Wrapper.INSTANCE.player().fallDistance >= 5.0f) {
//	            this.switchToItem(Items.WATER_BUCKET);
//	            Block block = BlockUtils.getBlock(Wrapper.INSTANCE.player().getPosition().down(-3));
//	            if (block != Blocks.AIR && hasItem(Items.WATER_BUCKET)) {
//	                this.useItem();
//	                ++this.delay;
//	                if (this.delay >= 20) {
//	                	this.switchToItem(Items.BUCKET);
//	                	this.useItem();
//	                    this.delay = 0;
//	                }
//	            }
//	        }
//		super.onClientTick(event);
//	}
//}
