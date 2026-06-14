/*
 * Decompiled with https://jar.tools
 */
package fear.dlc.api.commands.list.record;

import fear.dlc.api.commands.ArgumentLayer;
import java.io.File;
import java.util.List;

public class ListArgumentLayer
extends ArgumentLayer {
    public ListArgumentLayer() {
        super("list", 0);
    }

    public void execute(List<String> arguments) {
        File recordsDir = new File(mc.field_1697, "rivalvisual/aimrecords");
        File analyticsDir = new File(recordsDir, "analytics");
        if (!analyticsDir.exists()) {
            this.printError("No recordings found. Use .record start to create one!");
            return;
        }
        File[] files = analyticsDir.listFiles(ListArgumentLayer::lambda$execute$0);
        if (files == null || files.length == 0) {
            this.printError("No recordings found. Use .record start to create one!");
            return;
        }
        this.printSuccess("\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550");
        this.printSuccess("\u00a7a\u00a7lNeural Recordings");
        this.printSuccess("\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550");
        this.printSuccess("");
        for (File file : files) {
            String fileName = file.getName();
            String recordName = fileName.replace(".json", "");
            this.printSuccess("  \u00a7e" + recordName + " \u00a77(\u00a7f" + file.length() / 1024L + "KB\u00a77)");
        }
        this.printSuccess("");
        this.printSuccess("\u00a77Use \u00a7f.record load <name> \u00a77to apply");
        this.printSuccess("\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550");
    }
}
