/*
 * Decompiled with CFR 0.152.
 */
package modification.extenders;

import modification.interfaces.MCHook;

public final class Rotation
implements MCHook {
    public float yaw;
    public float pitch;
    public float lastYaw;
    public float lastPitch;

    public Rotation(float f, float f2) {
        this.yaw = f;
        this.pitch = f2;
    }

    public final void checkSensitivity() {
        float f = Rotation.MC.gameSettings.mouseSensitivity * 0.6f + 0.2f;
        float f2 = f * f * f * 8.0f;
        this.yaw -= this.yaw % f2;
        this.pitch -= this.pitch % f2;
    }
}
