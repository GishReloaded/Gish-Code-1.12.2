package i.gishreloaded.gishcode.gui.click.base;

import i.gishreloaded.gishcode.gui.click.ClickGui;

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
