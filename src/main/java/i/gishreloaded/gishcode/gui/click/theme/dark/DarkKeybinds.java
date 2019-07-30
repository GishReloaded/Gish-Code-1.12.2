package i.gishreloaded.gishcode.gui.click.theme.dark;

import java.awt.Color;

import org.lwjgl.input.Keyboard;

import i.gishreloaded.gishcode.gui.click.base.Component;
import i.gishreloaded.gishcode.gui.click.base.ComponentRenderer;
import i.gishreloaded.gishcode.gui.click.base.ComponentType;
import i.gishreloaded.gishcode.gui.click.elements.KeybindMods;
import i.gishreloaded.gishcode.gui.click.theme.Theme;
import i.gishreloaded.gishcode.utils.visual.ColorUtils;
import i.gishreloaded.gishcode.utils.visual.GLUtils;
import i.gishreloaded.gishcode.utils.visual.RenderUtils;

public class DarkKeybinds extends ComponentRenderer {

    public DarkKeybinds(Theme theme) {

        super(ComponentType.KEYBIND, theme);
    }

    @Override
    public void drawComponent(Component component, int mouseX, int mouseY) {

        KeybindMods keybind = (KeybindMods) component;
        Color colorButton = new Color(0, 0, 0, 200); 
        
        int colorString = ColorUtils.color(1.0f, 1.0f, 1.0f, 1.0f);
        
        int colorString1IsEdit = ColorUtils.color(1.0f, 1.0f, 1.0f, 1.0f);
        int colorString1IsStatic = ColorUtils.color(0.6f, 0.6f, 0.6f, 1.0f);
        
        int colorString2IsEdit = ColorUtils.color(1.0f, 1.0f, 1.0f, 1.0f);
        int colorString2IsStatic = ColorUtils.color(1.0f, 1.0f, 1.0f, 1.0f);
        
        theme.fontRenderer.drawString("Key", keybind.getX() + 4, keybind.getY() + 2, 
        		colorString);
        
        int nameWidth = theme.fontRenderer.getStringWidth("Key") + 7;
        
        RenderUtils.drawRect(keybind.getX() + nameWidth, keybind.getY(), keybind.getX() + keybind.getDimension().width, keybind.getY() + 12, 
        		colorButton);
        
        if(keybind.getMod().getKey() == -1) {
        	theme.fontRenderer.drawString(keybind.isEditing() ? "|" : "NONE", keybind.getX() + keybind.getDimension().width / 2 + nameWidth / 2 - theme.fontRenderer.getStringWidth("NONE") / 2, keybind.getY() + 2, keybind.isEditing() ? 
        			colorString1IsEdit : 
        				colorString1IsStatic);
        }
        else
        {
        	theme.fontRenderer.drawString(keybind.isEditing() ? "|" : Keyboard.getKeyName(keybind.getMod().getKey()), keybind.getX() + keybind.getDimension().width / 2 + nameWidth / 2 - theme.fontRenderer.getStringWidth(Keyboard.getKeyName(keybind.getMod().getKey())) / 2, keybind.getY() + 2, keybind.isEditing() ? 
        			colorString2IsEdit : 
        				colorString2IsStatic);
        }
    }

    @Override
    public void doInteractions(Component component, int mouseX, int mouseY) {

    }

}
