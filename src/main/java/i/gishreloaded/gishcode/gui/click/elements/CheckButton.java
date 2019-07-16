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
import i.gishreloaded.gishcode.gui.click.listener.CheckButtonClickListener;
import i.gishreloaded.gishcode.hack.Hack;
import i.gishreloaded.gishcode.managers.HackManager;
import i.gishreloaded.gishcode.value.Mode;
import i.gishreloaded.gishcode.value.ModeValue;
import i.gishreloaded.gishcode.value.Value;

/**
 * Created by Hexeption on 28/02/2017.
 */
public class CheckButton extends Component {

    public ArrayList<CheckButtonClickListener> listeners = new ArrayList<>();

    private boolean enabled = false;
    private ModeValue modeValue = null;

    public CheckButton(int xPos, int yPos, int width, int height, Component component, String text, boolean enabled, ModeValue modeValue) {

        super(xPos, yPos, width, height, ComponentType.CHECK_BUTTON, component, text);
        this.enabled = enabled;
        this.modeValue = modeValue;
    }

    @Override
    public void onMousePress(int x, int y, int buttonID) {
    	if(modeValue != null) {
    		for(Mode mode : modeValue.getModes()) {
    			mode.setToggled(false);
    		}
    		this.enabled = true;
    	} 
    	else {
    		this.enabled = !this.enabled;
    	}
        for (CheckButtonClickListener listener : listeners) {
            listener.onCheckButtonClick(this);
        }
    }

    public ArrayList<CheckButtonClickListener> getListeners() {

        return listeners;
    }

    public void addListeners(CheckButtonClickListener listener) {

        listeners.add(listener);
    }

    public boolean isEnabled() {

        return enabled;
    }
    
    public ModeValue getModeValue() {
		return modeValue;
	}

    public void setEnabled(boolean enabled) {

        this.enabled = enabled;
    }
}
