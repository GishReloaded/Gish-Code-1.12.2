package i.gishreloaded.gishcode.managers;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.lwjgl.input.Keyboard;

import i.gishreloaded.gishcode.Wrapper;
import i.gishreloaded.gishcode.gui.click.ClickGuiScreen;
import i.gishreloaded.gishcode.gui.click.theme.dark.DarkTheme;
import i.gishreloaded.gishcode.hack.Hack;
import i.gishreloaded.gishcode.hack.hacks.*;
import i.gishreloaded.gishcode.value.Mode;
import i.gishreloaded.gishcode.value.ModeValue;
import i.gishreloaded.gishcode.value.Value;
import net.minecraft.client.gui.GuiChat;
import net.minecraftforge.client.event.EntityViewRenderEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.event.entity.ProjectileImpactEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class HackManager {
	
	private static Hack toggleHack = null;
	private static ArrayList<Hack> hacks;
	private GuiManager guiManager;
	private ClickGuiScreen guiScreen;

	public HackManager() {
		hacks = new ArrayList<Hack>();
		addHack(new ClickGui());
		addHack(new HUD());
		addHack(new Targets());
		addHack(new Enemys());
		addHack(new Teams());
		addHack(new NoScreenEvents());
		addHack(new Glowing());
		addHack(new Trajectories());
		addHack(new XRay());
		addHack(new ESP());
		addHack(new ItemESP());
		addHack(new ChestESP());
		addHack(new Tracers());
		addHack(new WallHack());
		addHack(new Flight());
		addHack(new NightVision());
		addHack(new Profiler());
		addHack(new AntiBot());
		addHack(new AimBot());
		addHack(new BowAimBot());
		addHack(new Trigger());
		addHack(new Criticals());
		addHack(new KillAura());
		addHack(new Velocity());
		addHack(new AutoSprint());
		addHack(new AutoArmor());
		addHack(new FakeFace());
		addHack(new InteractClick());
		addHack(new ChestStealer());
		addHack(new Glide());
		addHack(new AntiFall());
		addHack(new Ghost());
		addHack(new Blink());
		addHack(new Scaffold());
		addHack(new FastLadder());
		addHack(new Speed());
		addHack(new Step());
		addHack(new NoSneak());
		addHack(new FreeCam());
		addHack(new BlockOverlay());
		addHack(new PluginsGetter());
		addHack(new AttackPacketCW());
		addHack(new AttackPacketCIA());
		addHack(new Teleport());
		//addHack(new DisableAtDeath());
		//addHack(new TestHack());
	}
	
	public ClickGuiScreen getGui() {
        if (this.guiManager == null) {
            this.guiManager = new GuiManager();
            this.guiScreen = new ClickGuiScreen();
            ClickGuiScreen.clickGui = this.guiManager;
            this.guiManager.Initialization();
            this.guiManager.setTheme(new DarkTheme());
        }
        return this.guiManager;
    }
	
	public static Hack getHack(String name) {
		Hack hack = null;
		for(Hack h : getHacks()) {
        	if(h.getName().equals(name)) {
        		hack = h;
        	}
        }
		return hack;
	}
	
	public static List<Hack> getSortedHacks() {
	        final List<Hack> list = new ArrayList<Hack>();
	        for (final Hack hack : getHacks()) {
	            if (hack.isToggled()) {
	                if (!hack.isShow()) {
	                    continue;
	                }
	                list.add(hack);
	            }
	        }
	        list.sort(new Comparator<Hack>() {
	            @Override
	            public int compare(final Hack h1, final Hack h2) {
	                String s1 = h1.getName();
	                String s2 = h2.getName();
	                for(Value value : h1.getValues()) {
	    				if(value instanceof ModeValue) {
	    					ModeValue modeValue = (ModeValue)value;
	    					if(!modeValue.getModeName().equals("Priority")) {
	    						for(Mode mode : modeValue.getModes()) {
	    							if(mode.isToggled()) {
	    								s1 = s1 + " " + mode.getName();
	    							}
	    						}
	    					}
	    				}
	    			}
	                for(Value value : h2.getValues()) {
	    				if(value instanceof ModeValue) {
	    					ModeValue modeValue = (ModeValue)value;
	    					if(!modeValue.getModeName().equals("Priority")) {
	    						for(Mode mode : modeValue.getModes()) {
	    							if(mode.isToggled()) {
	    								s2 = s2 + " " + mode.getName();
	    							}
	    						}
	    					}
	    				}
	    			}
	                final int cmp = Wrapper.INSTANCE.fontRenderer().getStringWidth(s2) - Wrapper.INSTANCE.fontRenderer().getStringWidth(s1);
	                return (cmp != 0) ? cmp : s2.compareTo(s1);
	            }
	        });
	        return list;
	    }
	
	public static void addHack(Hack hack) {
		hacks.add(hack);
	}

	public static ArrayList<Hack> getHacks() {
		return hacks;
	}
	
	public static Hack getToggleHack() {
		return toggleHack;
	}
	
	public static void onKeyPressed(int key) {
		if (Wrapper.INSTANCE.mc().currentScreen != null) {
            return;
        }
		for(Hack hack : getHacks()) {
    		if(hack.getKey() == key) {
    			hack.toggle();
    			toggleHack = hack;
    		}
    	}
	}
	
	public static void onCameraSetup(EntityViewRenderEvent.CameraSetup event) {
		for(Hack hack : getHacks()) {
    		if(hack.isToggled()) {
    			hack.onCameraSetup(event);
    		}
    	}
	}
	
	public static void onAttackEntity(AttackEntityEvent event) {
		for(Hack hack : getHacks()) {
    		if(hack.isToggled()) {
    			hack.onAttackEntity(event);
    		}
    	}
	}
	
	public static void onProjectileImpact(ProjectileImpactEvent event) {
			for(Hack hack : getHacks()) {
	    		if(hack.isToggled()) {
	    			hack.onProjectileImpact(event);
	    		}
	    	}
		}
	
    public static void onItemPickup(EntityItemPickupEvent event) {
		for(Hack hack : getHacks()) {
    		if(hack.isToggled()) {
    			hack.onItemPickup(event);
    		}
    	}
	}
	
	public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
    	for(Hack hack : getHacks()) {
    		if(hack.isToggled()) {
    			hack.onPlayerTick(event);
    		}
    	}
    }
	
    public static void onClientTick(TickEvent.ClientTickEvent event) {
    	for(Hack hack : getHacks()) {
    		if(hack.isToggled()) {
    			hack.onClientTick(event);
    		}
    	}
    }
    public static void onLivingUpdate(LivingEvent.LivingUpdateEvent event) {
    	for(Hack hack : getHacks()) {
    		if(hack.isToggled()) {
    			hack.onLivingUpdate(event);
    		}
    	}
    }
    public static void onRenderWorldLast(RenderWorldLastEvent event) {
    	for(Hack hack : getHacks()) {
    		if(hack.isToggled()) {
    			hack.onRenderWorldLast(event);
    		}
    	}
    }
    public static void onRenderGameOverlay(RenderGameOverlayEvent.Text event) {
    	for(Hack hack : getHacks()) {
    		if(hack.isToggled()) {
    			hack.onRenderGameOverlay(event);
    		}
    	}
    }
}
