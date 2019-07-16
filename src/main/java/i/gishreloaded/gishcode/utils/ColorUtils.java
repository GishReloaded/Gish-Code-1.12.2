package i.gishreloaded.gishcode.utils;

import java.awt.Color;

public class ColorUtils {

	public static Color rainbow(long offset, float fade) {
        float hue = (float) (System.nanoTime() + offset) / 1.0E10f % 1.0f;
        long color = Long.parseLong(Integer.toHexString(Color.HSBtoRGB(hue, 1.0f, 1.0f)), 16);
        Color c = new Color((int) color);
        return new Color((float) c.getRed() / 255.0f * fade, (float) c.getGreen() / 255.0f * fade, (float) c.getBlue() / 255.0f * fade, (float) c.getAlpha() / 255.0f);
    }
	
	public static int colorRGB(float red, float green, float blue, float alpha) {
        final Color color = new Color(red, green, blue, alpha);
        final int c = color.getRGB();
        return c;
	}
	
	public static int getColor(int a, int r, int g, int b) {
        return a << 24 | r << 16 | g << 8 | b;
    }

    public static int getColor(int r, int g, int b) {
        return 255 << 24 | r << 16 | g << 8 | b;
    }
}
