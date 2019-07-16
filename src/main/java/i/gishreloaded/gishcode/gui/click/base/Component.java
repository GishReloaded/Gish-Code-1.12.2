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

package i.gishreloaded.gishcode.gui.click.base;

import i.gishreloaded.gishcode.gui.click.ClickGui;

/**
 * Created by Hexeption on 27/02/2017.
 */
public class Component extends Interactable {

    private ComponentType componentType;

    private Component component;

    private String text;

    public Component(int xPos, int yPos, int width, int height, ComponentType componentType, Component component, String text) {

        super(xPos, yPos, width, height);
        this.componentType = componentType;
        this.component = component;
        this.text = text;
    }

    public void render(int x, int y) {

        ClickGui.getTheme().getRenderer().get(componentType).drawComponent(this, x, y);
    }

    public void onUpdate() {

    }

    public ComponentType getComponentType() {

        return componentType;
    }

    public void setComponentType(ComponentType componentType) {

        this.componentType = componentType;
    }

    public Component getComponent() {

        return component;
    }

    public void setComponent(Component component) {

        this.component = component;
    }

    public String getText() {

        return text;
    }

    public void setText(String text) {

        this.text = text;
    }
}
