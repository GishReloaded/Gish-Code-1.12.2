package i.gishreloaded.gishcode.hack.hacks;

import i.gishreloaded.gishcode.hack.Hack;
import i.gishreloaded.gishcode.hack.HackCategory;

import i.gishreloaded.gishcode.value.NumberValue;
import i.gishreloaded.gishcode.wrappers.Wrapper;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.GuiMultiplayer;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraftforge.fml.common.gameevent.TickEvent.ClientTickEvent;

public class Disconnect extends Hack{

	public NumberValue leaveHealth;
	
	public Disconnect() {
		super("Disconnect", HackCategory.COMBAT);
		
		leaveHealth = new NumberValue("LeaveHealth", 4.0D, 0D, 20D);
		
		this.addValue(leaveHealth);
	}
	
	@Override
	public String getDescription() {
		return "Automatically leaves the server when your health is low.";
	}
	
	@Override
	public void onClientTick(ClientTickEvent event) {
		if(Wrapper.INSTANCE.player().getHealth() <= leaveHealth.getValue().floatValue()) {
			
			boolean flag = Wrapper.INSTANCE.mc().isIntegratedServerRunning();
			Wrapper.INSTANCE.world().sendQuittingDisconnectingPacket();
			Wrapper.INSTANCE.mc().loadWorld((WorldClient)null);
			
            if (flag)
            	Wrapper.INSTANCE.mc().displayGuiScreen(new GuiMainMenu()); else
            	Wrapper.INSTANCE.mc().displayGuiScreen(new GuiMultiplayer(new GuiMainMenu()));
            this.setToggled(false);
		}
		super.onClientTick(event);
	}
}
