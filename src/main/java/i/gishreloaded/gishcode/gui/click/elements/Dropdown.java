package i.gishreloaded.gishcode.gui.click.elements;

import i.gishreloaded.gishcode.gui.click.base.Component;
import i.gishreloaded.gishcode.gui.click.base.ComponentType;
import i.gishreloaded.gishcode.gui.click.base.Container;

public class Dropdown extends Container {

    private boolean maximized = false;

    private int dropdownHeight;

    public Dropdown(int xPos, int yPos, int width, int dropdownHeight, Component component, String text) {

        super(xPos, yPos, width, 0, ComponentType.DROPDOWN, component, text);
        this.dropdownHeight = dropdownHeight;
    }

    @Override
    public void render(int x, int y) {

        int height = this.dropdownHeight;

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

        int height = this.dropdownHeight;
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

        if (x >= this.getX() && y >= this.getY() && x <= this.getX() + this.getDimension().width && y <= this.getY() + dropdownHeight) {
            if (buttonID == 1) {
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

    public boolean isMaximized() {

        return maximized;
    }

    public void setMaximized(boolean maximized) {

        this.maximized = maximized;
    }

    public int getDropdownHeight() {

        return dropdownHeight;
    }

    public void setDropdownHeight(int dropdownHeight) {

        this.dropdownHeight = dropdownHeight;
    }
}
