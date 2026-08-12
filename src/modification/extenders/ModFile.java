/*
 * Decompiled with CFR 0.152.
 */
package modification.extenders;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import modification.main.Modification;
import modification.managers.FileManager;

public abstract class ModFile {
    protected static final String SPLIT = "~";
    public final String name;
    public final File file;

    protected ModFile(String string) {
        this.name = string;
        this.file = new File(Modification.DIRECTORY, this.name.concat(".txt"));
        FileManager.FILES.add(this);
    }

    protected final void writeLine(BufferedWriter bufferedWriter, String string) {
        try {
            bufferedWriter.write(string);
            bufferedWriter.newLine();
        }
        catch (IOException iOException) {
            Modification.LOG_UTIL.sendConsoleMessage("Error: Couldn't write line");
        }
    }

    public abstract void write();

    public abstract void read();
}
