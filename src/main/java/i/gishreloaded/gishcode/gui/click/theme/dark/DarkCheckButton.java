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

import i.gishreloaded.gishcode.gui.click.base.Component;
import i.gishreloaded.gishcode.gui.click.base.ComponentRenderer;
import i.gishreloaded.gishcode.gui.click.base.ComponentType;
import i.gishreloaded.gishcode.gui.click.elements.CheckButton;
import i.gishreloaded.gishcode.gui.click.theme.Theme;
import i.gishreloaded.gishcode.hack.hacks.ClickGui;
import i.gishreloaded.gishcode.utils.ColorUtils;
import i.gishreloaded.gishcode.utils.GLUtils;
import i.gishreloaded.gishcode.utils.MathUtils;
import i.gishreloaded.gishcode.utils.RenderUtils;
import i.gishreloaded.gishcode.value.Mode;

/**
 * Created by Hexeption on 28/02/2017.
 */
public class DarkCheckButton extends ComponentRenderer {

    public DarkCheckButton(Theme theme) {

        super(ComponentType.CHECK_BUTTON, theme);
    }

    @Override
    public void drawComponent(Component component, int mouseX, int mouseY) {

        CheckButton button = (CheckButton) component;
        String text = button.getText();
        
        int colorStringIsEnabled = ColorUtils.colorRGB(1.0f, 1.0f, 1.0f, 1.0f);
        int colorStringIsDisabled = ColorUtils.colorRGB(0.5f, 0.5f, 0.5f, 1.0f);
        
        /*
        int colorIsSelect = ColorUtils.colorRGB(0.0f, 0.0f, 1.0f, 1.0f);
        if (GLUtils.isHovered(button.getX(), button.getY(), button.getDimension().width, 18, mouseX, mouseY)) {
        	RenderUtils.drawRect(button.getX(), button.getY() - 1, button.getX() + button.getDimension().width - 1, button.getY() + 17, 
        			colorIsSelect);
        }
        */
        
        if(button.getModeValue() == null) {
        	theme.fontRenderer.drawString("> " + text, button.getX() + 5, MathUtils.getMiddle(button.getY(), button.getY() + button.getDimension().height) - theme.fontRenderer.FONT_HEIGHT / 3 - 1, 
        			button.isEnabled() ? colorStringIsEnabled : colorStringIsDisabled);
        	return;
        }
        
        for(Mode mode : button.getModeValue().getModes()) {
    		if(mode.getName().equals(text)) {
    			theme.fontRenderer.drawString("- " + text, button.getX() + 5, MathUtils.getMiddle(button.getY(), button.getY() + button.getDimension().height) - theme.fontRenderer.FONT_HEIGHT / 3 - 1, 
    					mode.isToggled() ? colorStringIsEnabled : colorStringIsDisabled);
    		}
        }
    }

    @Override
    public void doInteractions(Component component, int mouseX, int mouseY) {

    }
}
