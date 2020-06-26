package i.gishreloaded.gishcode.gui.click.theme.dark;

import java.awt.Color;
import java.awt.Point;

import i.gishreloaded.gishcode.gui.Tooltip;
import i.gishreloaded.gishcode.gui.click.ClickGuiScreen;
import i.gishreloaded.gishcode.gui.click.base.Component;
import i.gishreloaded.gishcode.gui.click.base.ComponentRenderer;
import i.gishreloaded.gishcode.gui.click.base.ComponentType;
import i.gishreloaded.gishcode.gui.click.elements.ExpandingButton;
import i.gishreloaded.gishcode.gui.click.theme.Theme;
import i.gishreloaded.gishcode.hack.hacks.ClickGui;
import i.gishreloaded.gishcode.managers.HackManager;
import i.gishreloaded.gishcode.utils.visual.ColorUtils;
import i.gishreloaded.gishcode.utils.visual.GLUtils;
import i.gishreloaded.gishcode.utils.visual.RenderUtils;

public class DarkExpandingButton extends ComponentRenderer {

    public DarkExpandingButton(Theme theme) {
        super(ComponentType.EXPANDING_BUTTON, theme);
    }

    @Override
    public void drawComponent(Component component, int mouseX, int mouseY) {
        ExpandingButton button = (ExpandingButton) component;
        String text = button.getText();
        
        int mainColor = ClickGui.isLight ? ColorUtils.color(255, 255, 255, 155) : ColorUtils.color(0, 0, 0, 155);
        int mainColorInv = ClickGui.isLight ? ColorUtils.color(0, 0, 0, 255) : ColorUtils.color(255, 255, 255, 255);
        
        if (button.isMaximized()) {
        	RenderUtils.drawRect(button.getX(), button.getY(), button.getX() + button.getDimension().width - 1, button.getY() + button.getDimension().height - 1, 
            		mainColor);}
        if (button.isEnabled()) {
            RenderUtils.drawRect(button.getX(), button.getY(), button.getX() + button.getDimension().width - 1, button.getY() + 14, 
            		mainColor);
            RenderUtils.drawRect(button.getX(), button.getY(), button.getX() + button.getDimension().width - 95, button.getY() + 14, 
            		ClickGui.getColor());
            theme.fontRenderer.drawString(text, button.getX() + button.getDimension().width / 2 - theme.fontRenderer.getStringWidth(text) / 2, button.getY() + (button.getButtonHeight() / 2 - theme.fontRenderer.FONT_HEIGHT / 4) - 1, 
            		ClickGui.getColor());
        } 
        else 
        {
            RenderUtils.drawRect(button.getX(), button.getY(), button.getX() + button.getDimension().width - 1, button.getY() + 14, 
            		mainColor);
            theme.fontRenderer.drawString(text, button.getX() + button.getDimension().width / 2 - theme.fontRenderer.getStringWidth(text) / 2, button.getY() + (button.getButtonHeight() / 2 - theme.fontRenderer.FONT_HEIGHT / 4) - 1, 
            		mainColorInv);
        }

        if (button.isMaximized()) {
//        	RenderUtils.drawRect(button.getX(), button.getY(), button.getX() + button.getDimension().width - 1, button.getY() + button.getDimension().height - 1, 
//            		mainColor);
            RenderUtils.drawRect(button.getX(), button.getY() + button.getButtonHeight() - 1, button.getX() + button.getDimension().width, button.getY() + button.getButtonHeight(), ClickGui.getColor());
            RenderUtils.drawRect(button.getX(), button.getY() + button.getDimension().height - 1, button.getX() + button.getDimension().width, button.getY() + button.getDimension().height, ClickGui.getColor());
        }

        if (!button.isMaximized()) {
            drawExpanded(button.getX() + button.getDimension().width - 15, button.getY() + 3, 13, false, new Color(255, 255, 255, 255).hashCode());
        } else {
            drawExpanded(button.getX() + button.getDimension().width - 15, button.getY() + 3, 13, true, new Color(255, 255, 255, 255).hashCode());
        }

        if (button.isMaximized()) {
            button.renderChildren(mouseX, mouseY);
        }
        
        String description = button.hack.getDescription();
        if(description != null && button.isMouseOver(mouseX, mouseY) && !button.isMaximized() && HackManager.getHack("ClickGui").isToggledValue("Tooltip")) {
        	ClickGuiScreen.tooltip = new Tooltip(description, mouseX, mouseY, theme.fontRenderer);
        }
    }

    @Override
    public void doInteractions(Component component, int mouseX, int mouseY) {}
}
