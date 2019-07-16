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
import i.gishreloaded.gishcode.utils.GLUtils;

/**
 * Created by Hexeption on 27/02/2017.
 */
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
        //theme.icons.render(x, y, size, size, expanded ? 64F / 256F : 0F, 0F, expanded ? 128F / 256F : 64F / 256F, 64F / 256F);

    }

    public void drawPin(int x, int y, int size, boolean expanded, int color) {

        GLUtils.glColor(color);
//        theme.icons.render(x, y, size, size, 64F / 256F, 128F / 256F, 128F / 256F, 64F / 256F);
        //if (!expanded)
            //theme.icons.render(x, y, 16, 16, 192f / 256f, 128F / 256F, 128F / 256F, 64F / 256F);
        //else
            //theme.icons.render(x, y, 16, 16, 192f / 256f, 0, 128F / 256F, 64F / 256F);

    }

    public void drawArrow(int x, int y, int size, boolean right, int color) {

        GLUtils.glColor(color);
       //theme.icons.render(x, y, size, size, 0, right ? 0 : 64F / 256F, 64F / 256F, right ? 64F / 256F : 128F / 256F);
    }

    public void drawArrow(int x, int y, int size, boolean right) {

        drawArrow(x, y, size, right, 0xFFFFFFFF);
    }

    public void renderToolTip(Component component, String tooltip, Point mouse) {

        GL11.glPushMatrix();
        GL11.glDisable(GL11.GL_SCISSOR_TEST);
        int aboveMouse = 8;
        int width = theme.getFontRenderer().getStringWidth(tooltip);
        GLUtils.glColor(tooltipColor.brighter());
        drawRect(mouse.x - 1, mouse.y - aboveMouse - 1, mouse.x + width + 5, mouse.y + theme.getFontRenderer().FONT_HEIGHT - aboveMouse + 3, 1.0F);
        GLUtils.glColor(tooltipColor);
        drawFilledRect(mouse.x, mouse.y - aboveMouse, mouse.x + width + 4, mouse.y + theme.getFontRenderer().FONT_HEIGHT - aboveMouse + 2);
        theme.getFontRenderer().drawStringWithShadow(tooltip, mouse.x + 2, mouse.y - aboveMouse + 2, 16777215);
        GL11.glEnable(GL11.GL_SCISSOR_TEST);
        GL11.glPopMatrix();

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
