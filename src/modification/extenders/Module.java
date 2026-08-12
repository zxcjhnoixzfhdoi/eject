/*
 * Decompiled with CFR 0.152.
 */
package modification.extenders;

import java.util.Random;
import modification.enummerates.Category;
import modification.files.ModuleFile;
import modification.interfaces.Event;
import modification.interfaces.MCHook;
import modification.main.Modification;
import modification.managers.ModuleManager;

public abstract class Module
implements MCHook {
    protected static final Random RANDOM = new Random();
    public final String name;
    public final Category category;
    public String tag;
    public int keyCode;
    public int color;
    public boolean enabled;
    public float slide;

    protected Module(String string, Category category) {
        this.name = string;
        this.category = category;
        this.tag = "";
        this.color = RANDOM.nextInt(0xFFFFFF);
        ModuleManager.MODULES.add(this);
    }

    public final void toggle() {
        this.enabled = !this.enabled;
        Modification.FILE_MANAGER.update(ModuleFile.class);
        if (this.enabled) {
            this.onActivated();
            return;
        }
        this.onDeactivated();
    }

    protected abstract void onActivated();

    public abstract void onEvent(Event var1);

    protected abstract void onDeactivated();
}
