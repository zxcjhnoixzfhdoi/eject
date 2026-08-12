/*
 * Decompiled with CFR 0.152.
 */
package modification.utilities;

import net.minecraft.util.MathHelper;

public final class SlideUtil {
    public final float slide(float f, float f2, float f3, float f4, boolean bl) {
        return MathHelper.clamp_float((float)(bl ? (f < f3 ? f + (f3 - f) * f4 : f) : (f > f2 ? f - (f - f2) * f4 : f)), (float)f2, (float)f3);
    }

    public final float slideOther(float f, float f2, float f3, float f4, float f5) {
        return MathHelper.clamp_float((float)(f < f2 ? f + (f2 - f) * f5 : (f > f2 ? f - (f - f2) * f5 : f)), (float)f3, (float)f4);
    }

    public final float slideNormal(float f, float f2, float f3, float f4, boolean bl) {
        return MathHelper.clamp_float((float)(bl ? (f < f3 ? f + f4 : f) : (f > f2 ? f - f4 : f)), (float)f2, (float)f3);
    }
}
