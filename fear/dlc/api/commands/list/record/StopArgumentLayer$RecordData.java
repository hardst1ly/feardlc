/*
 * Decompiled with https://jar.tools
 */
package fear.dlc.api.commands.list.record;

import fear.dlc.api.commands.list.record.AimRecorder;
import fear.dlc.modules.impl.combat.AimAnalyzer;
import java.util.List;

private static class StopArgumentLayer.RecordData {
    public final List<AimRecorder.AimDataPoint> rawData;
    public final AimAnalyzer.AnalyzedSettings analysis;

    public StopArgumentLayer.RecordData(List<AimRecorder.AimDataPoint> rawData, AimAnalyzer.AnalyzedSettings analysis) {
        this.rawData = rawData;
        this.analysis = analysis;
    }
}
