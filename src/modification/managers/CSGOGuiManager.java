/*
 * Decompiled with CFR 0.152.
 */
package modification.managers;

import com.google.common.collect.Lists;
import java.io.IOException;
import java.util.List;
import modification.enummerates.Category;
import modification.files.ValueFile;
import modification.gui.GuiConfigEditor;
import modification.gui.GuiPictureButton;
import modification.gui.csgo.Button;
import modification.main.Modification;
import modification.managers.ModuleManager;
import modification.utilities.ColorUtil;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;

public final class CSGOGuiManager
extends GuiScreen {
    public int startX;
    public int startY;
    public int boxWidth;
    private int selected;
    public final List<Button> buttons = Lists.newArrayList();
    public float scale;

    public void initGui() {
        this.scale = 0.0f;
        int n = 36;
        int n2 = 50;
        this.boxWidth = 50 * Category.values().length;
        int n3 = this.startX = (this.width - Category.values().length * 50) / 2;
        for (int i = 0; i < Category.values().length; ++i) {
            this.startY = this.height / 2 - 150;
            this.buttonList.add(new GuiPictureButton(i, n3, this.startY, 36, 36, i, 0, 6, 1, 36, Category.values()[i].displayName, "clickgui"));
            n3 += 50;
        }
        this.buttonList.add(new GuiPictureButton(Category.values().length, this.width - 24, this.height - 24, 24, 24, 1, 2, 5, 3, 24, "Configs", "screens"));
    }

    protected void actionPerformed(GuiButton guiButton) {
        if (guiButton.id == Category.values().length) {
            this.mc.displayGuiScreen((GuiScreen)new GuiConfigEditor(this));
            return;
        }
        this.selected = guiButton.id;
        this.buttons.clear();
        ModuleManager.MODULES.forEach(module -> {
            if (module.category == Category.values()[this.selected]) {
                this.buttons.add(new Button(module));
            }
        });
    }

    public void drawScreen(int n, int n2, float f) {
        float f2 = (float)this.width / 2.0f;
        float f3 = (float)this.height / 2.0f;
        this.scale = Modification.SLIDE_UTIL.slide(this.scale, 0.0f, 1.0f, 0.1f, true);
        GlStateManager.pushMatrix();
        GlStateManager.translate((float)f2, (float)f3, (float)1.0f);
        GlStateManager.scale((float)this.scale, (float)this.scale, (float)1.0f);
        GlStateManager.translate((float)(-f2), (float)(-f3), (float)1.0f);
        Modification.RENDER_UTIL.drawRect(this.startX - 10, this.startY - 10, this.boxWidth + 20, 270.0f, ColorUtil.BACKGROUND);
        Modification.RENDER_UTIL.drawRect(this.startX - 5, this.startY - 5, this.boxWidth + 10, 260.0f, ColorUtil.BACKGROUND_DARKER);
        Modification.RENDER_UTIL.drawRect(this.startX - 5, this.startY + 45, this.boxWidth + 10, 3.0f, -1);
        Modification.RENDER_UTIL.drawRect((float)this.startX + (float)(this.boxWidth - 3) / 2.0f, this.startY + 48, 3.0f, 207.0f, -1);
        super.drawScreen(n, n2, f);
        this.drawString(this.fontRendererObj, "Modules: ", this.startX, this.startY + 50, -1);
        this.drawString(this.fontRendererObj, "Values: ", this.startX + this.boxWidth / 2 + 7, this.startY + 50, -1);
        if (!this.buttons.isEmpty()) {
            float f4 = this.startY + 50 + this.fontRendererObj.FONT_HEIGHT + 5;
            for (Button button : this.buttons) {
                button.x = this.startX + 4;
                button.y = f4;
                button.draw(n, n2);
                f4 += (float)(this.fontRendererObj.FONT_HEIGHT + 5);
            }
        }
        GlStateManager.popMatrix();
    }

    protected void mouseClicked(int n, int n2, int n3) throws IOException {
        super.mouseClicked(n, n2, n3);
        if (!this.buttons.isEmpty()) {
            this.buttons.forEach(button -> button.click(n, n2, n3));
        }
    }

    protected void mouseReleased(int n, int n2, int n3) {
        super.mouseReleased(n, n2, n3);
        if (!this.buttons.isEmpty()) {
            this.buttons.forEach(button -> button.release(n3));
        }
    }

    public boolean doesGuiPauseGame() {
        return true;
    }

    public void onGuiClosed() {
        Modification.FILE_MANAGER.update(ValueFile.class);
    }
}
