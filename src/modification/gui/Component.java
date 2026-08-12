/*
 * Decompiled with CFR 0.152.
 */
package modification.gui;

import modification.extenders.Value;
import modification.interfaces.MCHook;

public abstract class Component
implements MCHook {
    public final Value value;
    public float x;
    public float y;
    public int heightOffset;

    protected Component(Value value) {
        this.value = value;
        this.heightOffset = 1;
    }

    public abstract void draw(int var1, int var2);

    public abstract void click(int var1, int var2, int var3);

    public abstract void release(int var1);
}
