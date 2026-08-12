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

public final class CheckBox
extends Component {
    private float alpha;

    public CheckBox(Value value) {
        super(value);
    }

    @Override
    public void draw(int n, int n2) {
        GUI gUI = (GUI)Objects.requireNonNull(Modification.MODULE_MANAGER.checkModuleForName("GUI"));
        switch ((String)gUI.theme.value) {
            case "Icarus": {
                Modification.RENDER_UTIL.drawBorderedRect(this.x + 2.5f, this.y + 2.5f, 10.0f, 10.0f, 1, (Boolean)this.value.value != false ? ColorUtil.ICARUS_CLICK_GUI : Color.BLACK.getRGB(), Color.BLACK.getRGB());
                CheckBox.MC.fontRendererObj.drawStringWithShadow(this.value.displayName, this.x + 19.0f, this.y + (float)(15 - CheckBox.MC.fontRendererObj.FONT_HEIGHT) / 2.0f + 0.5f, -1);
                break;
            }
            case "Abraxas": {
                Modification.RENDER_UTIL.drawRect(this.x, this.y, 100.0f, 15.0f, ColorUtil.MIN_HOVERING);
                this.alpha = Modification.SLIDE_UTIL.slide(this.alpha, 0.1f, 0.3f, 0.1f, (Boolean)this.value.value);
                Modification.RENDER_UTIL.drawRect(this.x + 2.5f, this.y + 2.5f, 10.0f, 10.0f, new Color(1.0f, 1.0f, 1.0f, this.alpha).getRGB());
                CheckBox.MC.fontRendererObj.drawStringWithShadow(this.value.displayName, this.x + 19.0f, this.y + (float)(15 - CheckBox.MC.fontRendererObj.FONT_HEIGHT) / 2.0f + 0.5f, -1);
                break;
            }
            default: {
                Modification.RENDER_UTIL.drawRect(this.x, this.y, 10.0f, 10.0f, (Boolean)this.value.value != false ? ColorUtil.MAIN_COLOR : Color.BLACK.getRGB());
                Modification.RENDER_UTIL.drawOutlinedRect(this.x, this.y, 10.0f, 10.0f, 2, Color.BLACK.getRGB());
                CheckBox.MC.fontRendererObj.drawStringWithShadow(this.value.displayName, this.x + 14.0f, this.y + 1.5f, -1);
            }
        }
    }

    @Override
    public void click(int n, int n2, int n3) {
        if (n3 == 0 && Modification.RENDER_UTIL.mouseHovered(n, n2, this.x, this.y, 10.0f, 10.0f)) {
            this.value.value = (Boolean)this.value.value == false;
        }
    }

    @Override
    public void release(int n) {
    }
}
