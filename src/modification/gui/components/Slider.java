/*
 * Decompiled with CFR 0.152.
 */
package modification.gui.components;

import java.awt.Color;
import java.util.Objects;
import modification.extenders.Value;
import modification.gui.Component;
import modification.main.Modification;
import modification.modules.misc.GUI;
import modification.utilities.ColorUtil;
import net.minecraft.util.MathHelper;

public final class Slider
extends Component {
    private boolean dragging;

    public Slider(Value value) {
        super(value);
        this.heightOffset = 2;
    }

    @Override
    public void draw(int n, int n2) {
        float f = (((Float)this.value.value).floatValue() - this.value.min) / (this.value.max - this.value.min);
        GUI gUI = (GUI)Objects.requireNonNull(Modification.MODULE_MANAGER.checkModuleForName("GUI"));
        switch ((String)gUI.theme.value) {
            case "Icarus": {
                this.heightOffset = 1;
                String string = this.value.displayName.concat(": ").concat(Float.toString(((Float)this.value.value).floatValue()));
                Modification.RENDER_UTIL.drawBorderedRect(this.x, this.y, 120.0f, 15.0f, 1, Color.DARK_GRAY.getRGB(), Color.BLACK.getRGB());
                Modification.RENDER_UTIL.drawRect(this.x, this.y, 120.0f * f, 15.0f, ColorUtil.ICARUS_CLICK_GUI);
                Slider.MC.fontRendererObj.drawStringWithShadow(string, this.x + (float)(120 - Slider.MC.fontRendererObj.getStringWidth(string)) / 2.0f, this.y + (float)(15 - Slider.MC.fontRendererObj.FONT_HEIGHT) / 2.0f, -1);
                if (!this.dragging) break;
                float f2 = this.value.min + MathHelper.clamp_float((float)(((float)n - this.x) / 120.0f), (float)0.0f, (float)1.0f) * (this.value.max - this.value.min);
                this.value.value = Float.valueOf((float)((double)Math.round((double)f2 * Math.pow(10.0, this.value.dataTypeValue)) / Math.pow(10.0, this.value.dataTypeValue)));
                break;
            }
            case "Abraxas": {
                this.heightOffset = 1;
                String string = this.value.displayName.concat(": ").concat(Float.toString(((Float)this.value.value).floatValue()));
                Modification.RENDER_UTIL.drawRect(this.x, this.y, 100.0f, 15.0f, ColorUtil.MIN_HOVERING);
                Modification.RENDER_UTIL.drawRect(this.x, this.y, 100.0f * f, 15.0f, ColorUtil.MAIN_COLOR);
                Slider.MC.fontRendererObj.drawStringWithShadow(string, this.x + (float)(100 - Slider.MC.fontRendererObj.getStringWidth(string)) / 2.0f, this.y + (float)(15 - Slider.MC.fontRendererObj.FONT_HEIGHT) / 2.0f, -1);
                if (!this.dragging) break;
                float f3 = this.value.min + MathHelper.clamp_float((float)(((float)n - this.x) / 100.0f), (float)0.0f, (float)1.0f) * (this.value.max - this.value.min);
                this.value.value = Float.valueOf((float)((double)Math.round((double)f3 * Math.pow(10.0, this.value.dataTypeValue)) / Math.pow(10.0, this.value.dataTypeValue)));
                break;
            }
            default: {
                Slider.MC.fontRendererObj.drawStringWithShadow(this.value.displayName, this.x, this.y, -1);
                Modification.RENDER_UTIL.drawRect(this.x + 4.0f, this.y + (float)Slider.MC.fontRendererObj.FONT_HEIGHT + 2.0f, 100.0f, 15.0f, Color.BLACK.getRGB());
                Modification.RENDER_UTIL.drawOutlinedRect(this.x + 4.0f, this.y + (float)Slider.MC.fontRendererObj.FONT_HEIGHT + 2.0f, 100.0f, 15.0f, 2, Color.BLACK.getRGB());
                Modification.RENDER_UTIL.drawRect(this.x + 4.0f, this.y + (float)Slider.MC.fontRendererObj.FONT_HEIGHT + 2.0f, 100.0f * f, 15.0f, ColorUtil.MAIN_COLOR);
                Slider.MC.fontRendererObj.drawStringWithShadow(Float.toString(((Float)this.value.value).floatValue()), this.x + (float)(100 - Slider.MC.fontRendererObj.getStringWidth(Float.toString(((Float)this.value.value).floatValue()))) / 2.0f, this.y + (float)Slider.MC.fontRendererObj.FONT_HEIGHT + 5.0f, -1);
                if (!this.dragging) break;
                float f4 = this.value.min + MathHelper.clamp_float((float)(((float)n - (this.x + 4.0f)) / 92.0f), (float)0.0f, (float)1.0f) * (this.value.max - this.value.min);
                this.value.value = Float.valueOf((float)((double)Math.round((double)f4 * Math.pow(10.0, this.value.dataTypeValue)) / Math.pow(10.0, this.value.dataTypeValue)));
            }
        }
    }

    @Override
    public void click(int n, int n2, int n3) {
        GUI gUI = (GUI)Objects.requireNonNull(Modification.MODULE_MANAGER.checkModuleForName("GUI"));
        if (n3 == 0) {
            switch ((String)gUI.theme.value) {
                case "Icarus": {
                    this.dragging = Modification.RENDER_UTIL.mouseHovered(n, n2, this.x, this.y, 120.0f, 15.0f);
                    break;
                }
                case "Abraxas": {
                    this.dragging = Modification.RENDER_UTIL.mouseHovered(n, n2, this.x, this.y, 100.0f, 15.0f);
                    break;
                }
                default: {
                    this.dragging = Modification.RENDER_UTIL.mouseHovered(n, n2, this.x + 4.0f, this.y + (float)Slider.MC.fontRendererObj.FONT_HEIGHT + 2.0f, 100.0f, 15.0f);
                }
            }
        }
    }

    @Override
    public void release(int n) {
        if (n == 0) {
            this.dragging = false;
        }
    }
}
