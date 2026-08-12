/*
 * Decompiled with CFR 0.152.
 */
package modification.managers;

import com.google.common.collect.Lists;
import java.awt.Color;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import modification.enummerates.Category;
import modification.files.ClickGuiFile;
import modification.files.ValueFile;
import modification.gui.GuiConfigEditor;
import modification.gui.GuiPictureButton;
import modification.gui.click.Panel;
import modification.main.Modification;
import modification.modules.misc.GUI;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;

public final class ClickGuiManager
extends GuiScreen {
    public static final List<Panel> PANELS = Lists.newArrayList();
    private float scale;

    public final void initialize() {
        float f = 50.0f;
        for (Category category : Category.values()) {
            PANELS.add(new Panel(category.displayName, 50.0f, f));
            f += 25.0f;
        }
    }

    public void initGui() {
        this.scale = 0.0f;
        this.buttonList.add(new GuiPictureButton(Category.values().length, this.width - 24, this.height - 24, 24, 24, 1, 2, 5, 3, 24, "Configs", "screens"));
    }

    protected void actionPerformed(GuiButton guiButton) throws IOException {
        if (guiButton.id == Category.values().length) {
            this.mc.displayGuiScreen((GuiScreen)new GuiConfigEditor(this));
        }
    }

    public void drawScreen(int n, int n2, float f) {
        GUI gUI = (GUI)Objects.requireNonNull(Modification.MODULE_MANAGER.checkModuleForName("GUI"));
        if (((String)gUI.theme.value).equals("Abraxas")) {
            this.drawGradientRect(0, 0, this.width, this.height, Integer.MIN_VALUE, new Color(255, 50, 0, 255).getRGB());
        }
        float f2 = (float)this.width / 2.0f;
        float f3 = (float)this.height / 2.0f;
        this.scale = Modification.SLIDE_UTIL.slide(this.scale, 0.0f, 1.0f, 0.1f, true);
        GlStateManager.pushMatrix();
        GlStateManager.translate((float)f2, (float)f3, (float)1.0f);
        GlStateManager.scale((float)this.scale, (float)this.scale, (float)1.0f);
        GlStateManager.translate((float)(-f2), (float)(-f3), (float)1.0f);
        PANELS.forEach(panel -> panel.draw(n, n2));
        super.drawScreen(n, n2, f);
        GlStateManager.popMatrix();
    }

    protected void mouseClicked(int n, int n2, int n3) throws IOException {
        super.mouseClicked(n, n2, n3);
        for (Panel panel : PANELS) {
            if (!panel.clicked(n, n2, n3)) continue;
            return;
        }
    }

    protected void mouseReleased(int n, int n2, int n3) {
        PANELS.forEach(panel -> panel.release(n3));
        super.mouseReleased(n, n2, n3);
    }

    public boolean doesGuiPauseGame() {
        return true;
    }

    public void onGuiClosed() {
        Modification.FILE_MANAGER.update(ClickGuiFile.class);
        Modification.FILE_MANAGER.update(ValueFile.class);
    }

    public final Panel checkPanelForName(String string) {
        if (!PANELS.isEmpty()) {
            for (Panel panel : PANELS) {
                if (!panel.name.equals(string)) continue;
                return panel;
            }
        }
        return null;
    }
}
