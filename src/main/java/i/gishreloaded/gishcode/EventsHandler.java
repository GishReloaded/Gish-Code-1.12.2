package i.gishreloaded.gishcode;

import org.lwjgl.input.Keyboard;

import i.gishreloaded.gishcode.gui.click.ClickGuiScreen;
import i.gishreloaded.gishcode.hack.Hack;
import i.gishreloaded.gishcode.hack.hacks.AntiBot;
import i.gishreloaded.gishcode.hack.hacks.ClickGui;
import i.gishreloaded.gishcode.hack.hacks.GhostMode;
import i.gishreloaded.gishcode.managers.HackManager;
import i.gishreloaded.gishcode.utils.Utils;
import i.gishreloaded.gishcode.utils.system.Connection;
import i.gishreloaded.gishcode.utils.visual.ChatUtils;
import i.gishreloaded.gishcode.wrappers.Wrapper;
import net.minecraftforge.client.event.EntityViewRenderEvent;
import net.minecraftforge.client.event.GuiContainerEvent;
import net.minecraftforge.client.event.MouseEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.event.entity.ProjectileImpactEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class EventsHandler {
	private boolean initialized = false;
	
	public boolean onPacket(Object packet, Connection.Side side) {
        boolean suc = true;
        for (Hack hack : HackManager.getHacks()) {
            if (!hack.isToggled() || Wrapper.INSTANCE.world() == null) {
                continue;
            }
            suc &= hack.onPacket(packet, side);
        }
        return suc;
    }
	
	@SubscribeEvent
    public void onGuiContainer(GuiContainerEvent event) {
		if(Utils.nullCheck()) return;
    	try {
    		HackManager.onGuiContainer(event);
    	} catch (RuntimeException ex) {
    		ex.printStackTrace();
    		ChatUtils.error("RuntimeException: onGuiContainer");
    		ChatUtils.error(ex.toString());
    		Utils.copy(ex.toString());
    	}
	}
	
	@SubscribeEvent
    public void onGuiOpen(GuiOpenEvent event) {
		if(Utils.nullCheck()) return;
    	try {
    		HackManager.onGuiOpen(event);
    	} catch (RuntimeException ex) {
    		ex.printStackTrace();
    		ChatUtils.error("RuntimeException: onGuiOpen");
    		ChatUtils.error(ex.toString());
    		Utils.copy(ex.toString());
    	}
	}
	
	@SubscribeEvent
    public void onMouse(MouseEvent event) {
		if(Utils.nullCheck()) return;
    	try {
    		HackManager.onMouse(event);
    	} catch (RuntimeException ex) {
    		ex.printStackTrace();
    		ChatUtils.error("RuntimeException: onMouse");
    		ChatUtils.error(ex.toString());
    		Utils.copy(ex.toString());
    	}
	}
	
	@SubscribeEvent
    public void onKeyInput(InputEvent.KeyInputEvent event) {
		if(Utils.nullCheck()) return;
    	try {
    		int key = Keyboard.getEventKey();
    		if(Keyboard.getEventKeyState()) {
    			HackManager.onKeyPressed(key);
    		}
    	} catch (RuntimeException ex) {
    		ex.printStackTrace();
    		ChatUtils.error("RuntimeException: onKeyInput");
    		ChatUtils.error(ex.toString());
    		Utils.copy(ex.toString());
    	}
	}
	
	
	@SubscribeEvent
    public void onCameraSetup(EntityViewRenderEvent.CameraSetup event) {
		if(Utils.nullCheck() || GhostMode.enabled) return;
    	try {
    		HackManager.onCameraSetup(event);
    	} catch (RuntimeException ex) {
    		ex.printStackTrace();
    		ChatUtils.error("RuntimeException: onCameraSetup");
    		ChatUtils.error(ex.toString());
    		Utils.copy(ex.toString());
    	}
	}
	
	@SubscribeEvent
    public void onItemPickup(EntityItemPickupEvent event) {
		if(Utils.nullCheck() || GhostMode.enabled) return;
    	try {
    		HackManager.onItemPickup(event);
    	} catch (RuntimeException ex) {
    		ex.printStackTrace();
    		ChatUtils.error("RuntimeException: onItemPickup");
    		ChatUtils.error(ex.toString());
    		Utils.copy(ex.toString());
    	}
    } 
	
	@SubscribeEvent
    public void onProjectileImpact(ProjectileImpactEvent event) {
		if(Utils.nullCheck() || GhostMode.enabled) return;
    	try {
    		HackManager.onProjectileImpact(event);
    	} catch (RuntimeException ex) {
    		ex.printStackTrace();
    		ChatUtils.error("RuntimeException: ProjectileImpact");
    		ChatUtils.error(ex.toString());
    		Utils.copy(ex.toString());
    	}
	}
    	
	
    @SubscribeEvent
    public void onAttackEntity(AttackEntityEvent event) {
    	if(Utils.nullCheck() || GhostMode.enabled) return;
    	try {
    		HackManager.onAttackEntity(event);
    	} catch (RuntimeException ex) {
    		ex.printStackTrace();
    		ChatUtils.error("RuntimeException: onAttackEntity");
    		ChatUtils.error(ex.toString());
    		Utils.copy(ex.toString());
    	}
    }    
    
    @SubscribeEvent
    public void onPlayerTick(TickEvent.PlayerTickEvent event) {
    	if(Utils.nullCheck() || GhostMode.enabled) return;
    	try {
    		HackManager.onPlayerTick(event);
    	} catch (RuntimeException ex) {
    		ex.printStackTrace();
    		ChatUtils.error("RuntimeException: onPlayerTick");
    		ChatUtils.error(ex.toString());
    		Utils.copy(ex.toString());
    	}
    } 
    
    @SubscribeEvent
    public void onClientTick(TickEvent.ClientTickEvent event) {
    	if(Utils.nullCheck()) {
    		AntiBot.bots.clear();
    		initialized = false;
    		return; 
    	}
    	try {
    		if (!initialized) {
                new Connection(this);
                ClickGui.setColor();
                initialized = true;
            }
    		if(!(Wrapper.INSTANCE.mc().currentScreen instanceof ClickGuiScreen))
    			HackManager.getHack("ClickGui").setToggled(false);
    		if(!GhostMode.enabled)
    			HackManager.onClientTick(event);
    	} catch (RuntimeException ex) {
    		ex.printStackTrace();
    		ChatUtils.error("RuntimeException: onClientTick");
    		ChatUtils.error(ex.toString());
    		Utils.copy(ex.toString());
    	}
    }
    
    @SubscribeEvent
    public void onLivingUpdate(LivingEvent.LivingUpdateEvent event) {
    	if(Utils.nullCheck() || GhostMode.enabled) return;
    	try {
    		HackManager.onLivingUpdate(event);
    	} catch (RuntimeException ex) {
    		ex.printStackTrace();
    		ChatUtils.error("RuntimeException: onLivingUpdate");
    		ChatUtils.error(ex.toString());
    		Utils.copy(ex.toString());
    	}
    }
    
    @SubscribeEvent
    public void onRenderWorldLast(RenderWorldLastEvent event) {
    	if(Utils.nullCheck() || GhostMode.enabled || Wrapper.INSTANCE.mcSettings().hideGUI) return;
    	try {
    		HackManager.onRenderWorldLast(event);
    	} catch (RuntimeException ex) {
    		ex.printStackTrace();
    		ChatUtils.error("RuntimeException: onRenderWorldLast");
    		ChatUtils.error(ex.toString());
    		Utils.copy(ex.toString());
    	}
    }
	
	@SubscribeEvent
    public void onRenderGameOverlay(RenderGameOverlayEvent.Text event) {
		if(Utils.nullCheck() || GhostMode.enabled) return;
    	try {
    		HackManager.onRenderGameOverlay(event);
    	} catch (RuntimeException ex) {
    		ex.printStackTrace();
    		ChatUtils.error("RuntimeException: onRenderGameOverlay");
    		ChatUtils.error(ex.toString());
    		Utils.copy(ex.toString());
    	}
	}
	
	@SubscribeEvent
	public void onLeftClickBlock(PlayerInteractEvent.LeftClickBlock event){
		if(Utils.nullCheck() || GhostMode.enabled) return;
    	try {
    		HackManager.onLeftClickBlock(event);
    	} catch (RuntimeException ex) {
    		ex.printStackTrace();
    		ChatUtils.error("RuntimeException: onPlayerDamageBlock");
    		ChatUtils.error(ex.toString());
    		Utils.copy(ex.toString());
    	}
	}
}
