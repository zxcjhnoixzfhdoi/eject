/*
 * Decompiled with CFR 0.152.
 */
package modification.events;

import modification.interfaces.Event;
import net.minecraft.client.gui.ScaledResolution;

public final class EventRender2D
implements Event {
    public final ScaledResolution resolution;

    public EventRender2D(ScaledResolution scaledResolution) {
        this.resolution = scaledResolution;
    }
}
