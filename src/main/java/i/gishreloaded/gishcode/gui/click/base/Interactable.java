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

import java.awt.*;

/**
 * Created by Hexeption on 27/02/2017.
 */
public class Interactable {

    private int xPos, yPos, yBase;

    private Dimension dimension;

    public Interactable(int xPos, int yPos, int width, int height) {

        this.xPos = xPos;
        this.yPos = yPos;
        this.dimension = new Dimension(width, height);
    }

    public void onMousePress(int x, int y, int buttonID) {

    }

    public void onMouseRelease(int x, int y, int buttonID) {

    }

    public void onMouseDrag(int x, int y) {

    }

    public void onMouseScroll(int scroll) {

    }

    public boolean isMouseOver(int x, int y) {

        return (x >= xPos && y >= yPos && x <= xPos + dimension.width && y <= yPos + dimension.height);
    }

    public void onKeyPressed(int key, char character) {

    }

    public void onKeyReleased(int key, char character) {

    }

    public int getX() {

        return xPos;
    }

    public void setxPos(int xPos) {

        this.xPos = xPos;
    }

    public int getY() {

        return yPos;
    }

    public void setyPos(int yPos) {

        this.yPos = yPos;
    }

    public int getyBase() {

        return yBase;
    }

    public void setyBase(int yBase) {

        this.yBase = yBase;
    }

    public Dimension getDimension() {

        return dimension;
    }

    public void setDimension(Dimension dimension) {

        this.dimension = dimension;
    }
}
