package i.gishreloaded.gishcode.hack.hacks;

import i.gishreloaded.gishcode.hack.Hack;
import i.gishreloaded.gishcode.hack.HackCategory;
import i.gishreloaded.gishcode.utils.Utils;
import i.gishreloaded.gishcode.value.BooleanValue;
import net.minecraftforge.fml.common.gameevent.TickEvent.ClientTickEvent;

public class Targets extends Hack{

	public BooleanValue players;
    public BooleanValue mobs;
    public BooleanValue invisibles;
    public BooleanValue murder;
    
	public Targets() {
		super("Targets", HackCategory.ANOTHER);
		this.setShow(false);
		this.setToggled(true);
		
		players = new BooleanValue("Players", true);
		mobs = new BooleanValue("Mobs", false);
		invisibles = new BooleanValue("Invisibles", false);
		murder = new BooleanValue("Murder", false);
		
		addValue(players, mobs, invisibles, murder);
	}
	
	@Override
	public String getDescription() {
		return "Manage targets for hacks.";
	}
}
