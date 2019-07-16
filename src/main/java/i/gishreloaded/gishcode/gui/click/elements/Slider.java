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

import org.lwjgl.input.Mouse;

import i.gishreloaded.gishcode.gui.click.ClickGui;
import i.gishreloaded.gishcode.gui.click.base.Component;
import i.gishreloaded.gishcode.gui.click.base.ComponentType;
import i.gishreloaded.gishcode.gui.click.listener.SliderChangeListener;

public class Slider extends Component {

    public boolean dragging = false;

    public double min, max, value;

    public double percent = 0;

    private ArrayList<SliderChangeListener> listeners = new ArrayList<>();


    public Slider(double min, double max, double value, Component component, String text) {

        super(0, 0, 100, 20, ComponentType.SLIDER, component, text);
        this.min = min;
        this.max = max;
        this.value = value;
        this.percent = value / (max - min);
    }

    public void addListener(SliderChangeListener listener) {

        listeners.add(listener);
    }

    @Override
    public void onMousePress(int x, int y, int buttonID) {

        x -= this.getX();
        int x1 = (int) (getDimension().getWidth());
        percent = (double) x / (double) x1;
        value = this.round(((max - min) * percent) + min, 2);
        dragging = true;

        fireListeners();
    }

    private void fireListeners() {

        for (SliderChangeListener listener : listeners) {
            listener.onSliderChange(this);
        }
    }

    @Override
    public void onMouseRelease(int x, int y, int buttonID) {

        dragging = false;
    }

    @Override
    public void onUpdate() {

        int[] mouse = ClickGui.mouse;
        this.dragging = false;

        if (dragging && !isMouseOver(mouse[0], mouse[1])) {
            if (mouse[0] <= this.getX()) {
                this.percent = 0;
                this.value = this.min;
                fireListeners();
            } else {
                this.percent = 1;
                this.value = this.max;
                fireListeners();
            }
        }

        if (Mouse.isButtonDown(0) && this.isMouseOver(mouse[0], mouse[1])) {
            this.dragging = true;
        }
    }

    @Override
    public void onMouseDrag(int x, int y) {

        if (dragging) {
            x -= this.getX();
            int x1 = (int) (getDimension().getWidth());
            percent = (double) x / (double) x1;
            value = this.round(((max - min) * percent) + min, 2);
            fireListeners();
        }
    }

    public ArrayList<SliderChangeListener> getListeners() {

        return listeners;
    }


    public boolean isDragging() {

        return dragging;
    }

    public void setDragging(boolean dragging) {

        this.dragging = dragging;
    }

    public double getMin() {

        return min;
    }

    public void setMin(double min) {

        this.min = min;
    }

    public double getMax() {

        return max;
    }

    public void setMax(double max) {

        this.max = max;
    }

    public double getValue() {

        return value;
    }

    public void setValue(double value) {

        this.value = value;
    }

    public double getPercent() {

        return percent;
    }

    public void setPercent(double percent) {

        this.percent = percent;
    }

    private double round(double valueToRound, int numberOfDecimalPlaces) {

        double multipicationFactor = Math.pow(10, numberOfDecimalPlaces);
        double interestedInZeroDPs = valueToRound * multipicationFactor;
        return Math.round(interestedInZeroDPs) / multipicationFactor;
    }
}
