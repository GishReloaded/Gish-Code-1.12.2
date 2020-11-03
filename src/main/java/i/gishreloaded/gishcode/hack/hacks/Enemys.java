package i.gishreloaded.gishcode.hack.hacks;

import i.gishreloaded.gishcode.hack.Hack;
import i.gishreloaded.gishcode.hack.HackCategory;
import i.gishreloaded.gishcode.value.types.BooleanValue;

public class Enemys extends Hack{

	public Enemys() {
		super("Enemys", HackCategory.ANOTHER);
	}
	
	@Override
	public String getDescription() {
		return "Target only in enemy list.";
	}
}
