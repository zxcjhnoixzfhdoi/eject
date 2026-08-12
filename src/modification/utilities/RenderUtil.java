/*
 * Decompiled with CFR 0.152.
 */
package modification.utilities;

import java.awt.Color;
import java.util.Calendar;
import modification.interfaces.MCHook;
import modification.main.Modification;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.shader.Framebuffer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public final class RenderUtil
implements MCHook {
    public static boolean renderOutlinesCustom = false;

    public final float[] rgba(int n) {
        if ((n & 0xFC000000) == 0) {
            n |= 0xFF000000;
        }
        return new float[]{(float)(n >> 16 & 0xFF) / 255.0f, (float)(n >> 8 & 0xFF) / 255.0f, (float)(n & 0xFF) / 255.0f, (float)(n >> 24 & 0xFF) / 255.0f};
    }

    public final boolean mouseHovered(int n, int n2, float f, float f2, float f3, float f4) {
        return (float)n >= f && (float)n <= f + f3 && (float)n2 >= f2 && (float)n2 <= f2 + f4;
    }

    public final void drawRect(float f, float f2, float f3, float f4, int n) {
        GlStateManager.pushMatrix();
        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate((int)770, (int)771, (int)1, (int)0);
        GlStateManager.disableTexture2D();
        GlStateManager.disableCull();
        GlStateManager.disableAlpha();
        this.glSetColor(n);
        GL11.glBegin((int)7);
        GL11.glVertex2f((float)f, (float)f2);
        GL11.glVertex2f((float)(f + f3), (float)f2);
        GL11.glVertex2f((float)(f + f3), (float)(f2 + f4));
        GL11.glVertex2f((float)f, (float)(f2 + f4));
        GL11.glEnd();
        GlStateManager.enableAlpha();
        GlStateManager.enableCull();
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
        GlStateManager.popMatrix();
    }

    public final void drawOutlinedRect(float f, float f2, float f3, float f4, int n, int n2) {
        GlStateManager.pushMatrix();
        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate((int)770, (int)771, (int)1, (int)0);
        GlStateManager.disableTexture2D();
        GlStateManager.disableCull();
        GlStateManager.disableAlpha();
        this.glSetColor(n2);
        GL11.glLineWidth((float)n);
        GL11.glBegin((int)2);
        GL11.glVertex2f((float)f, (float)f2);
        GL11.glVertex2f((float)(f + f3), (float)f2);
        GL11.glVertex2f((float)(f + f3), (float)(f2 + f4));
        GL11.glVertex2f((float)f, (float)(f2 + f4));
        GL11.glEnd();
        GL11.glLineWidth((float)1.0f);
        GlStateManager.enableAlpha();
        GlStateManager.enableCull();
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
        GlStateManager.popMatrix();
    }

    public void glSetColor(int n) {
        GlStateManager.color((float)this.rgba(n)[0], (float)this.rgba(n)[1], (float)this.rgba(n)[2], (float)this.rgba(n)[3]);
    }

    public final void drawPicture(ResourceLocation resourceLocation, int n, int n2, int n3) {
        GlStateManager.pushMatrix();
        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate((int)770, (int)771, (int)1, (int)0);
        Minecraft.getMinecraft().getTextureManager().bindTexture(resourceLocation);
        Gui.drawScaledCustomSizeModalRect((int)n, (int)n2, (float)0.0f, (float)0.0f, (int)n3, (int)n3, (int)n3, (int)n3, (float)n3, (float)n3);
        GlStateManager.popMatrix();
    }

    public final void drawSprite(ResourceLocation resourceLocation, int n, int n2, int n3, int n4, int n5, int n6, int n7, int n8) {
        GlStateManager.pushMatrix();
        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate((int)770, (int)771, (int)1, (int)0);
        this.glSetColor(n8);
        Minecraft.getMinecraft().getTextureManager().bindTexture(resourceLocation);
        Gui.drawScaledCustomSizeModalRect((int)n, (int)n2, (float)(48 * n3), (float)(48 * n4), (int)48, (int)48, (int)n7, (int)n7, (float)(48 * n5), (float)(48 * n6));
        GlStateManager.popMatrix();
    }

    public final void drawBorderedRect(float f, float f2, float f3, float f4, int n, int n2, int n3) {
        this.drawRect(f, f2, f3, f4, n2);
        this.drawRect(f - (float)n, f2 - (float)n, f3 + (float)(n * 2), n, n3);
        this.drawRect(f + f3, f2, n, f4, n3);
        this.drawRect(f - (float)n, f2 + f4, f3 + (float)(n * 2), n, n3);
        this.drawRect(f - (float)n, f2, n, f4, n3);
    }

    public final void drawTexturedModalRect(float f, float f2, float f3, float f4, float f5, float f6) {
        float f7 = 0.00390625f;
        float f8 = 0.00390625f;
        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldRenderer = tessellator.getWorldRenderer();
        worldRenderer.begin(7, DefaultVertexFormats.POSITION_TEX);
        worldRenderer.pos((double)(f + 0.0f), (double)(f2 + f6), 0.0).tex((double)((f3 + 0.0f) * f7), (double)((f4 + f6) * f8)).endVertex();
        worldRenderer.pos((double)(f + f5), (double)(f2 + f6), 0.0).tex((double)((f3 + f5) * f7), (double)((f4 + f6) * f8)).endVertex();
        worldRenderer.pos((double)(f + f5), (double)(f2 + 0.0f), 0.0).tex((double)((f3 + f5) * f7), (double)((f4 + 0.0f) * f8)).endVertex();
        worldRenderer.pos((double)(f + 0.0f), (double)(f2 + 0.0f), 0.0).tex((double)((f3 + 0.0f) * f7), (double)((f4 + 0.0f) * f8)).endVertex();
        tessellator.draw();
    }

    public final void drawTexturedModalRect(float f, float f2, TextureAtlasSprite textureAtlasSprite, float f3, float f4) {
        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldRenderer = tessellator.getWorldRenderer();
        worldRenderer.begin(7, DefaultVertexFormats.POSITION_TEX);
        worldRenderer.pos((double)(f + 0.0f), (double)(f2 + f4), 0.0).tex((double)textureAtlasSprite.getMinU(), (double)textureAtlasSprite.getMaxV()).endVertex();
        worldRenderer.pos((double)(f + f3), (double)(f2 + f4), 0.0).tex((double)textureAtlasSprite.getMaxU(), (double)textureAtlasSprite.getMaxV()).endVertex();
        worldRenderer.pos((double)(f + f3), (double)(f2 + 0.0f), 0.0).tex((double)textureAtlasSprite.getMaxU(), (double)textureAtlasSprite.getMinV()).endVertex();
        worldRenderer.pos((double)(f + 0.0f), (double)(f2 + 0.0f), 0.0).tex((double)textureAtlasSprite.getMinU(), (double)textureAtlasSprite.getMinV()).endVertex();
        tessellator.draw();
    }

    public final Framebuffer createFrameBuffer(Framebuffer framebuffer) {
        if (framebuffer == null || framebuffer.framebufferWidth != RenderUtil.MC.displayWidth || framebuffer.framebufferHeight != RenderUtil.MC.displayHeight) {
            if (framebuffer != null) {
                framebuffer.deleteFramebuffer();
            }
            return new Framebuffer(RenderUtil.MC.displayWidth, RenderUtil.MC.displayHeight, true);
        }
        return framebuffer;
    }

    public final void renderAABB(AxisAlignedBB axisAlignedBB, Color color, boolean bl) {
        GlStateManager.pushMatrix();
        if (bl) {
            GlStateManager.disableDepth();
        }
        GlStateManager.disableLighting();
        GL11.glDisable((int)3553);
        GlStateManager.enableBlend();
        RenderManager renderManager = Minecraft.getMinecraft().getRenderManager();
        GlStateManager.translate((double)(-renderManager.renderPosX), (double)(-renderManager.renderPosY), (double)(-renderManager.renderPosZ));
        GL11.glColor4f((float)((float)color.getRed() / 255.0f), (float)((float)color.getGreen() / 255.0f), (float)((float)color.getBlue() / 255.0f), (float)0.8f);
        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldRenderer = tessellator.getWorldRenderer();
        worldRenderer.begin(7, DefaultVertexFormats.POSITION);
        worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ).endVertex();
        worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.minZ).endVertex();
        worldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.minZ).endVertex();
        worldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.minZ).endVertex();
        worldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.maxZ).endVertex();
        worldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.maxZ).endVertex();
        worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.maxZ).endVertex();
        worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.maxZ).endVertex();
        worldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.minZ).endVertex();
        worldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.minZ).endVertex();
        worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.minZ).endVertex();
        worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ).endVertex();
        worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.maxZ).endVertex();
        worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.maxZ).endVertex();
        worldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.maxZ).endVertex();
        worldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.maxZ).endVertex();
        worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.minZ).endVertex();
        worldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.minZ).endVertex();
        worldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.maxZ).endVertex();
        worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.maxZ).endVertex();
        worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.minZ).endVertex();
        worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.maxZ).endVertex();
        worldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.maxZ).endVertex();
        worldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.minZ).endVertex();
        worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ).endVertex();
        worldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.minZ).endVertex();
        worldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.maxZ).endVertex();
        worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.maxZ).endVertex();
        worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ).endVertex();
        worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.maxZ).endVertex();
        worldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.maxZ).endVertex();
        worldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.minZ).endVertex();
        worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ).endVertex();
        worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.minZ).endVertex();
        worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.maxZ).endVertex();
        worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.maxZ).endVertex();
        worldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.maxZ).endVertex();
        worldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.maxZ).endVertex();
        worldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.minZ).endVertex();
        worldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.minZ).endVertex();
        worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.maxZ).endVertex();
        worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.maxZ).endVertex();
        worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.minZ).endVertex();
        worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ).endVertex();
        worldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.minZ).endVertex();
        worldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.minZ).endVertex();
        worldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.maxZ).endVertex();
        worldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.maxZ).endVertex();
        tessellator.draw();
        GlStateManager.disableLighting();
        if (bl) {
            GlStateManager.enableDepth();
        }
        GL11.glEnable((int)3553);
        GlStateManager.popMatrix();
    }

    public final void drawFilledBox(AxisAlignedBB axisAlignedBB, int n) {
        GlStateManager.disableAlpha();
        GlStateManager.color((float)Modification.RENDER_UTIL.rgba(n)[0], (float)Modification.RENDER_UTIL.rgba(n)[1], (float)Modification.RENDER_UTIL.rgba(n)[2], (float)0.4f);
        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldRenderer = tessellator.getWorldRenderer();
        worldRenderer.begin(7, DefaultVertexFormats.POSITION);
        worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ).endVertex();
        worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.minZ).endVertex();
        worldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.minZ).endVertex();
        worldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.minZ).endVertex();
        worldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.maxZ).endVertex();
        worldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.maxZ).endVertex();
        worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.maxZ).endVertex();
        worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.maxZ).endVertex();
        worldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.minZ).endVertex();
        worldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.minZ).endVertex();
        worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.minZ).endVertex();
        worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ).endVertex();
        worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.maxZ).endVertex();
        worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.maxZ).endVertex();
        worldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.maxZ).endVertex();
        worldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.maxZ).endVertex();
        worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.minZ).endVertex();
        worldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.minZ).endVertex();
        worldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.maxZ).endVertex();
        worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.maxZ).endVertex();
        worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.minZ).endVertex();
        worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.maxZ).endVertex();
        worldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.maxZ).endVertex();
        worldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.minZ).endVertex();
        worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ).endVertex();
        worldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.minZ).endVertex();
        worldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.maxZ).endVertex();
        worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.maxZ).endVertex();
        worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ).endVertex();
        worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.maxZ).endVertex();
        worldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.maxZ).endVertex();
        worldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.minZ).endVertex();
        worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ).endVertex();
        worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.minZ).endVertex();
        worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.maxZ).endVertex();
        worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.maxZ).endVertex();
        worldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.maxZ).endVertex();
        worldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.maxZ).endVertex();
        worldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.minZ).endVertex();
        worldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.minZ).endVertex();
        worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.maxZ).endVertex();
        worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.maxZ).endVertex();
        worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.minZ).endVertex();
        worldRenderer.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ).endVertex();
        worldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.minZ).endVertex();
        worldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.minZ).endVertex();
        worldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.maxZ).endVertex();
        worldRenderer.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.maxZ).endVertex();
        tessellator.draw();
        GlStateManager.enableAlpha();
    }

    private void drawGradientRect(float f, float f2, float f3, float f4, int n, int n2) {
        GlStateManager.disableTexture2D();
        GlStateManager.enableBlend();
        GlStateManager.disableAlpha();
        GlStateManager.tryBlendFuncSeparate((int)770, (int)771, (int)1, (int)0);
        GlStateManager.shadeModel((int)7425);
        GlStateManager.disableCull();
        GL11.glBegin((int)7);
        this.glSetColor(n);
        GL11.glVertex2f((float)f, (float)f2);
        GL11.glVertex2f((float)f, (float)(f2 + f4));
        this.glSetColor(n2);
        GL11.glVertex2f((float)(f + f3), (float)(f2 + f4));
        GL11.glVertex2f((float)(f + f3), (float)f2);
        GL11.glEnd();
        GlStateManager.shadeModel((int)7424);
        GlStateManager.disableBlend();
        GlStateManager.enableAlpha();
        GlStateManager.enableTexture2D();
    }

    public final void renderColorPicker(float f, float f2, float f3, float f4, int n) {
        GlStateManager.disableTexture2D();
        GlStateManager.enableBlend();
        GlStateManager.disableAlpha();
        GlStateManager.tryBlendFuncSeparate((int)770, (int)771, (int)1, (int)0);
        GlStateManager.shadeModel((int)7425);
        GlStateManager.disableCull();
        GL11.glBegin((int)7);
        this.glSetColor(Color.WHITE.getRGB());
        GL11.glVertex2f((float)f, (float)f2);
        this.glSetColor(n);
        GL11.glVertex2f((float)(f + f3), (float)f2);
        this.glSetColor(Color.BLACK.getRGB());
        GL11.glVertex2f((float)(f + f3), (float)(f2 + f4));
        GL11.glVertex2f((float)f, (float)(f2 + f4));
        GL11.glEnd();
        GlStateManager.shadeModel((int)7424);
        GlStateManager.disableBlend();
        GlStateManager.enableAlpha();
        GlStateManager.enableTexture2D();
        Color[][] colorArrayArray = new Color[][]{{Color.RED, Color.GREEN}, {Color.GREEN, Color.BLUE}, {Color.BLUE, Color.RED}};
        float f5 = f3 / (float)colorArrayArray.length;
        for (int i = 0; i < colorArrayArray.length; ++i) {
            this.drawGradientRect(f + (float)i * f5, f2 + f4 + 10.0f, f5, 15.0f, colorArrayArray[i][0].getRGB(), colorArrayArray[i][1].getRGB());
        }
    }

    public final void drawCircle(float f, float f2, float f3, int n, int n2) {
        int n3;
        GlStateManager.pushMatrix();
        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate((int)770, (int)771, (int)1, (int)0);
        GlStateManager.disableTexture2D();
        GlStateManager.disableCull();
        GlStateManager.disableAlpha();
        this.glSetColor(n);
        GL11.glBegin((int)9);
        for (n3 = 0; n3 < 360; ++n3) {
            GL11.glVertex2d((double)((double)f - Math.sin(Math.toRadians(n3)) * (double)f3), (double)((double)f2 + Math.cos(Math.toRadians(n3)) * (double)f3));
        }
        GL11.glEnd();
        this.glSetColor(n2);
        GL11.glBegin((int)2);
        for (n3 = 0; n3 < 360; ++n3) {
            GL11.glVertex2d((double)((double)f - Math.sin(Math.toRadians(n3)) * (double)f3), (double)((double)f2 + Math.cos(Math.toRadians(n3)) * (double)f3));
        }
        GL11.glEnd();
        GlStateManager.enableAlpha();
        GlStateManager.enableCull();
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
        GlStateManager.popMatrix();
    }

    public final Color rainbowColor(float f, float f2, float f3, float f4) {
        return Color.getHSBColor(((float)System.nanoTime() / 1000000.0f + f3) % f4 / f4, f, f2);
    }

    public final void renderAnalogueClock(float f, float f2, float f3, int n) {
        this.drawCircle(f, f2, f3, Color.BLACK.getRGB(), 1);
        int n2 = Calendar.getInstance().getTime().getHours();
        int n3 = Calendar.getInstance().getTime().getMinutes();
        GlStateManager.pushMatrix();
        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate((int)770, (int)771, (int)1, (int)0);
        GlStateManager.disableTexture2D();
        GlStateManager.disableCull();
        GlStateManager.disableAlpha();
        this.glSetColor(n);
        GL11.glEnable((int)2848);
        GL11.glBegin((int)2);
        for (int i = 0; i < 360; ++i) {
            GL11.glVertex2d((double)((double)f + Math.sin(Math.toRadians(i)) * (double)f3), (double)((double)f2 + Math.cos(Math.toRadians(i)) * (double)f3));
        }
        GL11.glEnd();
        GL11.glBegin((int)1);
        GL11.glVertex2f((float)f, (float)f2);
        GL11.glVertex2d((double)((double)f + Math.sin((double)n2 * Math.PI / 6.0) * ((double)f3 / 1.5)), (double)((double)f2 - Math.cos((double)n2 * Math.PI / 6.0) * ((double)f3 / 1.5)));
        GL11.glVertex2f((float)f, (float)f2);
        GL11.glVertex2d((double)((double)f + Math.sin((double)n3 * Math.PI / 30.0) * (double)f3), (double)((double)f2 - Math.cos((double)n3 * Math.PI / 30.0) * (double)f3));
        GL11.glEnd();
        GL11.glDisable((int)2848);
        GlStateManager.enableAlpha();
        GlStateManager.enableCull();
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
        GlStateManager.popMatrix();
    }
}
