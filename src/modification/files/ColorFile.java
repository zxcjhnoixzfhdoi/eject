/*
 * Decompiled with CFR 0.152.
 */
package modification.files;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import modification.extenders.ModFile;
import modification.main.Modification;

public final class ColorFile
extends ModFile {
    public ColorFile(String string) {
        super(string);
    }

    @Override
    public void write() {
        try {
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(this.file));
            this.writeLine(bufferedWriter, Integer.toString(Modification.color.getRed()).concat("~").concat(Integer.toString(Modification.color.getGreen())).concat("~").concat(Integer.toString(Modification.color.getBlue())));
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
                String[] stringArray = string.split("~");
                if (stringArray.length != 3) continue;
                Modification.color = new Color(Integer.parseInt(stringArray[0]), Integer.parseInt(stringArray[1]), Integer.parseInt(stringArray[2]), 255);
            }
            bufferedReader.close();
        }
        catch (IOException iOException) {
            Modification.LOG_UTIL.sendConsoleMessage("Error: Couldn't read file");
        }
    }
}
