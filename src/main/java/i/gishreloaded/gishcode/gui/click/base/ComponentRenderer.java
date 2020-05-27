package i.gishreloaded.gishcode.gui.click.base;

import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glBlendFunc;
import static org.lwjgl.opengl.GL11.glDisable;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glVertex3f;

import java.awt.Color;
import java.awt.Point;

import org.lwjgl.opengl.GL11;

import i.gishreloaded.gishcode.gui.click.theme.Theme;
import i.gishreloaded.gishcode.hack.hacks.ClickGui;
import i.gishreloaded.gishcode.utils.visual.ColorUtils;
import i.gishreloaded.gishcode.utils.visual.GLUtils;
import i.gishreloaded.gishcode.utils.visual.RenderUtils;
import net.minecraft.client.renderer.GlStateManager;

public abstract class ComponentRenderer {

    protected static final Color tooltipColor = new Color(0.0F, 0.5F, 1.0F, 0.75F);

    public Theme theme;

    private ComponentType type;

    public ComponentRenderer(ComponentType type, Theme theme) {

        this.type = type;

        this.theme = theme;
    }

    public abstract void drawComponent(Component component, int mouseX, int mouseY);

    public abstract void doInteractions(Component component, int mouseX, int mouseY);

    public void drawExpanded(int x, int y, int size, boolean expanded, int color) {

        GLUtils.glColor(color);
    }

    public void drawPin(int x, int y, int size, boolean expanded, int color) {

        GLUtils.glColor(color);
    }

    public void drawArrow(int x, int y, int size, boolean right, int color) {

        GLUtils.glColor(color);
    }

    public void drawArrow(int x, int y, int size, boolean right) {

        drawArrow(x, y, size, right, 0xFFFFFFFF);
    }

    public void drawFilledRect(float x, float y, float x1, float y1) {

        glEnable(3042);
        glBlendFunc(770, 771);
        glDisable(3553);
        glBegin(7);
        glVertex3f(x, y1, 1);
        glVertex3f(x1, y1, 1);
        glVertex3f(x1, y, 1);
        glVertex3f(x, y, 1);
        glEnd();
        glEnable(3553);
    }

    public void drawRect(float x, float y, float x1, float y1, float thickness) {

        drawFilledRect(x + thickness, y, x1 - thickness, y + thickness);
        drawFilledRect(x, y, x + thickness, y1);
        drawFilledRect(x1 - thickness, y, x1, y1);
        drawFilledRect(x + thickness, y1 - thickness, x1 - thickness, y1);
    }


}
