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

package i.gishreloaded.gishcode.gui.click.elements;

import java.util.ArrayList;

import i.gishreloaded.gishcode.Wrapper;
import i.gishreloaded.gishcode.gui.click.base.Component;
import i.gishreloaded.gishcode.gui.click.base.ComponentType;
import i.gishreloaded.gishcode.gui.click.base.Container;
import i.gishreloaded.gishcode.gui.click.listener.ComboBoxListener;

/**
 * Created by Hexeption on 01/04/2017.
 */
public class ComboBox extends Container {

    public ArrayList<ComboBoxListener> listeners = new ArrayList<>();

    private String[] elements;

    private int selectedIndex;

    private boolean selected;

    public ComboBox(int xPos, int yPos, int width, int height, Component component, String text, String... elements) {

        super(xPos, yPos, width, height, ComponentType.COMBO_BOX, component, text);
        this.elements = elements;
    }

    @Override
    public void onMousePress(int x, int y, int buttonID) {

        if (this.isMouseOver(x, y)) {
            if (buttonID == 1) {
                selected = !selected;
            }

            if (buttonID == 0) {
                //LogHelper.info("hu");
                int offset = getDimension().height + 2;
                String[] elements = getElements();
                for (int i = 0; i < elements.length; i++) {
                    if (i == getSelectedIndex()) {
                        continue;
                    }

                    if (y >= offset && y <= offset + Wrapper.INSTANCE.fontRenderer().FONT_HEIGHT) {
                        setSelectedIndex(i);
                        setSelected(false);
                        break;
                    }

                    offset += Wrapper.INSTANCE.fontRenderer().FONT_HEIGHT + 2;
                }

            }
        }


    }

    public String[] getElements() {

        return elements;
    }

    public void setElements(String[] elements) {

        this.elements = elements;
        selectedIndex = 0;
    }

    public int getSelectedIndex() {

        return selectedIndex;
    }

    public void setSelectedIndex(int selectedIndex) {

        this.selectedIndex = selectedIndex;
        for (ComboBoxListener listener : listeners) {
            listener.onComboBoxSelectionChange(this);
        }
    }

    public String getSelectedElement() {

        return elements[selectedIndex];
    }

    public boolean isSelected() {

        return selected;
    }

    public void setSelected(boolean selected) {

        this.selected = selected;
        for (ComboBoxListener listener : listeners) {
            listener.onComboBoxSelectionChange(this);
        }
    }

    public ArrayList<ComboBoxListener> getListeners() {

        return listeners;
    }

    public void addListeners(ComboBoxListener listeners) {

        this.listeners.add(listeners);
    }
}
