/*
 * Decompiled with CFR 0.152.
 */
package modification.gui;

import java.io.File;
import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import modification.extenders.Config;
import modification.gui.GuiPictureButton;
import modification.interfaces.MCHook;
import modification.main.Modification;
import modification.managers.ConfigManager;
import modification.utilities.ColorUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.MathHelper;
import org.lwjgl.input.Mouse;

public final class GuiConfigEditor
extends GuiScreen
implements MCHook {
    private final GuiScreen guiScreen;
    private GuiTextField configField;
    private Config current;
    private Config lastSelected;
    private long lastClicked;
    private int offset;
    private int listSize;
    private float scale;

    public GuiConfigEditor(GuiScreen guiScreen) {
        this.guiScreen = guiScreen;
    }

    public void initGui() {
        this.current = null;
        this.scale = 0.0f;
        this.offset = 0;
        this.listSize = ConfigManager.CONFIGS.size();
        int n = 24;
        String[] stringArray = new String[]{"Add", "Remove", "Save", "Clear", "Load", "Refresh"};
        int n2 = Modification.CSGO_GUI_MANAGER.startY + (250 - stringArray.length * 28) / 2;
        for (int i = 0; i < stringArray.length; ++i) {
            this.buttonList.add(new GuiPictureButton(i, Modification.CSGO_GUI_MANAGER.startX, n2, 24, 24, i == 5 ? 1 : (i == 3 ? 0 : i), i == 5 ? 1 : (i == 3 ? 2 : 0), 5, 3, 24, stringArray[i], "screens"));
            n2 += 28;
        }
        this.buttonList.forEach(guiButton -> {
            guiButton.enabled = guiButton.id == 5;
        });
        this.configField = new GuiTextField(0, this.fontRendererObj, Modification.CSGO_GUI_MANAGER.startX, Modification.CSGO_GUI_MANAGER.startY + 250 - 10, 100, 10);
        this.configField.setMaxStringLength(1000);
        this.configField.setText("");
        this.configField.setFocused(false);
    }

    protected void keyTyped(char c, int n) {
        this.configField.textboxKeyTyped(c, n);
        if (n == 1) {
            this.mc.displayGuiScreen(this.guiScreen);
        }
    }

    protected void actionPerformed(GuiButton guiButton) {
        List list = ConfigManager.CONFIGS;
        switch (guiButton.id) {
            case 0: {
                Config config2 = new Config(this.configField.getText());
                this.configField.setText("");
                this.configField.setFocused(false);
                config2.write();
                this.listSize = ConfigManager.CONFIGS.size();
                break;
            }
            case 1: {
                for (int i = 0; i < list.size(); ++i) {
                    File file = ((Config)list.get((int)i)).file;
                    if (!file.getName().equals(this.current.file.getName()) || !file.delete()) continue;
                    Modification.LOG_UTIL.sendConsoleMessage("Deleted config ".concat(((Config)list.get((int)i)).file.getName()).concat(" successfully"));
                }
                ConfigManager.CONFIGS.remove(this.current);
                this.current = null;
                this.listSize = ConfigManager.CONFIGS.size();
                break;
            }
            case 2: {
                this.current.write();
                break;
            }
            case 3: {
                for (int i = 0; i < list.size(); ++i) {
                    if (!((Config)list.get((int)i)).file.delete()) continue;
                    Modification.LOG_UTIL.sendConsoleMessage("Deleted config ".concat(((Config)list.get((int)i)).file.getName()).concat(" successfully"));
                }
                this.current = null;
                ConfigManager.CONFIGS.clear();
                this.listSize = ConfigManager.CONFIGS.size();
                break;
            }
            case 4: {
                this.current.read();
                break;
            }
            case 5: {
                ConfigManager.CONFIGS.clear();
                for (File file : Objects.requireNonNull(Modification.CONFIG_DIRECTORY.listFiles())) {
                    if (file == null || !file.getName().toLowerCase().endsWith(".cfg")) continue;
                    new Config(file.getName().substring(0, file.getName().length() - 4));
                }
                ConfigManager.CONFIGS.sort(Comparator.comparing(config -> config.name));
                this.listSize = ConfigManager.CONFIGS.size();
            }
        }
    }

    public void drawScreen(int n, int n2, float f) {
        Modification.RENDER_UTIL.drawRect(0.0f, 0.0f, this.width, this.height, -872415232);
        float f2 = (float)this.width / 2.0f;
        float f3 = (float)this.height / 2.0f;
        this.scale = Modification.SLIDE_UTIL.slide(this.scale, 0.0f, 1.0f, 0.1f, true);
        GlStateManager.pushMatrix();
        GlStateManager.translate((float)f2, (float)f3, (float)1.0f);
        GlStateManager.scale((float)this.scale, (float)this.scale, (float)1.0f);
        GlStateManager.translate((float)(-f2), (float)(-f3), (float)1.0f);
        this.buttonList.forEach(guiButton -> {
            guiButton.enabled = guiButton.id == 0 && !this.configField.getText().isEmpty() || guiButton.id == 5 || guiButton.id == 3 && !ConfigManager.CONFIGS.isEmpty() || this.current != null;
        });
        Modification.RENDER_UTIL.drawRect(Modification.CSGO_GUI_MANAGER.startX - 10, Modification.CSGO_GUI_MANAGER.startY - 10, Modification.CSGO_GUI_MANAGER.boxWidth + 20, 270.0f, ColorUtil.BACKGROUND);
        Modification.RENDER_UTIL.drawRect(Modification.CSGO_GUI_MANAGER.startX - 5, Modification.CSGO_GUI_MANAGER.startY - 5, Modification.CSGO_GUI_MANAGER.boxWidth + 10, 260.0f, ColorUtil.BACKGROUND_DARKER);
        GuiConfigEditor.MC.fontRendererObj.drawStringWithShadow("Offline Configs", (float)Modification.CSGO_GUI_MANAGER.startX + (float)(Modification.CSGO_GUI_MANAGER.boxWidth - GuiConfigEditor.MC.fontRendererObj.getStringWidth("Offline Configs")) / 2.0f, Modification.CSGO_GUI_MANAGER.startY + 2, -1);
        float f4 = Modification.CSGO_GUI_MANAGER.startY + 15 - this.offset;
        Modification.SCISSOR_UTIL.begin();
        Modification.SCISSOR_UTIL.scissor(new ScaledResolution(this.mc), 0.0f, Modification.CSGO_GUI_MANAGER.startY + this.fontRendererObj.FONT_HEIGHT + 3, this.width, 250 - this.fontRendererObj.FONT_HEIGHT * 2 - 2);
        for (Config config : ConfigManager.CONFIGS) {
            Modification.RENDER_UTIL.drawRect((float)(this.width - 150) / 2.0f, f4, 150.0f, 30.0f, this.current == config ? ColorUtil.MAIN_COLOR : ColorUtil.MIN_HOVERING);
            GuiConfigEditor.MC.fontRendererObj.drawStringWithShadow(config.name, (float)(this.width - GuiConfigEditor.MC.fontRendererObj.getStringWidth(config.name)) / 2.0f, f4 + (float)(30 - GuiConfigEditor.MC.fontRendererObj.FONT_HEIGHT) / 2.0f, -1);
            f4 += 34.0f;
        }
        Modification.SCISSOR_UTIL.end();
        this.configField.drawTextBox();
        super.drawScreen(n, n2, f);
        GlStateManager.popMatrix();
        if (Mouse.hasWheel() && this.listSize > 6) {
            int n3 = 34 * (this.listSize - 6);
            this.offset = (int)((float)this.offset - Math.signum(Mouse.getDWheel()) * 34.0f);
            this.offset = (int)MathHelper.clamp_float((float)this.offset, (float)0.0f, (float)n3);
            Modification.RENDER_UTIL.drawRect((float)(this.width + 200) / 2.0f + 4.0f, Modification.CSGO_GUI_MANAGER.startY + this.fontRendererObj.FONT_HEIGHT + 4, 3.0f, 250 - this.fontRendererObj.FONT_HEIGHT * 2 - 4, ColorUtil.MIN_HOVERING);
            Modification.RENDER_UTIL.drawRect((float)(this.width + 200) / 2.0f + 4.0f, Modification.CSGO_GUI_MANAGER.startY + this.fontRendererObj.FONT_HEIGHT + 4 + this.offset / this.listSize, 3.0f, 250 - this.fontRendererObj.FONT_HEIGHT * 2 - 4 - n3 / this.listSize, ColorUtil.MAIN_COLOR);
        }
    }

    protected void mouseClicked(int n, int n2, int n3) throws IOException {
        super.mouseClicked(n, n2, n3);
        this.configField.mouseClicked(n, n2, n3);
        float f = Modification.CSGO_GUI_MANAGER.startY + 15 - this.offset;
        for (Config config : ConfigManager.CONFIGS) {
            if (Modification.RENDER_UTIL.mouseHovered(n, n2, (float)(this.width - 150) / 2.0f, f, 150.0f, 30.0f)) {
                if (config == this.current) {
                    if (this.lastSelected == this.current && Minecraft.getSystemTime() - this.lastClicked < 250L) {
                        this.current.read();
                        return;
                    }
                    this.current = null;
                    return;
                }
                this.lastSelected = this.current = config;
                this.lastClicked = Minecraft.getSystemTime();
            }
            f += 34.0f;
        }
    }

    public boolean doesGuiPauseGame() {
        return false;
    }

    public void updateScreen() {
        this.configField.updateCursorCounter();
    }
}
