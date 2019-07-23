package i.gishreloaded.gishcode.gui.click.elements;

import java.util.ArrayList;

import i.gishreloaded.gishcode.gui.click.base.Component;
import i.gishreloaded.gishcode.gui.click.base.ComponentType;
import i.gishreloaded.gishcode.gui.click.listener.ComponentClickListener;
import i.gishreloaded.gishcode.hack.Hack;

public class Button extends Component {

    public ArrayList<ComponentClickListener> listeners = new ArrayList<ComponentClickListener>();

    private Hack mod;

    private boolean enabled = false;

    public Button(int xPos, int yPos, int width, int height, Component component, String text) {

        super(xPos, yPos, width, height, ComponentType.BUTTON, component, text);
    }

    public Button(int xPos, int yPos, int width, int height, Component component, String text, Hack mod) {

        super(xPos, yPos, width, height, ComponentType.BUTTON, component, text);
        this.mod = mod;
    }

    public void addListeners(ComponentClickListener listener) {

        listeners.add(listener);
    }

    public void onMousePress(int x, int y, int button) {

        if (button != 0) {
            return;
        }

        this.enabled = !this.enabled;

        for (ComponentClickListener listener : listeners) {
            listener.onComponenetClick(this, button);
        }
    }

    public boolean isEnabled() {

        return enabled;
    }

    public void setEnabled(boolean enabled) {

        this.enabled = enabled;
    }

    public ArrayList<ComponentClickListener> getListeners() {

        return listeners;
    }

    public Hack getMod() {

        return mod;
    }
}
