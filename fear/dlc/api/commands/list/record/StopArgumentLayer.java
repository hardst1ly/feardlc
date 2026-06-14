/*
 * Decompiled with https://jar.tools
 */
package fear.dlc.api.commands.list.record;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import fear.dlc.api.commands.ArgumentLayer;
import fear.dlc.api.commands.list.record.AimRecorder;
import fear.dlc.modules.impl.combat.AimAnalyzer;
import java.io.File;
import java.io.FileWriter;
import java.util.List;
import net.minecraft.class_2561;
import net.minecraft.class_2583;

public class StopArgumentLayer
extends ArgumentLayer {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    public StopArgumentLayer() {
        super("stop", 0);
    }

    public void execute(List<String> arguments) {
        AimRecorder recorder = AimRecorder.getInstance();
        if (!recorder.isRecording()) {
            this.printError("\u0417\u0430\u043f\u0438\u0441\u044c \u043d\u0435 \u0438\u0434\u0435\u0442!");
            return;
        }
        recorder.stopRecording();
        List<AimRecorder.AimDataPoint> data = recorder.getRecordedData();
        int totalSamples = recorder.getCurrentSamples();
        if (data.isEmpty()) {
            this.printError("\u041d\u0435\u0442 \u0434\u0430\u043d\u043d\u044b\u0445 \u0434\u043b\u044f \u0441\u043e\u0445\u0440\u0430\u043d\u0435\u043d\u0438\u044f");
            return;
        }
        AnalyzedSettings settings = AimAnalyzer.analyze(data);
        File recordsDir = new File(mc.field_1697, "rivalvisual/aimrecords");
        File analyticsDir = new File(recordsDir, "analytics");
        File timepointsDir = new File(recordsDir, "timepoints");
        if (!analyticsDir.exists()) {
            analyticsDir.mkdirs();
        }
        if (!timepointsDir.exists()) {
            timepointsDir.mkdirs();
        }
        int recordNumber = 1;
        File timepointsFile = new File(timepointsDir, "record" + recordNumber + ".json");
        File analyticsFile = new File(analyticsDir, "record" + recordNumber + ".json");
        recordNumber++;
        if (!(timepointsFile.exists()) && !(analyticsFile.exists())) {
            recordNumber--;
        }
        FileWriter writer = new FileWriter(timepointsFile);
        RecordData recordData = new RecordData(data, settings);
        GSON.toJson(recordData, writer);
        writer.close();
        FileWriter writer = new FileWriter(analyticsFile);
        GSON.toJson(settings, writer);
        this.printSuccess("\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550");
        this.printSuccess("\u00a7a\u00a7l\u041d\u0435\u0439\u0440\u043e \u0437\u0430\u043f\u0438\u0441\u044c Complete!");
        this.printSuccess("\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550");
        this.printSuccess("");
        this.printSuccess("\u00a7e\u0410\u0439\u0434\u0438 \u0437\u0430\u043f\u0438\u0441\u0438: \u00a7frecord" + recordNumber);
        this.printSuccess("\u00a7e\u0421\u044d\u043c\u043f\u043b\u043e\u0432 \u0441\u043e\u0431\u0440\u0430\u043d\u043e: \u00a7f" + totalSamples + " / " + recorder.getMaxSamples());
        this.printSuccess("\u00a7eData Points: \u00a7f" + data.size());
        this.printSuccess("");
        this.printSuccess("\u00a76\u0424\u0430\u0439\u043b \u0441\u043e\u0445\u0440\u0430\u043d\u0435\u043d:");
        this.printSuccess("  \u00a77analytics/record" + recordNumber + ".json \u00a78(shareable config)");
        this.printSuccess("  \u00a77timepoints/record" + recordNumber + ".json \u00a78(full dataset)");
        this.printSuccess("");
        this.printSuccess("\u00a7a\u0418\u0441\u043f\u043e\u043b\u044c\u0437\u0443\u0439 \u00a7f.record load record" + recordNumber + " \u00a7a\u0427\u0442\u043e\u0431\u044b \u043f\u0440\u0438\u043c\u0435\u043d\u0438\u0442\u044c \u043d\u0435\u0439\u0440\u043e \u0440\u043e\u0442\u0430\u0446\u0438\u044e!");
        this.printSuccess("\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550");
        this.printSuccess("");
        mc.field_1705.method_1743().method_1812(class_2561.method_43470("\u00a77Neural Analysis:\n" + settings.toString()).method_10862(class_2583.field_24360.method_36139(-5592406)));
        writer.close();
        recorder.clearData();
    }
}
