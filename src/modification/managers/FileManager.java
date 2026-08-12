/*
 * Decompiled with CFR 0.152.
 */
package modification.managers;

import com.google.common.collect.Lists;
import java.io.IOException;
import java.util.List;
import modification.extenders.ModFile;
import modification.files.AccountFile;
import modification.files.ClickGuiFile;
import modification.files.ColorFile;
import modification.files.ModuleFile;
import modification.files.ValueFile;
import modification.main.Modification;

public final class FileManager {
    public static final List<ModFile> FILES = Lists.newArrayList();

    public final void initialize() {
        new AccountFile("Accounts");
        new ModuleFile("Modules");
        new ValueFile("Values");
        new ColorFile("Color");
        new ClickGuiFile("ClickGui-Settings");
    }

    public final void checkFiles() {
        if (!FILES.isEmpty()) {
            FILES.forEach(modFile -> {
                if (modFile.file.exists()) {
                    modFile.read();
                    return;
                }
                try {
                    if (modFile.file.createNewFile()) {
                        Modification.LOG_UTIL.sendConsoleMessage("Created file ".concat(modFile.name).concat(" successfully"));
                    }
                }
                catch (IOException iOException) {
                    Modification.LOG_UTIL.sendConsoleMessage("Error: Couldn't create file");
                }
            });
        }
    }

    public final void update(Class<? extends ModFile> clazz) {
        if (!FILES.isEmpty()) {
            FILES.forEach(modFile -> {
                if (modFile.getClass() == clazz) {
                    modFile.write();
                }
            });
        }
    }
}
