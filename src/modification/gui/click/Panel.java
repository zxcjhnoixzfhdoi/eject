/*
 * Decompiled with CFR 0.152.
 */
package modification.gui.click;

import com.google.common.collect.Lists;
import java.awt.Color;
import java.util.List;
import java.util.Objects;
import modification.extenders.Module;
import modification.gui.click.Element;
import modification.interfaces.MCHook;
import modification.main.Modification;
import modification.managers.ModuleManager;
import modification.modules.misc.GUI;

public final class Panel
implements MCHook {
    public final String name;
    public float x;
    public float y;
    public float height;
    private float offsetX;
    private float offsetY;
    private float width;
    private float slide;
    private float elementHeight;
    private float alpha;
    public boolean opened;
    private boolean dragging;
    private boolean hovered;
    private List<Element> elements;

    public Panel(String string, float f, float f2) {
        this.name = string;
        this.x = f;
        this.y = f2;
        this.elements = Lists.newArrayList();
        ModuleManager.MODULES.forEach(module -> {
            if (module.category.displayName.equals(string)) {
                this.elements.add(new Element((Module)module));
            }
        });
    }

    public final void draw(int n, int n2) {
        if (this.dragging) {
            this.x = this.offsetX + (float)n;
            this.y = this.offsetY + (float)n2;
        }
        GUI gUI = (GUI)Objects.requireNonNull(Modification.MODULE_MANAGER.checkModuleForName("GUI"));
        switch ((String)gUI.theme.value) {
            case "Icarus": {
                this.width = 100.0f;
                this.height = 18.0f;
                float f = this.x + (this.width - (float)Panel.MC.fontRendererObj.getStringWidth(this.name)) / 2.0f;
                Modification.RENDER_UTIL.drawBorderedRect(f - 5.0f, this.y, Panel.MC.fontRendererObj.getStringWidth(this.name) + 10, this.height, 1, this.opened ? Color.DARK_GRAY.getRGB() : Color.BLACK.getRGB(), Color.BLACK.getRGB());
                this.hovered = Modification.RENDER_UTIL.mouseHovered(n, n2, f - 5.0f, this.y, Panel.MC.fontRendererObj.getStringWidth(this.name) + 10, this.height);
                if (this.hovered) {
                    Modification.RENDER_UTIL.drawRect(f - 5.0f, this.y, Panel.MC.fontRendererObj.getStringWidth(this.name) + 10, this.height, Integer.MAX_VALUE);
                }
                Panel.MC.fontRendererObj.drawStringWithShadow(this.name, f - 0.5f, this.y + (this.height - (float)Panel.MC.fontRendererObj.FONT_HEIGHT) / 2.0f, -1);
                this.elements.forEach(element -> {
                    this.elementHeight = (float)this.elements.size() * (element.height + 3.0f);
                });
                this.slide = Modification.SLIDE_UTIL.slide(this.slide, -0.1f, this.elementHeight + 3.0f, 0.1f, this.opened);
                if (!(this.slide > 0.0f)) break;
                Modification.RENDER_UTIL.drawBorderedRect(this.x - 3.0f, this.y + this.height + 3.0f, this.width + 6.0f, this.slide, 1, Color.GRAY.getRGB(), Color.BLACK.getRGB());
                float f2 = this.y + this.height + 6.0f;
                for (Element element2 : this.elements) {
                    element2.addSettings(this.x, f2, this.width, this.height - 3.0f);
                    if (this.y + this.height + 6.0f + this.slide > f2 + this.height) {
                        element2.draw(n, n2);
                    }
                    f2 += element2.height + 3.0f;
                }
                break;
            }
            case "Abraxas": {
                this.width = 80.0f;
                this.height = 14.0f;
                this.hovered = Modification.RENDER_UTIL.mouseHovered(n, n2, this.x, this.y, this.width, this.height);
                this.alpha = Modification.SLIDE_UTIL.slide(this.alpha, 0.1f, 0.3f, 0.1f, this.hovered);
                Modification.RENDER_UTIL.drawBorderedRect(this.x, this.y, this.width, this.height, 1, new Color(1.0f, 1.0f, 1.0f, this.alpha).getRGB(), 0x11000000);
                Panel.MC.fontRendererObj.drawStringWithShadow(this.name, this.x + (this.width - (float)Panel.MC.fontRendererObj.getStringWidth(this.name)) / 2.0f - 0.5f, this.y + (this.height - (float)Panel.MC.fontRendererObj.FONT_HEIGHT) / 2.0f, -1);
                if (!this.opened) break;
                float f = this.y + this.height + 1.0f;
                for (Element element3 : this.elements) {
                    element3.addSettings(this.x, f, this.width, this.height);
                    element3.draw(n, n2);
                    f += element3.height;
                }
                break;
            }
        }
    }

    public final boolean clicked(int n, int n2, int n3) {
        if (this.hovered) {
            switch (n3) {
                case 0: {
                    this.offsetX = this.x - (float)n;
                    this.offsetY = this.y - (float)n2;
                    this.dragging = true;
                    break;
                }
                case 1: {
                    if (this.elements == null || this.elements.isEmpty()) break;
                    boolean bl = this.opened = !this.opened;
                }
            }
        }
        if (this.opened && this.elements != null && !this.elements.isEmpty()) {
            for (Element element : this.elements) {
                if (!element.clicked(n, n2, n3)) continue;
                return true;
            }
        }
        return this.hovered;
    }

    public final void release(int n) {
        if (n == 0) {
            this.dragging = false;
        }
        this.elements.forEach(element -> element.release(n));
    }
}
