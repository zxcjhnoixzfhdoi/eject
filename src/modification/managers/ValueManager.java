/*
 * Decompiled with CFR 0.152.
 */
package modification.managers;

import com.google.common.collect.Lists;
import java.util.Comparator;
import java.util.List;
import modification.extenders.Value;

public final class ValueManager {
    public static final List<Value> VALUES = Lists.newArrayList();

    public final void initialize() {
        VALUES.sort(Comparator.comparing(value -> value.name));
        VALUES.sort(Comparator.comparingInt(value -> value.mode));
    }

    public final Value checkValueForName(String string) {
        if (!VALUES.isEmpty()) {
            for (Value value : VALUES) {
                if (!value.name.equals(string)) continue;
                return value;
            }
        }
        return null;
    }
}
