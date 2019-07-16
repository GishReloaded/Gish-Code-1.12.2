/*******************************************************************************
 *     DarkForge a Forge Hacked Client
 *     Copyright (C) 2017  Hexeption (Keir Davis)
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/

package i.gishreloaded.gishcode.gui.click.theme.dark;

import java.awt.Color;

import org.lwjgl.input.Keyboard;

import i.gishreloaded.gishcode.gui.click.base.Component;
import i.gishreloaded.gishcode.gui.click.base.ComponentRenderer;
import i.gishreloaded.gishcode.gui.click.base.ComponentType;
import i.gishreloaded.gishcode.gui.click.elements.KeybindMods;
import i.gishreloaded.gishcode.gui.click.theme.Theme;
import i.gishreloaded.gishcode.utils.ColorUtils;
import i.gishreloaded.gishcode.utils.GLUtils;
import i.gishreloaded.gishcode.utils.RenderUtils;

/**
 * Created by Hexeption on 18/03/2017.
 */
public class DarkKeybinds extends ComponentRenderer {

    public DarkKeybinds(Theme theme) {

        super(ComponentType.KEYBIND, theme);
    }

    @Override
    public void drawComponent(Component component, int mouseX, int mouseY) {

        KeybindMods keybind = (KeybindMods) component;
        Color colorButton = new Color(0, 0, 0, 200); 
        
        int colorString = ColorUtils.colorRGB(1.0f, 1.0f, 1.0f, 1.0f);
        
        int colorString1IsEdit = ColorUtils.colorRGB(1.0f, 1.0f, 1.0f, 1.0f);
        int colorString1IsStatic = ColorUtils.colorRGB(0.6f, 0.6f, 0.6f, 1.0f);
        
        int colorString2IsEdit = ColorUtils.colorRGB(1.0f, 1.0f, 1.0f, 1.0f);
        int colorString2IsStatic = ColorUtils.colorRGB(1.0f, 1.0f, 1.0f, 1.0f);
        /*
        int colorIsSelect = ColorUtils.colorRGB(0.0f, 0.0f, 1.0f, 1.0f);
        if (GLUtils.isHovered(keybind.getX(), keybind.getY(), keybind.getDimension().width, 18, mouseX, mouseY)) {
        	RenderUtils.drawRect(keybind.getX(), keybind.getY() - 1, keybind.getX() + keybind.getDimension().width - 1, keybind.getY() + 13, 
        			colorIsSelect);
        }
        */
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
