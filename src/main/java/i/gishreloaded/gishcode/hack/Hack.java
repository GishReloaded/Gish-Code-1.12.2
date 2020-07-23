package i.gishreloaded.gishcode.hack;

import java.util.ArrayList;

import i.gishreloaded.gishcode.gui.click.ClickGuiScreen;
import i.gishreloaded.gishcode.hack.hacks.HUD;
import i.gishreloaded.gishcode.utils.system.Connection;
import i.gishreloaded.gishcode.utils.visual.ChatUtils;
import i.gishreloaded.gishcode.utils.visual.RenderUtils;
import i.gishreloaded.gishcode.value.BooleanValue;
import i.gishreloaded.gishcode.value.Mode;
import i.gishreloaded.gishcode.value.ModeValue;
import i.gishreloaded.gishcode.value.Value;
import i.gishreloaded.gishcode.wrappers.Wrapper;
import net.minecraftforge.client.event.EntityViewRenderEvent;
import net.minecraftforge.client.event.GuiContainerEvent;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.client.event.MouseEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.event.entity.ProjectileImpactEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class Hack {
	
	private String name;
	private HackCategory category;
	private boolean toggled;
	private boolean show;
	private int key;
	private ArrayList<Value> values = new ArrayList<Value>();

	public Hack(String name, HackCategory category) {
		this.name = name;
		this.category = category;
		this.toggled = false;
		this.show = true;
		this.key = -1;
	}
	
	public void addValue(Value... values) {
        for (Value value : values) {
            this.getValues().add(value);
        }
    }

    public ArrayList<Value> getValues() {
        return values;
    }
    
    public boolean isToggledMode(String modeName) {
    	for (Value value : this.values) {
    		if(value instanceof ModeValue) {
    			ModeValue modeValue = (ModeValue) value;
    			for(Mode mode : modeValue.getModes()) {
    				if(mode.getName().equalsIgnoreCase(modeName)) {
    					if(mode.isToggled()) {
    						return true;
    					}
    				}
    			}
    		}
    	}
    	return false;
    }
    
    public boolean isToggledValue(String valueName) {
    	for (Value value : this.values) {
        	if(value instanceof BooleanValue) {
        		BooleanValue booleanValue = (BooleanValue) value;
        		if(booleanValue.getName().equalsIgnoreCase(valueName)) {
        			if(booleanValue.getValue()) {
						return true;
					}
           		}
        	}
        }
    	return false;
    }
    
    public void setValues(ArrayList<Value> values) {
        for (Value value : values) {
            for (Value value1 : this.values) {
                if (value.getName().equalsIgnoreCase(value1.getName())) {
                    value1.setValue(value.getValue());
                }
            }
        }
    }

	public void toggle() {
        this.toggled = !this.toggled;
        if (this.toggled) {
            this.onEnable();
        } else {
            this.onDisable();
        }
    	RenderUtils.splashTickPos = 0;
        if(!RenderUtils.isSplash && !(Wrapper.INSTANCE.mc().currentScreen instanceof ClickGuiScreen)) {
        	RenderUtils.isSplash = true;
        }
    }
	
	public void onEnable() {}
    public void onDisable() {}
    public void onGuiContainer(GuiContainerEvent event) {}
    public void onGuiOpen(GuiOpenEvent event) {}
    public void onMouse(MouseEvent event) {}
    public boolean onPacket(Object packet, Connection.Side side) { return true; }
    public void onPlayerTick(TickEvent.PlayerTickEvent event) {}
    public void onClientTick(TickEvent.ClientTickEvent event) {}
    public void onCameraSetup(EntityViewRenderEvent.CameraSetup event) {}
    public void onAttackEntity(AttackEntityEvent event) {}
    public void onItemPickup(EntityItemPickupEvent event) {}
    public void onProjectileImpact(ProjectileImpactEvent event) {}
    public void onLivingUpdate(LivingEvent.LivingUpdateEvent event) {}
    public void onRenderWorldLast(RenderWorldLastEvent event) {}
    public void onRenderGameOverlay(RenderGameOverlayEvent.Text event) {}
    public void onLeftClickBlock(PlayerInteractEvent.LeftClickBlock event){}
	
    public void d(Object o) { 
    	String str = "[DEBUG]: " + o;
    	ChatUtils.warning(str);
    	System.out.println(str);
    }
    
    public void d() { 
    	String str = "[DEBUG]: !";
    	ChatUtils.warning(str);
    	System.out.println(str);
    }
    
    public String getDescription() {
		return null;
	}
    
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public HackCategory getCategory() {
		return category;
	}
	
	public void setCategory(HackCategory category) {
		this.category = category;
	}
	
	public int getKey() {
		return key;
	}
	
	public void setKey(int key) {
		this.key = key;
	}
	
	public boolean isToggled() {
		return toggled;
	}

	public void setToggled(boolean toggled) {
		this.toggled = toggled;
	}

	public boolean isShow() {
		return show;
	}

	public void setShow(boolean show) {
		this.show = show;
	}
}

