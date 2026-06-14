/*
 * Decompiled with https://jar.tools
 */
package fear.dlc.api.commands.list.record;

import com.google.gson.Gson;
import fear.dlc.FearDCP;
import fear.dlc.api.commands.ArgumentLayer;
import fear.dlc.modules.impl.combat.AimAnalyzer;
import fear.dlc.modules.impl.combat.AimAssistModule;
import java.io.File;
import java.io.FileReader;
import java.util.List;

public class LoadArgumentLayer
extends ArgumentLayer {
    private static final Gson GSON = new Gson();

    public LoadArgumentLayer() {
        super("load", 0);
    }

    public void execute(List<String> arguments) {
        if (arguments.isEmpty()) {
            this.printError("Specify recording name: .record load <name>");
            return;
        }
        String recordName = arguments.get(0);
        File recordsDir = new File(mc.field_1697, "rivalvisual/aimrecords");
        File analyticsDir = new File(recordsDir, "analytics");
        File analyticsFile = new File(analyticsDir, recordName + ".json");
        if (!analyticsFile.exists()) {
            this.printError("Recording '" + recordName + "' not found");
            return;
        }
        FileReader reader = new FileReader(analyticsFile);
        AnalyzedSettings analyzedSettings = GSON.fromJson(reader, AimAnalyzer.AnalyzedSettings.class);
        if (analyzedSettings == null || analyzedSettings.aimSettings == null) {
            this.printError("Failed to read settings file");
            reader.close();
            return;
        }
        AimAssistModule aimAssist = FearDCP.getInstance().getModuleRepository().find(AimAssistModule.class);
        if (aimAssist == null) {
            this.printError("AimAssist module not found");
            reader.close();
            return;
        }
        aimAssist.loadNeuroSettings(analyzedSettings);
        this.printSuccess("\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550");
        this.printSuccess("\u00a7a\u00a7lNeural Profile Loaded!");
        this.printSuccess("\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550");
        this.printSuccess("");
        this.printSuccess("\u00a7eProfile: \u00a7f" + recordName);
        this.printSuccess("\u00a7eMode: \u00a7fNeuro");
        this.printSuccess("");
        this.printSuccess("\u00a76Settings Applied:");
        Object[] tmp0 = new Object[1];
        tmp0[0] = analyzedSettings.aimSettings.maxYawSpeed;
        this.printSuccess("  \u00a77MaxYawSpeed: \u00a7f" + String.format("%.2f", tmp0));
        Object[] tmp1 = new Object[1];
        tmp1[0] = analyzedSettings.aimSettings.maxPitchSpeed;
        this.printSuccess("  \u00a77MaxPitchSpeed: \u00a7f" + String.format("%.2f", tmp1));
        Object[] tmp2 = new Object[1];
        tmp2[0] = analyzedSettings.aimSettings.smoothFactor;
        this.printSuccess("  \u00a77SmoothFactor: \u00a7f" + String.format("%.3f", tmp2));
        this.printSuccess("  \u00a77Data Points: \u00a7f" + analyzedSettings.dataPoints);
        this.printSuccess("");
        this.printSuccess("\u00a7aReady to use! Enable AimAssist to start.");
        this.printSuccess("\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550");
        reader.close();
    }
}
