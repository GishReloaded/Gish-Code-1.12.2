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

import i.gishreloaded.gishcode.gui.click.base.Component;
import i.gishreloaded.gishcode.gui.click.base.ComponentType;
import i.gishreloaded.gishcode.gui.click.base.Container;
import i.gishreloaded.gishcode.gui.click.listener.ComponentClickListener;
import i.gishreloaded.gishcode.hack.Hack;

/**
 * Created by Hexeption on 28/02/2017.
 */
public class ExpandingButton extends Container {

    public ArrayList<ComponentClickListener> listeners = new ArrayList<>();

    private boolean enabled = false, maximized = false;

    private int buttonHeight;

    private Hack mod;

    public ExpandingButton(int xPos, int yPos, int width, int buttonHeight, Component component, String text) {

        super(xPos, yPos, width, 0, ComponentType.EXPANDING_BUTTON, component, text);
        this.buttonHeight = buttonHeight;
    }

    public ExpandingButton(int xPos, int yPos, int width, int buttonHeight, Component component, String text, Hack mod) {

        super(xPos, yPos, width, 0, ComponentType.EXPANDING_BUTTON, component, text);
        this.buttonHeight = buttonHeight;
        this.mod = mod;
    }

    @Override
    public void render(int x, int y) {

        int height = this.buttonHeight;

        if (this.maximized) {
            for (Component component : this.getComponents()) {
                component.setxPos(getX());
                component.setyPos(getY() + height + 1);
                height += component.getDimension().height;
                component.getDimension().setSize(this.getDimension().width, component.getDimension().height);
            }
        }

        this.getDimension().setSize(this.getDimension().width, height);
        super.render(x, y);
    }

    @Override
    public void onUpdate() {

        int height = this.buttonHeight;

        if (this.maximized) {
            for (Component component : this.getComponents()) {
                component.setyPos(getY() + height + 1);
                height += component.getDimension().height;
                component.getDimension().setSize(this.getDimension().width, component.getDimension().height);
            }
        }

        this.getDimension().setSize(this.getDimension().width, height);
    }

    @Override
    public void onKeyPressed(int key, char character) {

        for (Component component : this.getComponents()) {
            component.onKeyPressed(key, character);

        }

    }

    @Override
    public void onKeyReleased(int key, char character) {

        for (Component component : this.getComponents()) {
            component.onKeyReleased(key, character);
        }
    }

    @Override
    public void onMouseDrag(int x, int y) {

        if (this.isMouseOver(x, y)) {
            for (Component component : this.getComponents()) {
                if (component.isMouseOver(x, y)) {
                    component.onMouseDrag(x, y);
                }
            }
        }
    }

    @Override
    public void onMouseRelease(int x, int y, int buttonID) {

        if (this.isMouseOver(x, y)) {
            for (Component component : this.getComponents()) {
                if (component.isMouseOver(x, y)) {
                    component.onMouseRelease(x, y, buttonID);
                }
            }
        }
    }

    @Override
    public void onMousePress(int x, int y, int buttonID) {

        if (this.isMouseOverButton(x, y)) {
            if (buttonID == 0) {
                this.enabled = !this.enabled;

                for (ComponentClickListener listener : listeners) {
                    listener.onComponenetClick(this, buttonID);
                }
            } else if (buttonID == 1) {
                maximized = !maximized;
            }
        } else if (this.isMouseOver(x, y)) {
            for (Component component : this.getComponents()) {
                if (component.isMouseOver(x, y)) {
                    component.onMousePress(x, y, buttonID);
                }
            }
        }
    }

    public boolean isMouseOverButton(int x, int y) {

        return (x >= this.getX() && y >= this.getY() && x <= this.getX() + this.getDimension().width && y <= this.getY() + buttonHeight);
    }

    public void addListner(ComponentClickListener listener) {

        listeners.add(listener);
    }

    public ArrayList<ComponentClickListener> getListeners() {

        return listeners;
    }

    public boolean isEnabled() {

        return enabled;
    }

    public void setEnabled(boolean enabled) {

        this.enabled = enabled;
    }

    public boolean isMaximized() {

        return maximized;
    }

    public void setMaximized(boolean maximized) {

        this.maximized = maximized;
    }

    public int getButtonHeight() {

        return buttonHeight;
    }

    public void setButtonHeight(int buttonHeight) {

        this.buttonHeight = buttonHeight;
    }

    public Hack getMod() {

        return mod;
    }
}
