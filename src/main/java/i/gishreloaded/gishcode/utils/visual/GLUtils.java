package i.gishreloaded.gishcode.utils.visual;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import org.lwjgl.BufferUtils;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.lwjgl.opengl.GL11.*;

/**
 * Created by halalaboos.
 */
public final class GLUtils {

    private static final Random random = new Random();

    private static final Tessellator tessellator = Tessellator.getInstance();

    public static List<Integer> vbos = new ArrayList<Integer>(), textures = new ArrayList<>();

    public static void glScissor(int[] rect) {

        glScissor(rect[0], rect[1], rect[0] + rect[2], rect[1] + rect[3]);
    }

    public static void glScissor(float x, float y, float x1, float y1) {

        int scaleFactor = getScaleFactor();
        GL11.glScissor((int) (x * scaleFactor), (int) (Minecraft.getMinecraft().displayHeight - (y1 * scaleFactor)), (int) ((x1 - x) * scaleFactor), (int) ((y1 - y) * scaleFactor));
    }

    /**
     * @return The scale factor used by the play's screen gui scale
     */
    public static int getScaleFactor() {

        int scaleFactor = 1;
        boolean isUnicode = Minecraft.getMinecraft().isUnicode();
        int guiScale = Minecraft.getMinecraft().gameSettings.guiScale;

        if (guiScale == 0) {
            guiScale = 1000;
        }

        while (scaleFactor < guiScale && Minecraft.getMinecraft().displayWidth / (scaleFactor + 1) >= 320 && Minecraft.getMinecraft().displayHeight / (scaleFactor + 1) >= 240) {
            scaleFactor++;
        }

        if (isUnicode && scaleFactor % 2 != 0 && scaleFactor != 1) {
            scaleFactor--;
        }

        return scaleFactor;

    }

    /**
     * @return Mouse X cord.
     */
    public static int getMouseX() {

        return (Mouse.getX() * getScreenWidth() / Minecraft.getMinecraft().displayWidth);
    }

    /**
     * @return Mouse Y cord.
     */
    public static int getMouseY() {

        return (getScreenHeight() - Mouse.getY() * getScreenHeight() / Minecraft.getMinecraft().displayWidth - 1);
    }

    /**
     * @return Screen width with gui scale.
     */
    public static int getScreenWidth() {

        return Minecraft.getMinecraft().displayWidth / getScaleFactor();
    }

    /**
     * @return Screen height with gui scale.
     */
    public static int getScreenHeight() {

        return Minecraft.getMinecraft().displayHeight / getScaleFactor();
    }

    /**
     * Checks if the mouse is hovering over a given item
     *
     * @param x
     * @param y
     * @param width
     * @param height
     * @param mouseX
     * @param mouseY
     * @return
     */
    public static boolean isHovered(int x, int y, int width, int height, int mouseX, int mouseY) {

        return (mouseX >= x) && (mouseX <= x + width) && (mouseY >= y) && (mouseY < y + height);
    }

