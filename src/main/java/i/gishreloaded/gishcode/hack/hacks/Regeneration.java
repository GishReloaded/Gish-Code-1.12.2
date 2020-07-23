//package i.gishreloaded.gishcode.hack.hacks;
//
//import i.gishreloaded.gishcode.hack.Hack;
//import i.gishreloaded.gishcode.hack.HackCategory;
//import i.gishreloaded.gishcode.utils.Utils;
//import i.gishreloaded.gishcode.value.Mode;
//import i.gishreloaded.gishcode.value.ModeValue;
//import i.gishreloaded.gishcode.value.NumberValue;
//import i.gishreloaded.gishcode.wrappers.Wrapper;
//import net.minecraft.network.play.client.CPacketPlayer;
//import net.minecraftforge.client.event.EntityViewRenderEvent.CameraSetup;
//import net.minecraftforge.fml.common.gameevent.TickEvent.ClientTickEvent;
//import i.gishreloaded.gishcode.utils.system.Connection.Side;
//
//public class Regeneration extends Hack {
//	
//	public NumberValue speed;
//	
//	public Regeneration() {
//		super("Regeneration", HackCategory.COMBAT);
//		
//		speed = new NumberValue("Speed", 100D, 10D, 1000D);
//		
//		this.addValue(speed);
//	}
//	
//	@Override
//	public void onClientTick(ClientTickEvent event) {
//		if(Wrapper.INSTANCE.player().capabilities.isCreativeMode
//				|| Wrapper.INSTANCE.player().getHealth() == 0)
//				return;
//		
//			if(Wrapper.INSTANCE.player().getFoodStats().getFoodLevel() < 18)
//				return;
//			
//			if(Wrapper.INSTANCE.player().getHealth() >= Wrapper.INSTANCE.player().getMaxHealth())
//				return;
//			
//			for(int i = 0; i < speed.getValue().intValue(); i++)
//				Wrapper.INSTANCE.sendPacket(new CPacketPlayer());
//		super.onClientTick(event);
//	}
//}
