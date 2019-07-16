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
import i.gishreloaded.gishcode.gui.click.elements.Slider;
import i.gishreloaded.gishcode.gui.click.theme.Theme;
import i.gishreloaded.gishcode.hack.hacks.ClickGui;
import i.gishreloaded.gishcode.utils.ColorUtils;
import i.gishreloaded.gishcode.utils.GLUtils;
import i.gishreloaded.gishcode.utils.RenderUtils;

/**
 * Created by Hexeption on 28/02/2017.
 */
public class DarkSlider extends ComponentRenderer {

    public DarkSlider(Theme theme) {

        super(ComponentType.SLIDER, theme);
    }

    @Override
    public void drawComponent(Component component, int mouseX, int mouseY) {

        Slider slider = (Slider) component;
        int width = (int) ((slider.getDimension().getWidth()) * slider.getPercent());
        
        int colorStringValueName = ColorUtils.colorRGB(0.5f, 0.5f, 0.5f, 1.0f);
        int colorStringValue = ColorUtils.colorRGB(1.0f, 1.0f, 1.0f, 1.0f);
        
        int colorSliderRect = ColorUtils.colorRGB(1.0f, 1.0f, 1.0f, 1.0f);   
        int colorSlider = ClickGui.color.getRGB();
        /*
        int colorIsSelect = ColorUtils.colorRGB(0.0f, 0.0f, 1.0f, 1.0f);
        if (GLUtils.isHovered(slider.getX(), slider.getY(), slider.getDimension().width, 18, mouseX, mouseY)) {
        	RenderUtils.drawRect(slider.getX(), slider.getY(), slider.getX() + slider.getDimension().width - 1, slider.getY() + 18, 
        			colorIsSelect);
        }
        */
        GLUtils.glColor(ColorUtils.colorRGB(1.0f, 1.0f, 1.0f, 1.0f));
        
        theme.fontRenderer.drawString(slider.getText(), slider.getX() + 4, slider.getY() + 2, 
        		colorStringValueName);
        
        theme.fontRenderer.drawString(slider.getValue() + "", slider.getX() + slider.getDimension().width - theme.fontRenderer.getStringWidth(slider.getValue() + "") - 2, slider.getY() + 2, 
        		colorStringValue);
        
        RenderUtils.drawRect(slider.getX(), slider.getY() + slider.getDimension().height / 2 + 3, slider.getX() + (width) + 3, (slider.getY() + slider.getDimension().height / 2) + 6, 
        		colorSliderRect);
        
        RenderUtils.drawRect(slider.getX(), slider.getY() + slider.getDimension().height / 2 + 3, slider.getX() + (width), (slider.getY() + slider.getDimension().height / 2) + 6, 
        		colorSlider);
    }

    @Override
    public void doInteractions(Component component, int mouseX, int mouseY) {

    }
}
