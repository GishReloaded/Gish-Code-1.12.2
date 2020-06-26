package i.gishreloaded.gishcode.gui.click.theme.dark;

import java.awt.Color;

import i.gishreloaded.gishcode.gui.click.base.Component;
import i.gishreloaded.gishcode.gui.click.base.ComponentRenderer;
import i.gishreloaded.gishcode.gui.click.base.ComponentType;
import i.gishreloaded.gishcode.gui.click.elements.CheckButton;
import i.gishreloaded.gishcode.gui.click.theme.Theme;
import i.gishreloaded.gishcode.hack.hacks.ClickGui;
import i.gishreloaded.gishcode.utils.MathUtils;
import i.gishreloaded.gishcode.utils.visual.ColorUtils;
import i.gishreloaded.gishcode.utils.visual.GLUtils;
import i.gishreloaded.gishcode.utils.visual.RenderUtils;
import i.gishreloaded.gishcode.value.Mode;

public class DarkCheckButton extends ComponentRenderer {

    public DarkCheckButton(Theme theme) {

        super(ComponentType.CHECK_BUTTON, theme);
    }

    @Override
    public void drawComponent(Component component, int mouseX, int mouseY) {

        CheckButton button = (CheckButton) component;
        String text = button.getText();
        
        int mainColor = ClickGui.isLight ? ColorUtils.color(255, 255, 255, 255) : ColorUtils.color(0, 0, 0, 255);
        int mainColorInv = ClickGui.isLight ? ColorUtils.color(0, 0, 0, 255) : ColorUtils.color(255, 255, 255, 255);
        int strColor = ClickGui.isLight ? ColorUtils.color(0.3f, 0.3f, 0.3f, 1.0f) : ColorUtils.color(0.5f, 0.5f, 0.5f, 1.0f);
        
        if(button.getModeValue() == null) {
        	theme.fontRenderer.drawString("> " + text, button.getX() + 5, MathUtils.getMiddle(button.getY(), button.getY() + button.getDimension().height) - theme.fontRenderer.FONT_HEIGHT / 3 - 1, 
        			button.isEnabled() ? mainColorInv : strColor);
        	return;
        }
        
        for(Mode mode : button.getModeValue().getModes()) {
    		if(mode.getName().equals(text)) {
    			theme.fontRenderer.drawString("- " + text, button.getX() + 5, MathUtils.getMiddle(button.getY(), button.getY() + button.getDimension().height) - theme.fontRenderer.FONT_HEIGHT / 3 - 1, 
    					mode.isToggled() ? mainColorInv : strColor);
    		}
        }
    }

    @Override
    public void doInteractions(Component component, int mouseX, int mouseY) {

    }
}
