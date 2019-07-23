package i.gishreloaded.gishcode.gui.click.theme.dark;

import java.awt.Color;

import i.gishreloaded.gishcode.gui.click.base.Component;
import i.gishreloaded.gishcode.gui.click.base.ComponentRenderer;
import i.gishreloaded.gishcode.gui.click.base.ComponentType;
import i.gishreloaded.gishcode.gui.click.elements.Slider;
import i.gishreloaded.gishcode.gui.click.theme.Theme;
import i.gishreloaded.gishcode.hack.hacks.ClickGui;
import i.gishreloaded.gishcode.utils.ColorUtils;
import i.gishreloaded.gishcode.utils.GLUtils;
import i.gishreloaded.gishcode.utils.RenderUtils;

public class DarkSlider extends ComponentRenderer {

    public DarkSlider(Theme theme) {

        super(ComponentType.SLIDER, theme);
    }

    @Override
    public void drawComponent(Component component, int mouseX, int mouseY) {

        Slider slider = (Slider) component;
        int width = (int) ((slider.getDimension().getWidth()) * slider.getPercent());
        
        int colorStringValueName = ColorUtils.colorRGB(0.5f, 0.5f, 0.5f, 1.0f);
        int colorStringValue = ColorUtils.colorRGB(1.0f, 1.0f, 1.0f, 1.0f);
        
        int colorSliderRect = ColorUtils.colorRGB(1.0f, 1.0f, 1.0f, 1.0f);   
        int colorSlider = ClickGui.color.getRGB();

        GLUtils.glColor(ColorUtils.colorRGB(1.0f, 1.0f, 1.0f, 1.0f));
        
        theme.fontRenderer.drawString(slider.getText(), slider.getX() + 4, slider.getY() + 2, 
        		colorStringValueName);
        
        theme.fontRenderer.drawString(slider.getValue() + "", slider.getX() + slider.getDimension().width - theme.fontRenderer.getStringWidth(slider.getValue() + "") - 2, slider.getY() + 2, 
        		colorStringValue);
        
        RenderUtils.drawRect(slider.getX(), slider.getY() + slider.getDimension().height / 2 + 3, slider.getX() + (width) + 3, (slider.getY() + slider.getDimension().height / 2) + 6, 
        		colorSliderRect);
        
        RenderUtils.drawRect(slider.getX(), slider.getY() + slider.getDimension().height / 2 + 3, slider.getX() + (width), (slider.getY() + slider.getDimension().height / 2) + 6, 
        		colorSlider);
    }

    @Override
    public void doInteractions(Component component, int mouseX, int mouseY) {

    }
}
