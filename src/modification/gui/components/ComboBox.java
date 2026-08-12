/*
 * Decompiled with CFR 0.152.
 */
package modification.gui.components;

import java.awt.Color;
import java.util.Objects;
import modification.extenders.Value;
import modification.gui.Component;
import modification.main.Modification;
import modification.managers.CSGOGuiManager;
import modification.managers.ClickGuiManager;
import modification.modules.misc.GUI;
import modification.utilities.ColorUtil;

public final class ComboBox
extends Component {
    private boolean extended;

    public ComboBox(Value value) {
        super(value);
        this.heightOffset = 2;
    }

    @Override
    public void draw(int n, int n2) {
        GUI gUI = (GUI)Objects.requireNonNull(Modification.MODULE_MANAGER.checkModuleForName("GUI"));
        switch ((String)gUI.theme.value) {
            case "Icarus": {
                Modification.RENDER_UTIL.drawBorderedRect(this.x + 5.0f, this.y, 110.0f, 15.0f, 1, this.extended ? Color.GRAY.getRGB() : Color.DARK_GRAY.getRGB(), Color.BLACK.getRGB());
                ComboBox.MC.fontRendererObj.drawStringWithShadow(this.value.displayName, this.x + 5.0f + (float)(110 - ComboBox.MC.fontRendererObj.getStringWidth(this.value.displayName)) / 2.0f - 0.5f, this.y + (float)(15 - ComboBox.MC.fontRendererObj.FONT_HEIGHT) / 2.0f, -1);
                this.heightOffset = 1;
                if (!this.extended) break;
                this.heightOffset = this.value.modes.size() + 1;
                Modification.RENDER_UTIL.drawBorderedRect(this.x + 12.0f, this.y + 16.0f, 98.0f, 16 * this.value.modes.size(), 1, Color.GRAY.getRGB(), Color.BLACK.getRGB());
                float f = this.y + 16.0f;
                for (String string : this.value.modes) {
                    Modification.RENDER_UTIL.drawBorderedRect(this.x + 15.0f, f, 92.0f, 13.0f, 1, this.value.value.equals(string) ? ColorUtil.ICARUS_CLICK_GUI : Color.DARK_GRAY.getRGB(), Color.BLACK.getRGB());
                    ComboBox.MC.fontRendererObj.drawStringWithShadow(String.valueOf(string), this.x + 12.0f + (float)(98 - ComboBox.MC.fontRendererObj.getStringWidth(String.valueOf(string))) / 2.0f - 0.5f, f + (float)(13 - ComboBox.MC.fontRendererObj.FONT_HEIGHT) / 2.0f, -1);
                    f += 16.0f;
                }
                break;
            }
            case "Abraxas": {
                Modification.RENDER_UTIL.drawRect(this.x, this.y, 100.0f, 15.0f, ColorUtil.MIN_HOVERING);
                Modification.RENDER_UTIL.drawRect(this.x, this.y, 100.0f, 15.0f, ColorUtil.MIN_HOVERING);
                ComboBox.MC.fontRendererObj.drawStringWithShadow(this.value.displayName.concat(": ").concat(String.valueOf(this.value.value)), this.x + (float)(100 - ComboBox.MC.fontRendererObj.getStringWidth(this.value.displayName.concat(": ").concat(String.valueOf(this.value.value)))) / 2.0f - 0.5f, this.y + (float)(15 - ComboBox.MC.fontRendererObj.FONT_HEIGHT) / 2.0f, -1);
                this.heightOffset = 1;
                if (!this.extended) break;
                this.heightOffset = this.value.modes.size();
                float f = this.y + 15.0f;
                for (String string : this.value.modes) {
                    if (this.value.value.equals(string)) continue;
                    Modification.RENDER_UTIL.drawRect(this.x, f, 100.0f, 15.0f, ColorUtil.MIN_HOVERING);
                    if (this.value.value.equals(string)) {
                        Modification.RENDER_UTIL.drawRect(this.x, f, 100.0f, 15.0f, ColorUtil.MAIN_COLOR);
                    }
                    ComboBox.MC.fontRendererObj.drawStringWithShadow(String.valueOf(string), this.x + (float)(100 - ComboBox.MC.fontRendererObj.getStringWidth(String.valueOf(string))) / 2.0f - 0.5f, f + (float)(15 - ComboBox.MC.fontRendererObj.FONT_HEIGHT) / 2.0f, -1);
                    f += 15.0f;
                }
                break;
            }
            default: {
                ComboBox.MC.fontRendererObj.drawStringWithShadow(this.value.displayName, this.x, this.y, -1);
                Modification.RENDER_UTIL.drawRect(this.x + 4.0f, this.y + (float)ComboBox.MC.fontRendererObj.FONT_HEIGHT + 2.0f, 60.0f, 10.0f, Color.BLACK.getRGB());
                ComboBox.MC.fontRendererObj.drawStringWithShadow(String.valueOf(this.value.value), this.x + 5.0f, this.y + (float)ComboBox.MC.fontRendererObj.FONT_HEIGHT + 3.5f, -1);
                this.heightOffset = 2;
                if (!this.extended) break;
                this.heightOffset = this.value.modes.size();
                float f = this.y + (float)ComboBox.MC.fontRendererObj.FONT_HEIGHT + 12.0f;
                for (String string : this.value.modes) {
                    if (string.equals(this.value.value)) continue;
                    Modification.RENDER_UTIL.drawRect(this.x + 4.0f, f, 60.0f, 10.0f, Color.BLACK.getRGB());
                    ComboBox.MC.fontRendererObj.drawStringWithShadow(String.valueOf(string), this.x + 7.0f, f + 1.5f, -1);
                    f += 10.0f;
                }
            }
        }
    }

    @Override
    public void click(int n, int n2, int n3) {
        if (n3 == 0) {
            GUI gUI = (GUI)Objects.requireNonNull(Modification.MODULE_MANAGER.checkModuleForName("GUI"));
            switch ((String)gUI.theme.value) {
                case "Icarus": {
                    if (Modification.RENDER_UTIL.mouseHovered(n, n2, this.x + 5.0f, this.y, 110.0f, 20.0f)) {
                        boolean bl = this.extended = !this.extended;
                    }
                    if (!this.extended) break;
                    float f = this.y + 16.0f;
                    for (String string : this.value.modes) {
                        if (Modification.RENDER_UTIL.mouseHovered(n, n2, this.x + 15.0f, f, 92.0f, 13.0f)) {
                            this.value.value = string;
                            if ((ComboBox.MC.currentScreen instanceof ClickGuiManager || ComboBox.MC.currentScreen instanceof CSGOGuiManager) && this.value.module.name.equals("GUI") && (this.value.value.equals("Eject") || this.value.value.equals("Icarus") || this.value.value.equals("Abraxas"))) {
                                MC.displayGuiScreen(null);
                            }
                        }
                        f += 16.0f;
                    }
                    break;
                }
                case "Abraxas": {
                    if (Modification.RENDER_UTIL.mouseHovered(n, n2, this.x, this.y, 100.0f, 15.0f)) {
                        boolean bl = this.extended = !this.extended;
                    }
                    if (!this.extended) break;
                    float f = this.y + 15.0f;
                    for (String string : this.value.modes) {
                        if (this.value.value.equals(string)) continue;
                        if (Modification.RENDER_UTIL.mouseHovered(n, n2, this.x, f, 100.0f, 15.0f)) {
                            this.value.value = string;
                            this.extended = false;
                            if ((ComboBox.MC.currentScreen instanceof ClickGuiManager || ComboBox.MC.currentScreen instanceof CSGOGuiManager) && this.value.module.name.equals("GUI") && (this.value.value.equals("Eject") || this.value.value.equals("Icarus") || this.value.value.equals("Abraxas"))) {
                                MC.displayGuiScreen(null);
                            }
                        }
                        f += 15.0f;
                    }
                    break;
                }
                default: {
                    if (Modification.RENDER_UTIL.mouseHovered(n, n2, this.x + 4.0f, this.y + (float)ComboBox.MC.fontRendererObj.FONT_HEIGHT + 2.0f, 60.0f, 10.0f)) {
                        boolean bl = this.extended = !this.extended;
                    }
                    if (!this.extended) break;
                    float f = this.y + (float)ComboBox.MC.fontRendererObj.FONT_HEIGHT + 12.0f;
                    for (String string : this.value.modes) {
                        if (string.equals(this.value.value)) continue;
                        if (Modification.RENDER_UTIL.mouseHovered(n, n2, this.x + 4.0f, f, 60.0f, 10.0f)) {
                            this.value.value = string;
                            this.extended = false;
                            if ((ComboBox.MC.currentScreen instanceof ClickGuiManager || ComboBox.MC.currentScreen instanceof CSGOGuiManager) && this.value.module.name.equals("GUI") && (this.value.value.equals("Eject") || this.value.value.equals("Icarus") || this.value.value.equals("Abraxas"))) {
                                MC.displayGuiScreen(null);
                            }
                        }
                        f += 10.0f;
                    }
                }
            }
        }
    }

    @Override
    public void release(int n) {
    }
}
