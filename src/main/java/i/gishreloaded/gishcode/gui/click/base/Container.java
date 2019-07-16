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

import java.util.ArrayList;

/**
 * Created by Hexeption on 27/02/2017.
 */
public class Container extends Component {

    private ArrayList<Component> components = new ArrayList<>();


    public Container(int xPos, int yPos, int width, int height, ComponentType componentType, Component component, String text) {

        super(xPos, yPos, width, height, componentType, component, text);
    }

    public void addComponent(Component c) {

        components.add(c);
    }

    public void removeCompoent(Component c) {

        components.remove(c);
    }

    public void renderChildren(int mouseX, int mouseY) {

        for (Component c : getComponents()) {
            c.render(mouseX, mouseY);
        }
    }

    public ArrayList<Component> getComponents() {

        return components;
    }

}
