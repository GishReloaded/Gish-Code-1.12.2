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

public class AutoEat extends Hack{
	
	private int oldSlot;
	private int bestSlot;
	
	public AutoEat() {
		super("AutoEat", HackCategory.PLAYER);
	}
	
	@Override
	public String getDescription() {
		return "Eat food automatically.";
	}
	
	@Override
	public void onEnable() {
	      this.oldSlot = -1;
	      this.bestSlot = -1;
		super.onEnable();
	}
	
	@Override
	public void onClientTick(ClientTickEvent event) {
		if (this.oldSlot == -1) {
	         if (!this.canEat()) {
	            return;
	         }
	         float bestSaturation = 0.0F;
	         for(int i = 0; i < 9; ++i) {
	            ItemStack stack = Wrapper.INSTANCE.inventory().getStackInSlot(i);
	            if (this.isFood(stack)) {
	               ItemFood food = (ItemFood)stack.getItem();
	               float saturation = food.getSaturationModifier(stack);
	               if (saturation > bestSaturation) {
	                  bestSaturation = saturation;
	                  this.bestSlot = i;
	               }
	            }
	         }
	         if (this.bestSlot != -1) 
	            this.oldSlot = Wrapper.INSTANCE.inventory().currentItem;
	      } else {
	         if (!this.canEat()) {
	            this.stop();
	            return;
	         }

	         if (!this.isFood(Wrapper.INSTANCE.inventory().getStackInSlot(this.bestSlot))) {
	            this.stop();
	            return;
	         }

	         Wrapper.INSTANCE.inventory().currentItem = this.bestSlot;
	         KeyBinding.setKeyBindState(Wrapper.INSTANCE.mcSettings().keyBindUseItem.getKeyCode(), true);
	      }
		super.onClientTick(event);
	}
	
	boolean canEat() {
	      if (!Wrapper.INSTANCE.player().canEat(false)) {
	         return false;
	      } else {
	         if (Wrapper.INSTANCE.mc().objectMouseOver != null) {
	            Entity entity = Wrapper.INSTANCE.mc().objectMouseOver.entityHit;
	            if (entity instanceof EntityVillager || entity instanceof EntityTameable) {
	               return false;
	            }

	            BlockPos pos = Wrapper.INSTANCE.mc().objectMouseOver.getBlockPos();
	            if (pos != null) {
	               Block block = BlockUtils.getBlock(pos);
	               if (block instanceof BlockContainer || block instanceof BlockWorkbench) {
	                  return false;
	               }
	            }
	         }

	         return true;
	      }
	   }

	   boolean isFood(ItemStack stack) {
	      return !Utils.isNullOrEmptyStack(stack) && stack.getItem() instanceof ItemFood;
	   }

	   void stop() {
		  KeyBinding.setKeyBindState(Wrapper.INSTANCE.mcSettings().keyBindUseItem.getKeyCode(), false);
	      Wrapper.INSTANCE.inventory().currentItem = this.oldSlot;
	      this.oldSlot = -1;
	   }
}
