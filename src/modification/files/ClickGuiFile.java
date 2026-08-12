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
import modification.gui.click.Panel;
import modification.main.Modification;
import modification.managers.ClickGuiManager;

public final class ClickGuiFile
extends ModFile {
    public ClickGuiFile(String string) {
        super(string);
    }

    @Override
    public void write() {
        try {
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(this.file));
            ClickGuiManager.PANELS.forEach(panel -> this.writeLine(bufferedWriter, panel.name.concat("~").concat(Float.toString(panel.x)).concat("~").concat(Float.toString(panel.y)).concat("~").concat(Boolean.toString(panel.opened))));
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
                Panel panel;
                String[] stringArray = string.split("~");
                if (stringArray.length != 4 || (panel = Modification.CLICK_GUI_MANAGER.checkPanelForName(stringArray[0])) == null) continue;
                panel.x = Float.parseFloat(stringArray[1]);
                panel.y = Float.parseFloat(stringArray[2]);
                panel.opened = Boolean.parseBoolean(stringArray[3]);
            }
            bufferedReader.close();
        }
        catch (IOException iOException) {
            Modification.LOG_UTIL.sendConsoleMessage("Error: Couldn't read file");
        }
    }
}
