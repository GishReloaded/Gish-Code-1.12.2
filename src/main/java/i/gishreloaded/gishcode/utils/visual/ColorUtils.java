package i.gishreloaded.gishcode.utils.visual;

import java.awt.Color;

public class ColorUtils {

	public static Color rainbow() {
		long offset = 999999999999L;
		float fade = 1.0f;
        float hue = (float) (System.nanoTime() + offset) / 1.0E10f % 1.0f;
        long color = Long.parseLong(Integer.toHexString(Color.HSBtoRGB(hue, 1.0f, 1.0f)), 16);
        Color c = new Color((int) color);
        return new Color((float) c.getRed() / 255.0f * fade, (float) c.getGreen() / 255.0f * fade, (float) c.getBlue() / 255.0f * fade, (float) c.getAlpha() / 255.0f);
    }
	
	public static int color(int r, int g, int b, int a) {
		return new Color(r, g, b, a).getRGB();
	}
	
	public static int color(float r, float g, float b, float a) {
		return new Color(r, g, b, a).getRGB();
	}
	
	public static int getColor(int a, int r, int g, int b) {
        return a << 24 | r << 16 | g << 8 | b;
    }

    public static int getColor(int r, int g, int b) {
        return 255 << 24 | r << 16 | g << 8 | b;
    }
}
