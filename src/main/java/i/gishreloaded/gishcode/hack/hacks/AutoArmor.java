package i.gishreloaded.gishcode.hack.hacks;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import i.gishreloaded.gishcode.hack.Hack;
import i.gishreloaded.gishcode.hack.HackCategory;
import i.gishreloaded.gishcode.utils.system.Wrapper;
import i.gishreloaded.gishcode.utils.system.Connection.Side;
import i.gishreloaded.gishcode.value.BooleanValue;
import i.gishreloaded.gishcode.value.NumberValue;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.InventoryEffectRenderer;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Enchantments;
import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.CPacketClickWindow;
import net.minecraft.util.DamageSource;
import net.minecraftforge.fml.common.gameevent.TickEvent.ClientTickEvent;

public class AutoArmor extends Hack{

	public BooleanValue useEnchantments;
	public BooleanValue swapWhileMoving;
	
	public NumberValue delay;
	
	private int timer;
	
	public AutoArmor() {
		super("AutoArmor", HackCategory.PLAYER);
		
		useEnchantments = new BooleanValue("Enchantments", true);
		swapWhileMoving = new BooleanValue("SwapWhileMoving", false);
		
		this.addValue(useEnchantments, swapWhileMoving);
		delay = new NumberValue("Delay", 2.0D, 0D, 20D);
	}
	
	@Override
	public void onEnable() {
		this.timer = 0;
		super.onEnable();
	}
	
	@Override
	public void onClientTick(ClientTickEvent event) {
				if(timer > 0){
					timer--;
					return;
				}
				if(Wrapper.INSTANCE.mc().currentScreen instanceof GuiContainer
					&& !(Wrapper.INSTANCE.mc().currentScreen instanceof InventoryEffectRenderer))
					return;
				
				InventoryPlayer inventory = Wrapper.INSTANCE.player().inventory;
				
				if(!swapWhileMoving.getValue()
					&& (Wrapper.INSTANCE.player().movementInput.moveForward != 0
						|| Wrapper.INSTANCE.player().movementInput.moveStrafe != 0))
					return;
				
				
				int[] bestArmorSlots = new int[4];
				int[] bestArmorValues = new int[4];
				
				
				for(int type = 0; type < 4; type++)
				{
					bestArmorSlots[type] = -1;
					
					ItemStack stack = inventory.armorItemInSlot(type);
					if(this.isNullOrEmpty(stack)
						|| !(stack.getItem() instanceof ItemArmor))
						continue;
					
					ItemArmor item = (ItemArmor)stack.getItem();
					bestArmorValues[type] = getArmorValue(item, stack);
				}
				
				
				for(int slot = 0; slot < 36; slot++)
				{
					ItemStack stack = inventory.getStackInSlot(slot);
					
					if(this.isNullOrEmpty(stack)
						|| !(stack.getItem() instanceof ItemArmor))
						continue;
					
					ItemArmor item = (ItemArmor)stack.getItem();
					int armorType = item.armorType.getIndex();
					int armorValue = getArmorValue(item, stack);
					
					if(armorValue > bestArmorValues[armorType])
					{
						bestArmorSlots[armorType] = slot;
						bestArmorValues[armorType] = armorValue;
					}
				}
				
				
				ArrayList<Integer> types = new ArrayList<Integer>(Arrays.asList(0, 1, 2, 3));
				Collections.shuffle(types);
				for(int type : types)
				{
					
					int slot = bestArmorSlots[type];
					if(slot == -1)
						continue;
						
					
					ItemStack oldArmor = inventory.armorItemInSlot(type);
					if(!this.isNullOrEmpty(oldArmor)
						&& inventory.getFirstEmptyStack() == -1)
						continue;
					
					
					if(slot < 9)
						slot += 36;
					
					if(!this.isNullOrEmpty(oldArmor))
						Wrapper.INSTANCE.mc().playerController.windowClick(0, 8 - type, 0, ClickType.QUICK_MOVE,
								Wrapper.INSTANCE.player());
					
					
					Wrapper.INSTANCE.mc().playerController.windowClick(0, slot, 0, ClickType.QUICK_MOVE,
							Wrapper.INSTANCE.player());
					
					break;
				}
		super.onClientTick(event);
	}
	
	@Override
	public boolean onPacket(Object packet, Side side) {
		if(side == Side.OUT && packet instanceof CPacketClickWindow) {
			this.timer = this.delay.getValue().intValue();
		}
		return true;
	}
	
	public static boolean isNullOrEmpty(ItemStack stack)
	{
		return stack == null || stack.isEmpty();
	}
	
	int getArmorValue(ItemArmor item, ItemStack stack) {
		int armorPoints = item.damageReduceAmount;
		int prtPoints = 0;
		int armorToughness = (int)item.toughness;
		int armorType = item.getArmorMaterial().getDamageReductionAmount(EntityEquipmentSlot.LEGS);
		
		if(useEnchantments.getValue())
		{
			Enchantment protection = Enchantments.PROTECTION;
			int prtLvl = EnchantmentHelper.getEnchantmentLevel(protection, stack);
			
			EntityPlayerSP player = Wrapper.INSTANCE.player();
			DamageSource dmgSource = DamageSource.causePlayerDamage(player);
			prtPoints = protection.calcModifierDamage(prtLvl, dmgSource);
		}
		
		return armorPoints * 5 + prtPoints * 3 + armorToughness + armorType;
	}

}
