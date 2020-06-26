package i.gishreloaded.gishcode.hack.hacks;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

import org.lwjgl.opengl.GL11;

import i.gishreloaded.gishcode.Main;
import i.gishreloaded.gishcode.hack.Hack;
import i.gishreloaded.gishcode.hack.HackCategory;
import i.gishreloaded.gishcode.managers.HackManager;

import i.gishreloaded.gishcode.utils.visual.ChatUtils;
import i.gishreloaded.gishcode.utils.visual.ColorUtils;
import i.gishreloaded.gishcode.utils.visual.RenderUtils;
import i.gishreloaded.gishcode.value.BooleanValue;
import i.gishreloaded.gishcode.value.Mode;
import i.gishreloaded.gishcode.value.ModeValue;
import i.gishreloaded.gishcode.value.Value;
import i.gishreloaded.gishcode.wrappers.Wrapper;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.Text;

public class ArmorHUD extends Hack{
	
	public BooleanValue damage;
    public BooleanValue extraInfo;
 
    private int offHandHeldItemCount;
    private int armourCompress;
    private int armourSpacing;
 
    public ArmorHUD() {
        super("ArmorHUD", HackCategory.VISUAL);
        damage = new BooleanValue("Damage", true);
        extraInfo = new BooleanValue("ExtraInfo", false);
        this.addValue(damage, extraInfo);
    }
 
    @Override
    public String getDescription() {
        return "Show armor in game overlay.";
    }
 
    @Override
    public void onRenderGameOverlay(RenderGameOverlayEvent.Text event) {
    	ScaledResolution resolution = new ScaledResolution(Wrapper.INSTANCE.mc());
    	RenderItem itemRender = Wrapper.INSTANCE.mc().getRenderItem();
    	
        GlStateManager.enableTexture2D();
        int i = resolution.getScaledWidth() / 2;
        int iteration = 0;
        int y = resolution.getScaledHeight() - 55 - (Wrapper.INSTANCE.player().isInWater() ? 10 : 0);
        
        for (ItemStack is : Wrapper.INSTANCE.inventory().armorInventory) {
        	
            iteration++;
            if (is.isEmpty()) continue;
            int x = i - 90 + (9 - iteration) * armourSpacing + armourCompress;
            GlStateManager.enableDepth();
 
            itemRender.zLevel = 200F;
            itemRender.renderItemAndEffectIntoGUI(is, x, y);
            itemRender.renderItemOverlayIntoGUI(Wrapper.INSTANCE.fontRenderer(), is, x, y, "");
            itemRender.zLevel = 0F;
 
            GlStateManager.enableTexture2D();
            GlStateManager.disableLighting();
            GlStateManager.disableDepth();
 
            String s = is.getCount() > 1 ? is.getCount() + "" : "";
            Wrapper.INSTANCE.fontRenderer().drawStringWithShadow(s, x + 19 - 2 - Wrapper.INSTANCE.fontRenderer().getStringWidth(s), y + 9, 0xffffff);
 
            if (damage.getValue()) {
                float green = ((float) is.getMaxDamage() - (float) is.getItemDamage()) / (float) is.getMaxDamage();
                float red = 1 - green;
                int dmg = 100 - (int) (red * 100);
                Wrapper.INSTANCE.fontRenderer().drawStringWithShadow(dmg + "", x + 8 - Wrapper.INSTANCE.fontRenderer().getStringWidth(dmg + "") / 2, y - 11, 0xFFFFFF);
            }
            
            GlStateManager.enableDepth();
            GlStateManager.disableLighting();
        }
 
        if (extraInfo.getValue()) {
            for (ItemStack is : Wrapper.INSTANCE.inventory().offHandInventory) {
                Item helfInOffHand = Wrapper.INSTANCE.player().getHeldItemOffhand().getItem();
                offHandHeldItemCount = getItemsOffHand(helfInOffHand);
                GlStateManager.pushMatrix();
                GlStateManager.disableAlpha();
                GlStateManager.clear(256);
                GlStateManager.enableBlend();
                GlStateManager.pushAttrib();
                RenderHelper.enableGUIStandardItemLighting();
                GlStateManager.disableDepth();
 
                Wrapper.INSTANCE.mc().getRenderItem().renderItemAndEffectIntoGUI(is, 572, y);
                itemRender.renderItemOverlayIntoGUI(Wrapper.INSTANCE.fontRenderer(), is, 572, y, String.valueOf(offHandHeldItemCount));
                GlStateManager.enableDepth();
                RenderHelper.disableStandardItemLighting();
                GlStateManager.popAttrib();
                GlStateManager.disableBlend();
 
                GlStateManager.disableDepth();
                GlStateManager.disableLighting();
                GlStateManager.enableDepth();
                GlStateManager.enableAlpha();
                GlStateManager.popMatrix();
 
            }
        }
        
        if (extraInfo.getValue()) {
            Item currentHeldItem = Wrapper.INSTANCE.inventory().getCurrentItem().getItem();
            int currentHeldItemCount = Wrapper.INSTANCE.inventory().getCurrentItem().getCount();
 
            ItemStack stackHeld = new ItemStack(currentHeldItem, 1);
            GlStateManager.pushMatrix();
            GlStateManager.disableAlpha();
            GlStateManager.clear(256);
            GlStateManager.enableBlend();
            GlStateManager.pushAttrib();
            RenderHelper.enableGUIStandardItemLighting();
            GlStateManager.disableDepth();
            Wrapper.INSTANCE.mc().getRenderItem().renderItemAndEffectIntoGUI(stackHeld, 556, y);
 
            itemRender.renderItemOverlayIntoGUI(Wrapper.INSTANCE.fontRenderer(), stackHeld, 556, y, String.valueOf(currentHeldItemCount));
 
            GlStateManager.enableDepth();
            RenderHelper.disableStandardItemLighting();
            GlStateManager.popAttrib();
            GlStateManager.disableBlend();
 
            GlStateManager.disableDepth();
            GlStateManager.disableLighting();
            GlStateManager.enableDepth();
            GlStateManager.enableAlpha();
            GlStateManager.popMatrix();
 
        }
        
        GlStateManager.enableDepth();
        GlStateManager.disableLighting();
        
        if (extraInfo.getValue()) {
            armourCompress = 14;
            armourSpacing = 17;
        } else {
            armourCompress = 2;
            armourSpacing = 20;
        }
        
        GlStateManager.enableDepth();
        GlStateManager.disableLighting();
    }
 
    int getItemsOffHand(Item i) {
        return Wrapper.INSTANCE.inventory().offHandInventory.stream().
        		filter(itemStack -> itemStack.getItem() == i).mapToInt(ItemStack::getCount).sum();
    }
}
