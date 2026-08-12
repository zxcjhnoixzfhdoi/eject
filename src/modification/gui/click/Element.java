/*
 * Decompiled with CFR 0.152.
 */
package modification.gui.click;

import com.google.common.collect.Lists;
import java.awt.Color;
import java.util.List;
import java.util.Objects;
import modification.extenders.Module;
import modification.extenders.Value;
import modification.gui.Component;
import modification.gui.components.CheckBox;
import modification.gui.components.ColorPicker;
import modification.gui.components.ComboBox;
import modification.gui.components.Slider;
import modification.interfaces.MCHook;
import modification.main.Modification;
import modification.managers.ValueManager;
import modification.modules.misc.GUI;
import modification.utilities.ColorUtil;
import net.minecraft.client.gui.ScaledResolution;

public final class Element
implements MCHook {
    private final Module module;
    private boolean opened;
    private boolean hovered;
    private float x;
    private float y;
    private float width;
    private float slide;
    private float componentHeight;
    private float alpha;
    public float height;
    private final List<Component> components;

    public Element(Module module) {
        this.module = module;
        this.components = Lists.newArrayList();
        ValueManager.VALUES.forEach(value -> {
            if (value.module == module) {
                switch (value.mode) {
                    case 0: {
                        this.components.add(new CheckBox((Value)value));
                        break;
                    }
                    case 1: {
                        this.components.add(new Slider((Value)value));
                        break;
                    }
                    case 2: {
                        this.components.add(new ComboBox((Value)value));
                    }
                }
            }
        });
        if (module.name.equals("GUI")) {
            this.components.add(new ColorPicker());
        }
    }

    public final void addSettings(float f, float f2, float f3, float f4) {
        this.x = f;
        this.y = f2;
        this.width = f3;
        this.height = f4;
    }

    public final void draw(int n, int n2) {
        GUI gUI = (GUI)Objects.requireNonNull(Modification.MODULE_MANAGER.checkModuleForName("GUI"));
        switch ((String)gUI.theme.value) {
            case "Icarus": {
                Modification.RENDER_UTIL.drawBorderedRect(this.x, this.y, this.width, this.height, 1, this.module.enabled ? ColorUtil.ICARUS_CLICK_GUI : Color.DARK_GRAY.getRGB(), Color.BLACK.getRGB());
                this.hovered = Modification.RENDER_UTIL.mouseHovered(n, n2, this.x, this.y, this.width, this.height);
                if (this.hovered) {
                    Modification.RENDER_UTIL.drawRect(this.x, this.y, this.width, this.height, Integer.MAX_VALUE);
                }
                Element.MC.fontRendererObj.drawStringWithShadow(this.module.name, this.x + (this.width - (float)Element.MC.fontRendererObj.getStringWidth(this.module.name)) / 2.0f - 0.5f, this.y + (this.height - (float)Element.MC.fontRendererObj.FONT_HEIGHT) / 2.0f, -1);
                this.componentHeight = 0.0f;
                for (Component object : this.components) {
                    if (object.value != null && !object.value.shouldRender()) continue;
                    this.componentHeight += (float)object.heightOffset * this.height + 3.0f;
                    if (!(object instanceof ColorPicker)) continue;
                    this.componentHeight += 133.0f;
                }
                this.slide = Modification.SLIDE_UTIL.slide(this.slide, -0.1f, this.width + 26.0f, 0.1f, this.opened);
                Modification.SCISSOR_UTIL.begin();
                Modification.SCISSOR_UTIL.scissor(new ScaledResolution(MC), this.x + this.width + 5.0f, this.y - 1.0f, this.slide + 3.0f, this.componentHeight + 6.0f);
                float f = this.y + 3.0f;
                if (this.slide > 0.0f) {
                    Modification.RENDER_UTIL.drawBorderedRect(this.x + this.width + 6.0f, this.y, this.slide, this.componentHeight + 3.0f, 1, Color.GRAY.getRGB(), Color.BLACK.getRGB());
                    for (Component component : this.components) {
                        if (component.value != null && !component.value.shouldRender()) continue;
                        component.x = this.x + this.width + 9.0f;
                        component.y = f;
                        component.draw(n, n2);
                        f += (float)component.heightOffset * this.height + 3.0f;
                    }
                }
                Modification.SCISSOR_UTIL.end();
                break;
            }
            case "Abraxas": {
                this.hovered = Modification.RENDER_UTIL.mouseHovered(n, n2, this.x, this.y, this.width, this.height);
                this.alpha = Modification.SLIDE_UTIL.slide(this.alpha, 0.1f, 0.3f, 0.1f, this.hovered);
                Modification.RENDER_UTIL.drawRect(this.x, this.y, this.width, this.height, new Color(1.0f, 1.0f, 1.0f, this.alpha).getRGB());
                Element.MC.fontRendererObj.drawStringWithShadow(this.module.name, this.x + (this.width - (float)Element.MC.fontRendererObj.getStringWidth(this.module.name)) / 2.0f - 0.5f, this.y + (this.height - (float)Element.MC.fontRendererObj.FONT_HEIGHT) / 2.0f, this.module.enabled ? -1 : -1711276033);
                if (!this.opened) break;
                float f = this.y;
                for (Component component : this.components) {
                    if (component.value != null && !component.value.shouldRender()) continue;
                    component.x = this.x + this.width + 2.0f;
                    component.y = f;
                    component.draw(n, n2);
                    f += (float)component.heightOffset * this.height + 1.0f;
                }
                break;
            }
        }
    }

    public final boolean clicked(int n, int n2, int n3) {
        if (this.hovered) {
            switch (n3) {
                case 0: {
                    this.module.toggle();
                    break;
                }
                case 1: {
                    if (this.components == null || this.components.isEmpty()) break;
                    boolean bl = this.opened = !this.opened;
                }
            }
        }
        if (this.opened && this.components != null && !this.components.isEmpty()) {
            for (Component component : this.components) {
                if (component.value != null && !component.value.shouldRender()) continue;
                component.click(n, n2, n3);
            }
        }
        return this.hovered;
    }

    public final void release(int n) {
        this.components.forEach(component -> component.release(n));
    }
}
