package i.gishreloaded.gishcode.hack.hacks;

import java.lang.reflect.Field;

import i.gishreloaded.gishcode.hack.Hack;
import i.gishreloaded.gishcode.hack.HackCategory;
import i.gishreloaded.gishcode.utils.Utils;
import i.gishreloaded.gishcode.utils.system.Mapping;
import i.gishreloaded.gishcode.value.NumberValue;
import i.gishreloaded.gishcode.wrappers.Wrapper;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraftforge.fml.common.gameevent.TickEvent.ClientTickEvent;

public class Suicide extends Hack{
	
	public NumberValue damage;
	
	public Suicide() {
		super("Suicide", HackCategory.COMBAT);
		
		damage = new NumberValue("Damage", 0.35D, 0.0125D, 0.50D);
		
		this.addValue(damage);
	}
	
	@Override
	public String getDescription() {
		return "Kills you.";
	}
	
	@Override
	public void onClientTick(ClientTickEvent event) {
		if(Wrapper.INSTANCE.player().isDead) this.toggle();
		Utils.selfDamage(damage.getValue().doubleValue());
		super.onClientTick(event);
	}
	
}
