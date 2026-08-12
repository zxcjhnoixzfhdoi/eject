/*
 * Decompiled with CFR 0.152.
 */
package modification.utilities;

import net.minecraft.client.gui.ScaledResolution;
import org.lwjgl.opengl.GL11;

public final class ScissorUtil {
    public final void begin() {
        GL11.glEnable((int)3089);
    }

    public final void scissor(ScaledResolution scaledResolution, float f, float f2, float f3, float f4) {
        int n = scaledResolution.getScaleFactor();
        GL11.glScissor((int)((int)(f * (float)n)), (int)((int)(((float)scaledResolution.getScaledHeight() - (f2 + f4)) * (float)n)), (int)((int)(f3 * (float)n)), (int)((int)(f4 * (float)n)));
    }

    public final void end() {
        GL11.glDisable((int)3089);
    }
}
