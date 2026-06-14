/*
 * Decompiled with https://jar.tools
 */
package fear.dlc.api.commands.list.record;

import fear.dlc.api.commands.ArgumentLayer;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class DirArgumentLayer
extends ArgumentLayer {
    public DirArgumentLayer() {
        super("dir", 0);
    }

    public void execute(List<String> arguments) {
        File recordsDir = new File(mc.field_1697, "rivalvisual/aimrecords");
        File analyticsDir = new File(recordsDir, "analytics");
        if (!analyticsDir.exists()) {
            analyticsDir.mkdirs();
        }
        try {
            Runtime.getRuntime().exec("explorer " + analyticsDir.getAbsolutePath());
            this.printSuccess("\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550");
            this.printSuccess("\u00a7a\u00a7lOpening Recordings Folder");
            this.printSuccess("\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550");
            this.printSuccess("");
            this.printSuccess("\u00a77Path: \u00a7f" + analyticsDir.getAbsolutePath());
            this.printSuccess("");
            this.printSuccess("\u00a76Folders:");
            this.printSuccess("  \u00a77analytics/ \u00a78- Shareable configs");
            this.printSuccess("  \u00a77timepoints/ \u00a78- Full datasets");
            this.printSuccess("\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550");
        }
        catch (IOException e) {
            this.printError("Failed to open folder: " + e.getMessage());
            return;
        }
    }
}
