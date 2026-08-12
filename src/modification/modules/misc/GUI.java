/*
 * Decompiled with CFR 0.152.
 */
package modification.modules.misc;

import modification.enummerates.Category;
import modification.extenders.Module;
import modification.extenders.Value;
import modification.interfaces.Event;
import modification.main.Modification;
import net.minecraft.client.gui.GuiScreen;

public final class GUI
extends Module {
    public final Value<String> theme = new Value<String>("Theme", "Eject", new String[]{"Eject", "Icarus", "Abraxas", "Xanax"}, this, new String[0]);

    public GUI(String string, Category category) {
        super(string, category);
    }

    @Override
    protected void onActivated() {
        switch ((String)this.theme.value) {
            case "Eject": 
            case "Xanax": {
                MC.displayGuiScreen((GuiScreen)Modification.CSGO_GUI_MANAGER);
                break;
            }
            case "Icarus": 
            case "Abraxas": {
                MC.displayGuiScreen((GuiScreen)Modification.CLICK_GUI_MANAGER);
            }
        }
        this.toggle();
    }

    @Override
    public void onEvent(Event event) {
    }

    @Override
    protected void onDeactivated() {
    }
}
