/*
 * Decompiled with CFR 0.152.
 */
package modification.enummerates;

public enum Category {
    COMBAT,
    MISC,
    MOVEMENT,
    PLAYER,
    VISUALS,
    WORLD;

    public final String displayName = this.name().substring(0, 1).toUpperCase().concat(this.name().substring(1).toLowerCase()).replaceAll("Misc", "Miscellaneous");
}