    public static int genVBO() {

        int id = GL15.glGenBuffers();
        vbos.add(id);
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, id);
        return id;
    }

    public static int getTexture() {

        int textureID = glGenTextures();
        textures.add(textureID);
        return textureID;
    }

    /**
     * @param filter determines how the texture will interpolate when scaling up / down. <br>
     *               GL_LINEAR - smoothest <br> GL_NEAREST - most accurate <br>
     * @param wrap   determines how the UV coordinates outside of the 0.0F ~ 1.0F range will be handled. <br>
     *               GL_CLAMP_TO_EDGE - samples edge color <br> GL_REPEAT - repeats the texture <br>
     */
    public static int applyTexture(int texId, File file, int filter, int wrap) throws IOException {

        applyTexture(texId, ImageIO.read(file), filter, wrap);
        return texId;
    }

    /**
     * @param filter determines how the texture will interpolate when scaling up / down. <br>
     *               GL_LINEAR - smoothest <br> GL_NEAREST - most accurate <br>
     * @param wrap   determines how the UV coordinates outside of the 0.0F ~ 1.0F range will be handled. <br>
     *               GL_CLAMP_TO_EDGE - samples edge color <br> GL_REPEAT - repeats the texture <br>
     */
    public static int applyTexture(int texId, BufferedImage image, int filter, int wrap) {

        int[] pixels = new int[image.getWidth() * image.getHeight()];
        image.getRGB(0, 0, image.getWidth(), image.getHeight(), pixels, 0, image.getWidth());

        ByteBuffer buffer = BufferUtils.createByteBuffer(image.getWidth() * image.getHeight() * 4);

        for (int y = 0; y < image.getHeight(); y++) {
            for (int x = 0; x < image.getWidth(); x++) {
                int pixel = pixels[y * image.getWidth() + x];
                buffer.put((byte) ((pixel >> 16) & 0xFF));
                buffer.put((byte) ((pixel >> 8) & 0xFF));
                buffer.put((byte) (pixel & 0xFF));
                buffer.put((byte) ((pixel >> 24) & 0xFF));
            }
        }

        buffer.flip();
        applyTexture(texId, image.getWidth(), image.getHeight(), buffer, filter, wrap);
        return texId;
    }

    /**
     * @param filter determines how the texture will interpolate when scaling up / down. <br>
     *               GL_LINEAR - smoothest <br> GL_NEAREST - most accurate <br>
     * @param wrap   determines how the UV coordinates outside of the 0.0F ~ 1.0F range will be handled. <br>
     *               GL_CLAMP_TO_EDGE - samples edge color <br> GL_REPEAT - repeats the texture <br>
     */
    public static int applyTexture(int texId, int width, int height, ByteBuffer pixels, int filter, int wrap) {

        glBindTexture(GL_TEXTURE_2D, texId);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, filter);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, filter);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, wrap);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, wrap);
        glPixelStorei(GL_UNPACK_ALIGNMENT, 1);
        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA8, width, height, 0, GL_RGBA, GL_UNSIGNED_BYTE, pixels);
        glBindTexture(GL_TEXTURE_2D, 0);
        return texId;
    }

    /**
     * Cleans ups the arrays on close
     */
    public static void cleanup() {

        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
        glBindTexture(GL_TEXTURE_2D, 0);

        for (int vbo : vbos) {
            GL15.glDeleteBuffers(vbo);
        }

        for (int texture : textures) {
            glDeleteTextures(texture);
        }

    }

    /**
     * Rect
     */


    public static void drawBorderRect(float x, float y, float x1, float y1, float borderSize) {

        drawBorder(borderSize, x, y, x1, y1);
        drawRect(x, y, x1, y1);
    }

    public static void drawBorder(float size, float x, float y, float x1, float y1) {

        glLineWidth(size);
        GlStateManager.disableTexture2D();
        GlStateManager.enableBlend();
        GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        BufferBuilder vertexBuffer = tessellator.getBuffer();
        vertexBuffer.begin(GL_LINE_LOOP, DefaultVertexFormats.POSITION);
        vertexBuffer.pos(x, y, 0F).endVertex();
        vertexBuffer.pos(x, y1, 0F).endVertex();
        vertexBuffer.pos(x1, y1, 0F).endVertex();
        vertexBuffer.pos(x1, y, 0F).endVertex();
        tessellator.draw();
        GlStateManager.enableTexture2D();
    }

    public static void drawRect(float x, float y, float w, float h) {

        GlStateManager.disableTexture2D();
        GlStateManager.enableBlend();
        GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        BufferBuilder vertexBuffer = tessellator.getBuffer();
        vertexBuffer.begin(GL_QUADS, DefaultVertexFormats.POSITION);
        vertexBuffer.pos(x, h, 0F).endVertex();
        vertexBuffer.pos(w, h, 0F).endVertex();
        vertexBuffer.pos(w, y, 0F).endVertex();
        vertexBuffer.pos(x, y, 0F).endVertex();
        tessellator.draw();
        GlStateManager.enableTexture2D();
    }


    public static void drawGradientRect(int x, int y, int w, int h, int startColor, int endColor) {

        float f = (float) (startColor >> 24 & 255) / 255.0F;
        float f1 = (float) (startColor >> 16 & 255) / 255.0F;
        float f2 = (float) (startColor >> 8 & 255) / 255.0F;
        float f3 = (float) (startColor & 255) / 255.0F;
        float f4 = (float) (endColor >> 24 & 255) / 255.0F;
        float f5 = (float) (endColor >> 16 & 255) / 255.0F;
        float f6 = (float) (endColor >> 8 & 255) / 255.0F;
        float f7 = (float) (endColor & 255) / 255.0F;
        GlStateManager.disableTexture2D();
        GlStateManager.enableBlend();
        GlStateManager.disableAlpha();
        GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        GlStateManager.shadeModel(7425);
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder vertexbuffer = tessellator.getBuffer();
        vertexbuffer.begin(7, DefaultVertexFormats.POSITION_COLOR);
        vertexbuffer.pos((double) x + w, (double) y, (double) 0).color(f1, f2, f3, f).endVertex();
        vertexbuffer.pos((double) x, (double) y, (double) 0).color(f1, f2, f3, f).endVertex();
        vertexbuffer.pos((double) x, (double) y + h, (double) 0).color(f5, f6, f7, f4).endVertex();
        vertexbuffer.pos((double) x + w, (double) y + h, (double) 0).color(f5, f6, f7, f4).endVertex();
        tessellator.draw();
        GlStateManager.shadeModel(7424);
        GlStateManager.disableBlend();
        GlStateManager.enableAlpha();
        GlStateManager.enableTexture2D();
    }

    public static void enableGL2D() {

        GL11.glDisable(GL11.GL_DEPTH_TEST);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glDepthMask(true);
        GL11.glEnable(GL11.GL_LINE_SMOOTH);
        GL11.glHint(GL11.GL_LINE_SMOOTH_HINT, GL11.GL_NICEST);
        GL11.glHint(GL11.GL_POLYGON_SMOOTH_HINT, GL11.GL_NICEST);
    }

    public static void disableGL2D() {

        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glDisable(GL11.GL_LINE_SMOOTH);
        GL11.glHint(GL11.GL_LINE_SMOOTH_HINT, GL11.GL_DONT_CARE);
        GL11.glHint(GL11.GL_POLYGON_SMOOTH_HINT, GL11.GL_DONT_CARE);
    }


    /**
     * Colors
     */

    public static void glColor(float red, float green, float blue, float alpha) {

        GlStateManager.color(red, green, blue, alpha);
    }

    public static void glColor(Color color) {

        GlStateManager.color((float) color.getRed() / 255F, (float) color.getGreen() / 255F, (float) color.getBlue() / 255F, (float) color.getAlpha() / 255F);
    }

    public static void glColor(int color) {

        GlStateManager.color((float) (color >> 16 & 255) / 255F, (float) (color >> 8 & 255) / 255F, (float) (color & 255) / 255F, (float) (color >> 24 & 255) / 255F);
    }

    public static Color getHSBColor(float hue, float sturation, float luminance) {

        return Color.getHSBColor(hue, sturation, luminance);
    }

    public static Color getRandomColor(int saturationRandom, float luminance) {

        final float hue = random.nextFloat();
        final float saturation = (random.nextInt(saturationRandom) + (float) saturationRandom) / (float) saturationRandom + (float) saturationRandom;
        return getHSBColor(hue, saturation, luminance);
    }

    public static Color getRandomColor() {

        return getRandomColor(1000, 0.6f);
    }


}