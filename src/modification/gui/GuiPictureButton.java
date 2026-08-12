/*
 * Decompiled with CFR 0.152.
 */
package modification.gui;

import java.awt.Color;
import modification.main.Modification;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.util.ResourceLocation;

public final class GuiPictureButton
extends GuiButton {
    private static final FontRenderer MINI_RENDERER = new FontRenderer("Arial", 1, 12, false);
    private final ResourceLocation location;
    private final int offsetX;
    private final int offsetY;
    private final int sizeX;
    private final int sizeY;
    private final int renderSize;

    public GuiPictureButton(int n, int n2, int n3, int n4, int n5, int n6, int n7, int n8, int n9, int n10, String string, String string2) {
        super(n, n2, n3, n4, n5, string);
        this.offsetX = n6;
        this.offsetY = n7;
        this.sizeX = n8;
        this.sizeY = n9;
        this.renderSize = n10;
        this.location = new ResourceLocation("modification/".concat("sprite_").concat(string2).concat(".png"));
    }

    public void drawButton(Minecraft minecraft, int n, int n2) {
        this.hovered = Modification.RENDER_UTIL.mouseHovered(n, n2, this.xPosition, this.yPosition, this.width, this.height);
        Modification.RENDER_UTIL.drawSprite(this.location, this.xPosition, this.yPosition + (this.height - this.renderSize) / 2, this.offsetX, this.offsetY, this.sizeX, this.sizeY, this.renderSize, this.enabled ? Color.WHITE.getRGB() : Color.DARK_GRAY.getRGB());
        if (this.hovered) {
            Modification.RENDER_UTIL.drawRect((float)n - (float)MINI_RENDERER.getStringWidth(this.displayString) / 2.0f, n2 - 5, MINI_RENDERER.getStringWidth(this.displayString) + 2, GuiPictureButton.MINI_RENDERER.FONT_HEIGHT + 2, Color.BLACK.getRGB());
            this.drawString(MINI_RENDERER, this.displayString, n - MINI_RENDERER.getStringWidth(this.displayString) / 2 + 1, n2 - 3, this.enabled ? -1 : Color.DARK_GRAY.getRGB());
        }
    }

    public void playPressSound(SoundHandler soundHandler) {
    }
}
