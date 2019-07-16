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

import org.lwjgl.input.Keyboard;

import i.gishreloaded.gishcode.Wrapper;
import i.gishreloaded.gishcode.gui.click.base.Component;
import i.gishreloaded.gishcode.gui.click.base.ComponentType;
import i.gishreloaded.gishcode.hack.Hack;

/**
 * Created by Hexeption on 18/03/2017.
 */
public class KeybindMods extends Component {

    private Hack mod;

    private boolean editing;

    public KeybindMods(int xPos, int yPos, int width, int height, Component component, Hack mod) {

        super(xPos, yPos, width, height, ComponentType.KEYBIND, component, "");
        this.mod = mod;
    }

    @Override
    public void onUpdate() {
        if (Keyboard.getEventKeyState()) {
            if (editing) {
                if (Keyboard.getEventKey() == Keyboard.KEY_DELETE)
                    mod.setKey(-1);
                else
                    mod.setKey(Keyboard.getEventKey());
                editing = false;
            }
        }
    }


    @Override
    public void onMousePress(int x, int y, int buttonID) {
        if (x > this.getX() + Wrapper.INSTANCE.fontRenderer().getStringWidth("Key") + 6 && x < this.getX() + this.getDimension().width && y > this.getY() && y < this.getY() + this.getDimension().height) {
            editing = !editing;
        }
    }

    public Hack getMod() {
        return mod;
    }

    public boolean isEditing() {
        return editing;
    }

    public void setEditing(boolean editing) {
        this.editing = editing;
    }
}
