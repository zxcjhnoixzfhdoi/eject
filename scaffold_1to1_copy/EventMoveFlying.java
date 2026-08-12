/*
 * Decompiled with CFR 0.152.
 */
package modification.events;

import modification.interfaces.Event;

public final class EventMoveFlying
implements Event {
    public float strafe;
    public float forward;
    public float friction;
    public float yaw;
    public boolean canceled;

    public EventMoveFlying(float f, float f2, float f3, float f4) {
        this.strafe = f;
        this.forward = f2;
        this.friction = f3;
        this.yaw = f4;
    }
}
