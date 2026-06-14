/*
 * Decompiled with https://jar.tools
 */
package fear.dlc.api.commands.list.record;

import fear.dlc.api.commands.ArgumentLayer;
import fear.dlc.api.commands.list.record.AimRecorder;
import java.util.List;

public class StartArgumentLayer
extends ArgumentLayer {
    public StartArgumentLayer() {
        super("start", 0);
    }

    public void execute(List<String> arguments) {
        AimRecorder recorder = AimRecorder.getInstance();
        if (recorder.isRecording()) {
            this.printError("Recording already in progress!");
            return;
        }
        recorder.startRecording();
        this.printSuccess("\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550");
        this.printSuccess("\u00a7a\u00a7l\u041d\u0435\u0439\u0440\u043e \u0437\u0430\u043f\u0438\u0441\u044c \u041d\u0430\u0447\u0430\u043b\u0430\u0441\u044c!");
        this.printSuccess("\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550");
        this.printSuccess("");
        this.printSuccess("\u00a77Target: \u00a7fFind and track players");
        this.printSuccess("\u00a77Goal: \u00a7fCollect \u00a7e30,000 samples");
        this.printSuccess("\u00a77Status: \u00a7aWaiting for target...");
        this.printSuccess("");
        this.printSuccess("\u00a76Tips:");
        this.printSuccess("  \u00a77\u2022 Move your mouse naturally");
        this.printSuccess("  \u00a77\u2022 Track players smoothly");
        this.printSuccess("  \u00a77\u2022 Recording stops at 15k samples");
        this.printSuccess("");
        this.printSuccess("\u00a7eUse \u00a7f.record stop \u00a7eto finish early");
        this.printSuccess("\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550\u2550");
    }
}
