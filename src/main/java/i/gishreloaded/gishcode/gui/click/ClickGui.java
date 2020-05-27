package i.gishreloaded.gishcode.gui.click;

import java.util.ArrayList;

import javax.vecmath.Vector2f;

import i.gishreloaded.gishcode.gui.click.base.Component;
import i.gishreloaded.gishcode.gui.click.base.Container;
import i.gishreloaded.gishcode.gui.click.elements.Frame;
import i.gishreloaded.gishcode.gui.click.elements.Slider;
import i.gishreloaded.gishcode.gui.click.theme.Theme;
import i.gishreloaded.gishcode.hack.Hack;
import i.gishreloaded.gishcode.managers.HackManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;

public class ClickGui extends ClickGuiScreen {

    //Bug: Gui Scale fucks up

    private static Theme theme;

    private static ArrayList<Frame> frames = new ArrayList<Frame>();

    private Frame currentFrame;

    private boolean dragging = false;

    private Vector2f draggingOffset;

    public static void renderPinned() {

        Minecraft mc = Minecraft.getMinecraft();
        ScaledResolution scaledResolution = new ScaledResolution(mc);
        float scale = scaledResolution.getScaleFactor() / (float) Math.pow(scaledResolution.getScaleFactor(), 2D);
        GlStateManager.pushMatrix();
        GlStateManager.translate(0, 0, 1000);
        GlStateManager.scale(scale * 2, scale * 2, scale * 2);

        for (Frame frame : frames) {
            if (frame.isPinned()) {
                frame.render(mouse[0], mouse[1]);
            }
        }

        GlStateManager.popMatrix();
    }

    public static Theme getTheme() {
        return theme;
    }

    public void setTheme(Theme theme) {

        ClickGui.theme = theme;
    }

    public void render() {
        for (Frame frame : frames) {
            frame.render(mouse[0], mouse[1]);
        }
    }

    public void onMouseUpdate(int x, int y) {
        for (Frame frame : frames) {
            for (Component component : frame.getComponents()) {
                if (component.isMouseOver(x, y)) {
                    component.onMouseDrag(x, y);
                } else {
                    if (component instanceof Slider) {
                        Slider s = (Slider) component;
                        s.dragging = false;
                    }
                }
            }
        }

        if (dragging && this.currentFrame != null) {
            int yOffset = (int) ((y - this.draggingOffset.getY()) - currentFrame.getY());
            currentFrame.setxPos((int) (x - this.draggingOffset.getX()));
            currentFrame.setyPos((int) (y - this.draggingOffset.getY()));

            for (Component component : currentFrame.getComponents()) {
                component.setyBase(component.getyBase() + yOffset);

                if (component instanceof Container) {
                    Container container = (Container) component;
                    int height = 0;

                    for (Component component1 : container.getComponents()) {
                        component1.setxPos(component.getX());
                        component1.setyPos(component.getY());
                        height += component1.getDimension().height;
                    }
                }
            }
        }
    }

    public boolean onMouseScroll(int ammount) {
    	boolean overFrame = false;
        for (Frame frame : frames) {
            if (frame.isMouseOver(mouse[0], mouse[1])) {
                frame.scrollFrame(ammount * 4);
                overFrame = true;
            }

            frame.onMouseScroll(ammount * 4);
        }
        return overFrame;
    }

    public void onMouseRelease(int x, int y) {
        for (Frame frame : frames) {
            if (frame.isMouseOver(x, y)) {
                this.currentFrame = frame;

                if (frame.isMouseOverBar(x, y)) {
                    this.dragging = false;
                }

                frame.onMouseRelease(x, y, 0);
            }
        }
    }

    public void onMouseClick(int x, int y, int buttonID) {
        for (Frame frame : frames) {
            if (frame.isMouseOver(x, y)) {
                this.currentFrame = frame;

                if (frame.isMouseOverBar(x, y)) {
                    this.dragging = true;
                    this.draggingOffset = new Vector2f(x - frame.getX(), y - frame.getY());
                }

                frame.onMousePress(x, y, buttonID);
            }
        }
    }

    public void onUpdate() {
        for (Frame frame : frames) {
            frame.updateComponents();
        }
    }

    public void addFrame(Frame frame) {

        frames.add(frame);
    }

    public ArrayList<Frame> getFrames() {

        return frames;
    }


    public void onKeyRelease(int eventKey, char eventCharacter) {

        for (Frame frame : frames) {
            frame.onKeyReleased(eventKey, eventCharacter);
        }
    }

    public void onkeyPressed(int eventKey, char eventCharacter) {


        for (Frame frame : frames) {

            frame.onKeyPressed(eventKey, eventCharacter);
        }
    }
}
