package i.gishreloaded.gishcode.hack.hacks;

import org.lwjgl.input.Keyboard;

import i.gishreloaded.gishcode.Main;
import i.gishreloaded.gishcode.hack.Hack;
import i.gishreloaded.gishcode.hack.HackCategory;
import i.gishreloaded.gishcode.utils.visual.ColorUtils;
import i.gishreloaded.gishcode.utils.visual.RenderUtils;
import i.gishreloaded.gishcode.value.Mode;
import i.gishreloaded.gishcode.value.types.BooleanValue;
import i.gishreloaded.gishcode.value.types.IntegerValue;
import i.gishreloaded.gishcode.value.types.ModeValue;
import i.gishreloaded.gishcode.wrappers.Wrapper;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraftforge.client.event.RenderGameOverlayEvent.Text;
import net.minecraftforge.fml.common.gameevent.TickEvent.ClientTickEvent;

public class ClickGui extends Hack{

	public ModeValue theme;
	public static BooleanValue rainbow;
	public static BooleanValue shadow;
	public static BooleanValue tooltip;
	
	public static IntegerValue red;
	public static IntegerValue green;
	public static IntegerValue blue;
	public static IntegerValue alpha;
	
	private static int color;
	public static boolean isLight = false;
	
	public ClickGui() {
		super("ClickGui", HackCategory.VISUAL);
		this.setKey(Keyboard.KEY_RSHIFT);
		this.setShow(false);
		
		this.theme = new ModeValue("Theme", new Mode("Dark", true), new Mode("Light", false));
		
		this.tooltip = new BooleanValue("Tooltip", true);
		this.shadow = new BooleanValue("Shadow", true);
		this.rainbow = new BooleanValue("Rainbow", true);
		this.red = new IntegerValue("Red", 255, 0, 255);
		this.green = new IntegerValue("Green", 255, 0, 255);
		this.blue = new IntegerValue("Blue", 255, 0, 255);
		this.alpha = new IntegerValue("Alpha", 255, 0, 255);
		
		this.addValue(theme, tooltip, shadow, rainbow, red, green, blue, alpha);
		this.setColor();
	}
	
	@Override
	public String getDescription() {
		return "Graphical user interface.";
	}
	
	 public static int getColor() {
		 return rainbow.getValue() ? ColorUtils.rainbow().getRGB() : color;
	 }
	
	 public static void setColor() {
		color = ColorUtils.color(red.getValue(),
				green.getValue(),
				blue.getValue(),
				alpha.getValue());
	}
	
	@Override
	public void onEnable() {
		if(GhostMode.enabled) 
			return;
		Wrapper.INSTANCE.mc().displayGuiScreen(Main.hackManager.getGui());
		super.onEnable();
	}
	
	@Override
	public void onClientTick(ClientTickEvent event) {
		this.setColor();
		this.isLight = theme.getMode("Light").isToggled();
		super.onClientTick(event);
	}
	
	@Override
	public void onRenderGameOverlay(Text event) {
		if(shadow.getValue()) {
			ScaledResolution sr = new ScaledResolution(Wrapper.INSTANCE.mc());
			RenderUtils.drawRect(0, 0, sr.getScaledWidth(), sr.getScaledHeight(), ColorUtils.color(0.0F, 0.0F, 0.0F, 0.5F));
		}
		super.onRenderGameOverlay(event);
	}

}
