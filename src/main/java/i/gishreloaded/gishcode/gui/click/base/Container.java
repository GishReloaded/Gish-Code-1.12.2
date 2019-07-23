package i.gishreloaded.gishcode.gui.click.base;

import java.util.ArrayList;

public class Container extends Component {

    private ArrayList<Component> components = new ArrayList<Component>();


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
