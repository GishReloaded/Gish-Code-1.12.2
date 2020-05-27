package i.gishreloaded.gishcode.hack.hacks;

import i.gishreloaded.gishcode.hack.Hack;
import i.gishreloaded.gishcode.hack.HackCategory;
import i.gishreloaded.gishcode.managers.HackManager;
import i.gishreloaded.gishcode.utils.RobotUtils;
import i.gishreloaded.gishcode.utils.Utils;
import i.gishreloaded.gishcode.utils.ValidUtils;
import i.gishreloaded.gishcode.utils.system.Connection.Side;
import i.gishreloaded.gishcode.value.BooleanValue;
import i.gishreloaded.gishcode.value.NumberValue;
import i.gishreloaded.gishcode.wrappers.Wrapper;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.ContainerPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.CPacketClickWindow;
import net.minecraft.util.NonNullList;
import net.minecraftforge.fml.common.gameevent.TickEvent.ClientTickEvent;

public class AutoShield extends Hack{
	
	public AutoShield() {
		super("AutoShield", HackCategory.COMBAT);
	}
	
	@Override
	public String getDescription() {
		return "Manages your shield automatically.";
	}
	
	@Override
	public void onClientTick(ClientTickEvent event) {
		if(!Utils.screenCheck()) { this.blockByShield(false); }
		super.onClientTick(event);
	}
	
	@Override
	public void onDisable() {
		this.blockByShield(false);
		super.onDisable();
	}
	
	 public void blockByShield(boolean state) {
		if(Wrapper.INSTANCE.player().getHeldItemOffhand().getItem() != Items.SHIELD) return;
			RobotUtils.setMouse(1, state);
	}
	 
	 public static void block(boolean state) {
		AutoShield hack = (AutoShield)HackManager.getHack("AutoShield");
		if(hack.isToggled()) hack.blockByShield(state);
	}
}
