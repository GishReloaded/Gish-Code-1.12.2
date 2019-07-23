package i.gishreloaded.gishcode.hack.hacks;

import java.awt.Color;

import org.lwjgl.input.Keyboard;

import i.gishreloaded.gishcode.Main;
import i.gishreloaded.gishcode.Wrapper;
import i.gishreloaded.gishcode.hack.Hack;
import i.gishreloaded.gishcode.hack.HackCategory;
import i.gishreloaded.gishcode.managers.HackManager;
import i.gishreloaded.gishcode.utils.ColorUtils;
import i.gishreloaded.gishcode.value.BooleanValue;
import i.gishreloaded.gishcode.value.NumberValue;
import net.minecraftforge.client.event.RenderGameOverlayEvent.Text;
import net.minecraftforge.fml.common.gameevent.TickEvent.ClientTickEvent;

public class ClickGui extends Hack{

	public static BooleanValue rainbow;
	
	public static NumberValue red;
	public static NumberValue green;
	public static NumberValue blue;
	public static NumberValue alpha;
	
	public static Color color;
	
	public ClickGui() {
		super("ClickGui", HackCategory.VISUAL);
		this.setKey(Keyboard.KEY_RSHIFT);
		this.setShow(false);
		
		this.rainbow = new BooleanValue("Rainbow", true);
		this.red = new NumberValue("Red", 170D, 0D, 255D);
		this.green = new NumberValue("Green", 170D, 0D, 255D);
		this.blue = new NumberValue("Blue", 170D, 0D, 255D);
		this.alpha = new NumberValue("Alpha", 170D, 0D, 255D);
		
		this.addValue(rainbow, red, green, blue, alpha);
		this.setColor();
	}
	
	 public static int color() {
		 return rainbow.getValue() ? ColorUtils.rainbow(999999999999L, 1.0F).getRGB() : color.getRGB();
	 }
	
	void setColor() {
		if(this.rainbow.getValue()) {
			color = ColorUtils.rainbow(999999999999L, 1.0F);
		}
		else
		{
			color = new Color(this.red.getValue().intValue(),
					this.green.getValue().intValue(),
					this.blue.getValue().intValue(),
					this.alpha.getValue().intValue());
		}
	}
	
	@Override
	public void onEnable() {
		Wrapper.INSTANCE.mc().displayGuiScreen(Main.hackManager.getGui());
		super.onEnable();
	}
	
	@Override
	public void onClientTick(ClientTickEvent event) {
		this.setColor();
		super.onClientTick(event);
	}

}
