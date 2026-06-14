/*
 * Decompiled with https://jar.tools
 */
package fear.dlc.api.animations;

public enum Direction {
    public static final Direction FORWARDS = new Direction("FORWARDS", 0);
    public static final Direction BACKWARDS = new Direction("BACKWARDS", 1);
    private static final /* synthetic */ Direction[] $VALUES;

    public static Direction[] values() {
        return $VALUES.clone();
    }

    public static Direction valueOf(String name) {
        return Enum.valueOf(Direction.class, name);
    }

    private Direction() {
        super(var1, var2);
    }

    static {
        $VALUES = Direction.$values();
    }
}
