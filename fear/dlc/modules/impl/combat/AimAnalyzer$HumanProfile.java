/*
 * Decompiled with https://jar.tools
 */
package fear.dlc.modules.impl.combat;

public static enum AimAnalyzer.HumanProfile {
    public static final AimAnalyzer.HumanProfile AGGRESSIVE = new AimAnalyzer.HumanProfile("AGGRESSIVE", 0);
    public static final AimAnalyzer.HumanProfile SMOOTH = new AimAnalyzer.HumanProfile("SMOOTH", 1);
    public static final AimAnalyzer.HumanProfile FLICKER = new AimAnalyzer.HumanProfile("FLICKER", 2);
    public static final AimAnalyzer.HumanProfile ADAPTIVE = new AimAnalyzer.HumanProfile("ADAPTIVE", 3);
    private static final /* synthetic */ AimAnalyzer.HumanProfile[] $VALUES;

    public static AimAnalyzer.HumanProfile[] values() {
        return (AimAnalyzer.HumanProfile[])$VALUES.clone();
    }

    public static AimAnalyzer.HumanProfile valueOf(String name) {
        return (AimAnalyzer.HumanProfile)Enum.valueOf(AimAnalyzer.HumanProfile.class, name);
    }

    private AimAnalyzer.HumanProfile() {
        super(var1, var2);
    }

    static {
        $VALUES = AimAnalyzer.HumanProfile.$values();
    }
}
