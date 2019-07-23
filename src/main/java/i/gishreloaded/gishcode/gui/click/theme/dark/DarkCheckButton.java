package i.gishreloaded.gishcode.gui.click.theme.dark;

import java.awt.Color;

import i.gishreloaded.gishcode.gui.click.base.Component;
import i.gishreloaded.gishcode.gui.click.base.ComponentRenderer;
import i.gishreloaded.gishcode.gui.click.base.ComponentType;
import i.gishreloaded.gishcode.gui.click.elements.CheckButton;
import i.gishreloaded.gishcode.gui.click.theme.Theme;
import i.gishreloaded.gishcode.hack.hacks.ClickGui;
import i.gishreloaded.gishcode.utils.ColorUtils;
import i.gishreloaded.gishcode.utils.GLUtils;
import i.gishreloaded.gishcode.utils.MathUtils;
import i.gishreloaded.gishcode.utils.RenderUtils;
import i.gishreloaded.gishcode.value.Mode;

public class DarkCheckButton extends ComponentRenderer {

    public DarkCheckButton(Theme theme) {

        super(ComponentType.CHECK_BUTTON, theme);
    }

    @Override
    public void drawComponent(Component component, int mouseX, int mouseY) {

        CheckButton button = (CheckButton) component;
        String text = button.getText();
        
        int colorStringIsEnabled = ColorUtils.colorRGB(1.0f, 1.0f, 1.0f, 1.0f);
        int colorStringIsDisabled = ColorUtils.colorRGB(0.5f, 0.5f, 0.5f, 1.0f);
        
        if(button.getModeValue() == null) {
        	theme.fontRenderer.drawString("> " + text, button.getX() + 5, MathUtils.getMiddle(button.getY(), button.getY() + button.getDimension().height) - theme.fontRenderer.FONT_HEIGHT / 3 - 1, 
        			button.isEnabled() ? colorStringIsEnabled : colorStringIsDisabled);
        	return;
        }
        
        for(Mode mode : button.getModeValue().getModes()) {
    		if(mode.getName().equals(text)) {
    			theme.fontRenderer.drawString("- " + text, button.getX() + 5, MathUtils.getMiddle(button.getY(), button.getY() + button.getDimension().height) - theme.fontRenderer.FONT_HEIGHT / 3 - 1, 
    					mode.isToggled() ? colorStringIsEnabled : colorStringIsDisabled);
    		}
        }
    }

    @Override
    public void doInteractions(Component component, int mouseX, int mouseY) {

    }
}
