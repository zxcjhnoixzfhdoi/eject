/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import modification.extenders.Glyph;
import modification.main.Modification;
import modification.utilities.ShaderUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.MCFontRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import optifine.Config;
import optifine.CustomColors;
import org.lwjgl.opengl.GL11;

public final class FontRenderer
extends MCFontRenderer {
    private static final int IMAGE_SIZE = 1024;
    public final int FONT_HEIGHT;
    private final Font font;
    private final Glyph[] basicGlyphs = new Glyph[256];
    private final Glyph[] boldGlyphs = new Glyph[256];
    private final Glyph[] italicGlyphs = new Glyph[256];
    private final Glyph[] bothGlyphs = new Glyph[256];
    private final DynamicTexture basicTexture;
    private final DynamicTexture boldTexture;
    private final DynamicTexture italicTexture;
    private final DynamicTexture bothTexture;
    private boolean fractional;

    public FontRenderer(String string, int n, int n2, boolean bl) {
        super(Minecraft.getMinecraft().gameSettings, new ResourceLocation("textures/font/ascii.png"), Minecraft.getMinecraft().getTextureManager(), false);
        this.font = new Font(string, n, n2);
        this.basicTexture = this.generateFontTexture(this.font, this.basicGlyphs);
        this.boldTexture = this.generateFontTexture(this.font.deriveFont(1), this.boldGlyphs);
        this.italicTexture = this.generateFontTexture(this.font.deriveFont(2), this.italicGlyphs);
        this.bothTexture = this.generateFontTexture(this.font.deriveFont(3), this.bothGlyphs);
        this.FONT_HEIGHT = n2 / 2;
        this.fractional = bl;
    }

    public FontRenderer(String string, int n, boolean bl) {
        super(Minecraft.getMinecraft().gameSettings, new ResourceLocation("textures/font/ascii.png"), Minecraft.getMinecraft().getTextureManager(), false);
        this.font = this.readFontData(string, n);
        this.basicTexture = this.generateFontTexture(this.font, this.basicGlyphs);
        this.boldTexture = this.generateFontTexture(this.font.deriveFont(1), this.boldGlyphs);
        this.italicTexture = this.generateFontTexture(this.font.deriveFont(2), this.italicGlyphs);
        this.bothTexture = this.generateFontTexture(this.font.deriveFont(3), this.bothGlyphs);
        this.FONT_HEIGHT = n / 2;
        this.fractional = bl;
    }

    private Font readFontData(String string, float f) {
        InputStream inputStream = ShaderUtil.class.getResourceAsStream("fonts/".concat(string).concat(".ttf"));
        Font font = null;
        try {
            font = Font.createFont(0, inputStream).deriveFont(f);
        }
        catch (FontFormatException | IOException exception) {
            exception.printStackTrace();
        }
        return font;
    }

    private int renderString(String string, float f, float f2, int n, boolean bl) {
        if (string == null) {
            return 0;
        }
        f *= 2.0f;
        f2 = f2 * 2.0f - 4.0f;
        if (bl) {
            n = Color.BLACK.getRGB();
        }
        this.randomStyle = false;
        this.boldStyle = false;
        this.strikethroughStyle = false;
        this.underlineStyle = false;
        this.italicStyle = false;
        DynamicTexture dynamicTexture = this.basicTexture;
        Glyph[] glyphArray = this.basicGlyphs;
        GlStateManager.pushMatrix();
        GlStateManager.enableBlend();
        float f3 = Modification.RENDER_UTIL.rgba(n)[3];
        this.setColor(Modification.RENDER_UTIL.rgba(n)[0], Modification.RENDER_UTIL.rgba(n)[1], Modification.RENDER_UTIL.rgba(n)[2], f3);
        for (int i = 0; i < string.length(); ++i) {
            char c = string.charAt(i);
            if (c == '\u00a7' && i + 1 < string.length()) {
                int n2 = "0123456789abcdefklmnor".indexOf(string.toLowerCase().charAt(i + 1));
                if (n2 < 16) {
                    this.randomStyle = false;
                    this.boldStyle = false;
                    this.strikethroughStyle = false;
                    this.underlineStyle = false;
                    this.italicStyle = false;
                    dynamicTexture = this.basicTexture;
                    glyphArray = this.basicGlyphs;
                    if (n2 < 0) {
                        n2 = 15;
                    }
                    if (bl) {
                        n2 += 16;
                    }
                    int n3 = this.colorCode[n2];
                    if (Config.isCustomColors()) {
                        n3 = CustomColors.getTextColor((int)n2, (int)n3);
                    }
                    this.setColor(Modification.RENDER_UTIL.rgba(n3)[0], Modification.RENDER_UTIL.rgba(n3)[1], Modification.RENDER_UTIL.rgba(n3)[2], f3);
                } else if (n2 == 16) {
                    this.randomStyle = true;
                } else if (n2 == 17) {
                    this.boldStyle = true;
                    if (this.italicStyle) {
                        dynamicTexture = this.bothTexture;
                        glyphArray = this.bothGlyphs;
                    } else {
                        dynamicTexture = this.boldTexture;
                        glyphArray = this.boldGlyphs;
                    }
                } else if (n2 == 18) {
                    this.strikethroughStyle = true;
                } else if (n2 == 19) {
                    this.underlineStyle = true;
                } else if (n2 == 20) {
                    this.italicStyle = true;
                    if (this.boldStyle) {
                        dynamicTexture = this.bothTexture;
                        glyphArray = this.bothGlyphs;
                    } else {
                        dynamicTexture = this.italicTexture;
                        glyphArray = this.italicGlyphs;
                    }
                } else {
                    this.randomStyle = false;
                    this.boldStyle = false;
                    this.strikethroughStyle = false;
                    this.underlineStyle = false;
                    this.italicStyle = false;
                    dynamicTexture = this.basicTexture;
                    glyphArray = this.basicGlyphs;
                    this.setColor(Modification.RENDER_UTIL.rgba(n)[0], Modification.RENDER_UTIL.rgba(n)[1], Modification.RENDER_UTIL.rgba(n)[2], f3);
                }
                ++i;
                continue;
            }
            if (c < glyphArray.length) {
                WorldRenderer worldRenderer;
                GlStateManager.scale((double)0.5, (double)0.5, (double)0.5);
                GlStateManager.bindTexture((int)dynamicTexture.getGlTextureId());
                this.drawCharacter(c, glyphArray, f, f2);
                float f4 = glyphArray[c].width - 8;
                if (this.strikethroughStyle) {
                    Tessellator tessellator = Tessellator.getInstance();
                    worldRenderer = tessellator.getWorldRenderer();
                    GlStateManager.disableTexture2D();
                    worldRenderer.begin(7, DefaultVertexFormats.POSITION);
                    worldRenderer.pos((double)f, (double)(f2 + (float)(this.font.getSize() / 2)), 0.0).endVertex();
                    worldRenderer.pos((double)(f + f4), (double)(f2 + (float)(this.font.getSize() / 2)), 0.0).endVertex();
                    worldRenderer.pos((double)(f + f4), (double)(f2 + (float)(this.font.getSize() / 2) - 1.0f), 0.0).endVertex();
                    worldRenderer.pos((double)f, (double)(f2 + (float)(this.font.getSize() / 2) - 1.0f), 0.0).endVertex();
                    tessellator.draw();
                    GlStateManager.enableTexture2D();
                }
                if (this.underlineStyle) {
                    Tessellator tessellator = Tessellator.getInstance();
                    worldRenderer = tessellator.getWorldRenderer();
                    GlStateManager.disableTexture2D();
                    worldRenderer.begin(7, DefaultVertexFormats.POSITION);
                    int n4 = this.underlineStyle ? -1 : 0;
                    worldRenderer.pos((double)(f + (float)n4), (double)(f2 + (float)this.font.getSize() + 2.0f), 0.0).endVertex();
                    worldRenderer.pos((double)(f + f4), (double)(f2 + (float)this.font.getSize() + 2.0f), 0.0).endVertex();
                    worldRenderer.pos((double)(f + f4), (double)(f2 + (float)this.font.getSize() + 1.0f), 0.0).endVertex();
                    worldRenderer.pos((double)(f + (float)n4), (double)(f2 + (float)this.font.getSize() + 1.0f), 0.0).endVertex();
                    tessellator.draw();
                    GlStateManager.enableTexture2D();
                }
                f += f4;
                GlStateManager.scale((float)2.0f, (float)2.0f, (float)2.0f);
                continue;
            }
            this.posX = f / 2.0f + 1.5f;
            this.posY = f2 / 2.0f + 1.5f;
            float f5 = this.renderUnicodeChar(c, false);
            f += f5 + 6.0f;
        }
        GlStateManager.enableAlpha();
        GlStateManager.popMatrix();
        return (int)f / 2;
    }

    private DynamicTexture generateFontTexture(Font font, Glyph[] glyphArray) {
        BufferedImage bufferedImage = new BufferedImage(1024, 1024, 2);
        Graphics2D graphics2D = (Graphics2D)bufferedImage.getGraphics();
        graphics2D.setColor(new Color(255, 255, 255, 0));
        graphics2D.fillRect(0, 0, 1024, 1024);
        graphics2D.setColor(Color.WHITE);
        graphics2D.setFont(font);
        graphics2D.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, this.fractional ? RenderingHints.VALUE_FRACTIONALMETRICS_ON : RenderingHints.VALUE_FRACTIONALMETRICS_OFF);
        graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        graphics2D.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        int n = 0;
        int n2 = 0;
        for (int i = 0; i < glyphArray.length; ++i) {
            Glyph glyph = new Glyph();
            char c = (char)i;
            Rectangle2D rectangle2D = graphics2D.getFontMetrics().getStringBounds(Character.toString(c), graphics2D);
            int n3 = rectangle2D.getBounds().width + 8;
            int n4 = rectangle2D.getBounds().height;
            if (n + n3 >= 1024) {
                n2 += n4;
                n = 0;
            }
            glyphArray[i] = glyph;
            glyph.x = n;
            glyph.y = n2;
            glyph.width = n3;
            glyph.height = n4;
            graphics2D.drawString(Character.toString(c), n + 1, n2 + graphics2D.getFontMetrics().getAscent());
            n += n3;
        }
        return new DynamicTexture(bufferedImage);
    }

    private void drawCharacter(char c, Glyph[] glyphArray, float f, float f2) {
        int n = glyphArray[c].width;
        int n2 = glyphArray[c].height;
        float f3 = (float)glyphArray[c].x / 1024.0f;
        float f4 = (float)glyphArray[c].y / 1024.0f;
        float f5 = (float)n / 1024.0f;
        float f6 = (float)n2 / 1024.0f;
        GL11.glBegin((int)5);
        GL11.glTexCoord2f((float)f3, (float)f4);
        GL11.glVertex2f((float)f, (float)f2);
        GL11.glTexCoord2f((float)f3, (float)(f4 + f6));
        GL11.glVertex2f((float)f, (float)(f2 + (float)n2));
        GL11.glTexCoord2f((float)(f3 + f5), (float)f4);
        GL11.glVertex2f((float)(f + (float)n), (float)f2);
        GL11.glTexCoord2f((float)(f3 + f5), (float)(f4 + f6));
        GL11.glVertex2f((float)(f + (float)n), (float)(f2 + (float)n2));
        GL11.glEnd();
    }

    public int getStringWidth(String string) {
        if (string == null) {
            return 0;
        }
        int n = 0;
        Glyph[] glyphArray = this.boldStyle && this.italicStyle ? this.bothGlyphs : (this.boldStyle ? this.boldGlyphs : (this.italicStyle ? this.italicGlyphs : this.basicGlyphs));
        for (int i = 0; i < string.length(); ++i) {
            char c = string.charAt(i);
            if (c == '\u00a7' && i + 1 < string.length()) {
                ++i;
                continue;
            }
            if (c < glyphArray.length) {
                n += glyphArray[c].width - 8;
                continue;
            }
            int n2 = this.glyphWidth[c] >>> 4;
            int n3 = this.glyphWidth[c] & 0xF;
            float f = n2 &= 0xF;
            float f2 = n3 + 1;
            n = (int)((float)n + ((f2 - f) / 2.0f + 1.0f + 6.0f));
        }
        return n / 2;
    }

    public int drawStringWithShadow(String string, float f, float f2, int n) {
        this.renderString(string, f + 1.0f, f2 + 1.0f, n, true);
        return this.renderString(string, f, f2, n, false);
    }

    public int drawString(String string, int n, int n2, int n3) {
        return this.renderString(string, n, n2, n3, false);
    }
}
