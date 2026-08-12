/*
 * Decompiled with CFR 0.152.
 */
package modification.files;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import modification.extenders.ModFile;
import modification.extenders.Value;
import modification.main.Modification;
import modification.managers.ValueManager;

public final class ValueFile
extends ModFile {
    public ValueFile(String string) {
        super(string);
    }

    @Override
    public void write() {
        try {
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(this.file));
            ValueManager.VALUES.forEach(value -> this.writeLine(bufferedWriter, value.name.concat("~").concat(String.valueOf(value.value))));
            bufferedWriter.close();
        }
        catch (IOException iOException) {
            Modification.LOG_UTIL.sendConsoleMessage("Error: Couldn't write file");
        }
    }

    @Override
    public void read() {
        try {
            String string;
            BufferedReader bufferedReader = new BufferedReader(new FileReader(this.file));
            while ((string = bufferedReader.readLine()) != null) {
                Value value;
                String[] stringArray = string.split("~");
                if (stringArray.length != 2 || (value = Modification.VALUE_MANAGER.checkValueForName(stringArray[0])) == null) continue;
                switch (value.mode) {
                    case 0: {
                        value.value = Boolean.parseBoolean(stringArray[1]);
                        break;
                    }
                    case 1: {
                        value.value = Float.valueOf(Float.parseFloat(stringArray[1]));
                        break;
                    }
                    case 2: {
                        value.value = stringArray[1];
                    }
                }
            }
            bufferedReader.close();
        }
        catch (IOException iOException) {
            Modification.LOG_UTIL.sendConsoleMessage("Error: Couldn't read file");
        }
    }
}
