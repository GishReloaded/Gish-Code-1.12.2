package i.gishreloaded.gishcode.hack.hacks;

import i.gishreloaded.gishcode.hack.Hack;
import i.gishreloaded.gishcode.hack.HackCategory;
import i.gishreloaded.gishcode.utils.Utils;
import i.gishreloaded.gishcode.value.Mode;
import i.gishreloaded.gishcode.value.ModeValue;
import i.gishreloaded.gishcode.value.NumberValue;
import i.gishreloaded.gishcode.wrappers.Wrapper;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraftforge.client.event.EntityViewRenderEvent.CameraSetup;
import net.minecraftforge.fml.common.gameevent.TickEvent.ClientTickEvent;
import i.gishreloaded.gishcode.utils.system.Connection.Side;

public class SelfDamage extends Hack{
	
	public NumberValue damage;
	
	public SelfDamage() {
		super("SelfDamage", HackCategory.COMBAT);
		
		damage = new NumberValue("Damage", 0.0625D, 0.0125D, 0.35D);
		
		this.addValue(damage);
	}
	
	@Override
	public String getDescription() {
		return "Deals damage to you (useful for bypassing AC).";
	}
	
	@Override
	public void onEnable() {
		Utils.selfDamage(damage.getValue().doubleValue());
		this.toggle();
		super.onEnable();
	}
}
