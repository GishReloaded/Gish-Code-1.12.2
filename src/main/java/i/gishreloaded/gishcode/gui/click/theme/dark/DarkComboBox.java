package i.gishreloaded.gishcode.gui.click.theme.dark;

import static org.lwjgl.opengl.GL11.GL_BLEND;
import static org.lwjgl.opengl.GL11.GL_CULL_FACE;
import static org.lwjgl.opengl.GL11.GL_LINES;
import static org.lwjgl.opengl.GL11.GL_LINE_LOOP;
import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glColor4f;
import static org.lwjgl.opengl.GL11.glDisable;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glLineWidth;
import static org.lwjgl.opengl.GL11.glTranslated;
import static org.lwjgl.opengl.GL11.glVertex2d;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;

import org.lwjgl.input.Mouse;

import i.gishreloaded.gishcode.gui.click.base.Component;
import i.gishreloaded.gishcode.gui.click.base.ComponentRenderer;
import i.gishreloaded.gishcode.gui.click.base.ComponentType;
import i.gishreloaded.gishcode.gui.click.elements.ComboBox;
import i.gishreloaded.gishcode.gui.click.theme.Theme;
import i.gishreloaded.gishcode.utils.visual.GLUtils;

public class DarkComboBox extends ComponentRenderer {

    public DarkComboBox(Theme theme) {

        super(ComponentType.COMBO_BOX, theme);
    }

    @Override
    public void drawComponent(Component component, int mouseX, int mouseY) {

        ComboBox comboBox = (ComboBox) component;
        Dimension area = comboBox.getDimension();

        glEnable(GL_BLEND);
        glDisable(GL_CULL_FACE);
        glDisable(GL_TEXTURE_2D);

        glTranslated(1 * comboBox.getX(), 1 * comboBox.getY(), 0);
        int maxWidth = 0;
        for (String element : comboBox.getElements())
            maxWidth = Math.max(maxWidth, theme.getFontRenderer().getStringWidth(element));
        int extendedHeight = 0;
        if (comboBox.isSelected()) {
            String[] elements = comboBox.getElements();
            for (int i = 0; i < elements.length - 1; i++)
                extendedHeight += theme.getFontRenderer().FONT_HEIGHT + 2;
            extendedHeight += 2;
        }

        comboBox.setDimension(new Dimension(maxWidth + 8 + theme.getFontRenderer().FONT_HEIGHT, theme.getFontRenderer().FONT_HEIGHT));

        GLUtils.glColor(new Color(2, 2, 2, 40));
        glBegin(GL_QUADS);
        {
            glVertex2d(0, 0);
            glVertex2d(area.width, 0);
            glVertex2d(area.width, area.height + extendedHeight);
            glVertex2d(0, area.height + extendedHeight);
        }
        glEnd();
        Point mouse = new Point(mouseX, mouseY);
        glColor4f(0.0f, 0.0f, 0.0f, Mouse.isButtonDown(0) ? 0.5f : 0.3f);
        if (GLUtils.isHovered(comboBox.getX(), comboBox.getY(), area.width, area.height, mouseX, mouseY)) {
            glBegin(GL_QUADS);
            {
                glVertex2d(0, 0);
                glVertex2d(area.width, 0);
                glVertex2d(area.width, area.height);
                glVertex2d(0, area.height);
            }
            glEnd();
        } else if (comboBox.isSelected() && mouse.x >= comboBox.getX() && mouse.x <= comboBox.getX() + area.width) {
            int offset = area.height;
            String[] elements = comboBox.getElements();
            for (int i = 0; i < elements.length; i++) {
                if (i == comboBox.getSelectedIndex())
                    continue;
                int height = theme.getFontRenderer().FONT_HEIGHT + 2;
                if ((comboBox.getSelectedIndex() == 0 ? i == 1 : i == 0) || (comboBox.getSelectedIndex() == elements.length - 1 ? i == elements.length - 2 : i == elements.length - 1))
                    height++;
                if (mouse.y >= comboBox.getY() + offset && mouse.y <= comboBox.getY() + offset + height) {
                    glBegin(GL_QUADS);
                    {
                        glVertex2d(0, offset);
                        glVertex2d(0, offset + height);
                        glVertex2d(area.width, offset + height);
                        glVertex2d(area.width, offset);
                    }
                    glEnd();
                    break;
                }
                offset += height;
            }
        }
        int height = theme.getFontRenderer().FONT_HEIGHT + 4;
        glColor4f(0.0f, 0.0f, 0.0f, 0.3f);
        glBegin(GL_TRIANGLES);
        {
            if (comboBox.isSelected()) {
                glVertex2d(maxWidth + 4 + height / 2d, height / 3d);
                glVertex2d(maxWidth + 4 + height / 3d, 2d * height / 3d);
                glVertex2d(maxWidth + 4 + 2d * height / 3d, 2d * height / 3d);
            } else {
                glVertex2d(maxWidth + 4 + height / 3d, height / 3d);
                glVertex2d(maxWidth + 4 + 2d * height / 3d, height / 3d);
                glVertex2d(maxWidth + 4 + height / 2d, 2d * height / 3d);
            }
        }
        glEnd();
        glLineWidth(1.0f);
        glColor4f(0.0f, 0.0f, 0.0f, 1.0f);
        if (comboBox.isSelected()) {
            glBegin(GL_LINES);
            {
                glVertex2d(2, area.height);
                glVertex2d(area.width - 2, area.height);
            }
            glEnd();
        }
        glBegin(GL_LINES);
        {
            glVertex2d(maxWidth + 4, 2);
            glVertex2d(maxWidth + 4, area.height - 2);
        }
        glEnd();
        glBegin(GL_LINE_LOOP);
        {
            if (comboBox.isSelected()) {
                glVertex2d(maxWidth + 4 + height / 2d, height / 3d);
                glVertex2d(maxWidth + 4 + height / 3d, 2d * height / 3d);
                glVertex2d(maxWidth + 4 + 2d * height / 3d, 2d * height / 3d);
            } else {
                glVertex2d(maxWidth + 4 + height / 3d, height / 3d);
                glVertex2d(maxWidth + 4 + 2d * height / 3d, height / 3d);
                glVertex2d(maxWidth + 4 + height / 2d, 2d * height / 3d);
            }
        }
        glEnd();
        glEnable(GL_TEXTURE_2D);

        String text = comboBox.getSelectedElement();
        theme.getFontRenderer().drawString(text, 2, area.height / 2 - theme.getFontRenderer().FONT_HEIGHT / 4, -1);
        if (comboBox.isSelected()) {
            int offset = area.height + 2;
            String[] elements = comboBox.getElements();
            for (int i = 0; i < elements.length; i++) {
                if (i == comboBox.getSelectedIndex())
                    continue;
                theme.getFontRenderer().drawString(elements[i], 2, offset, -1);
                offset += theme.getFontRenderer().FONT_HEIGHT + 2;
            }
        }

        glEnable(GL_CULL_FACE);
        glDisable(GL_BLEND);
        glTranslated(-1 * comboBox.getX(), -1 * comboBox.getY(), 0);
    }

    @Override
    public void doInteractions(Component component, int mouseX, int mouseY) {

        ComboBox comboBox = (ComboBox) component;

    }
}
