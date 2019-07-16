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
import i.gishreloaded.gishcode.gui.click.elements.Dropdown;
import i.gishreloaded.gishcode.gui.click.theme.Theme;
import i.gishreloaded.gishcode.hack.hacks.ClickGui;

/**
 * Created by Hexeption on 24/03/2017.
 */
public class DarkDropDown extends ComponentRenderer {

    public DarkDropDown(Theme theme) {

        super(ComponentType.DROPDOWN, theme);
    }

    @Override
    public void drawComponent(Component component, int mouseX, int mouseY) {

        Dropdown dropdown = (Dropdown) component;
        String text = dropdown.getText();

        theme.fontRenderer.drawString(text, dropdown.getX() + 5, dropdown.getY() + (dropdown.getDropdownHeight() / 2 - theme.fontRenderer.FONT_HEIGHT / 4), 
        		ClickGui.color.getRGB());

        if (dropdown.isMaximized()) {
            dropdown.renderChildren(mouseX, mouseY);
        }
    }

    @Override
    public void doInteractions(Component component, int mouseX, int mouseY) {

    }
}
