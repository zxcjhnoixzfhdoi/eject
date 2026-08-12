/*
 * Decompiled with CFR 0.152.
 */
package modification.extenders;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import modification.extenders.Module;
import modification.main.Modification;
import modification.managers.ValueManager;

public final class Value<V> {
    public final String name;
    public final String displayName;
    public final Module module;
    public int mode;
    public V value;
    public float min;
    public float max;
    public float dataTypeValue;
    public List<String> modes;
    public String expandMode;
    public String expandValue;
    public String expandCheck;

    public Value(String string, V v, Module module, String ... stringArray) {
        this.displayName = string;
        this.name = module.name.toLowerCase().concat("_").concat(this.displayName);
        this.value = v;
        this.module = module;
        this.mode = 0;
        if (stringArray.length > 0) {
            this.expandMode = stringArray[0];
            if (stringArray.length > 1) {
                this.expandValue = stringArray[1];
                if (stringArray.length > 2) {
                    this.expandCheck = stringArray[2];
                }
            }
        }
        ValueManager.VALUES.add(this);
    }

    public Value(String string, V v, float f, float f2, int n, Module module, String ... stringArray) {
        this(string, v, module, stringArray);
        this.min = f;
        this.max = f2;
        this.dataTypeValue = n;
        this.mode = 1;
    }

    public Value(String string2, V v, String[] stringArray, Module module, String ... stringArray2) {
        this(string2, v, module, stringArray2);
        this.modes = Arrays.asList(stringArray);
        this.modes.sort(Comparator.comparing(string -> string));
        this.mode = 2;
    }

    public final boolean shouldRender() {
        if (this.expandMode == null) {
            return true;
        }
        Value value = Modification.VALUE_MANAGER.checkValueForName(this.module.name.toLowerCase().concat("_").concat(this.expandMode));
        if (value == null) {
            return true;
        }
        if (value.mode == 0) {
            return (Boolean)value.value;
        }
        if (value.mode == 2) {
            if (this.expandValue == null) {
                return true;
            }
            if (this.expandCheck == null) {
                return value.value.equals(this.expandValue);
            }
            Value value2 = Modification.VALUE_MANAGER.checkValueForName(this.module.name.toLowerCase().concat("_").concat(this.expandCheck));
            if (value2 == null) {
                return value.value.equals(this.expandValue);
            }
            if (value2.mode == 0) {
                return (Boolean)value.value;
            }
        }
        return false;
    }
}
