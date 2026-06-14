/*
 * Decompiled with https://jar.tools
 */
package fear.dlc.api.commands.list.gps;

public static class GpsRepository.WayPoint {
    private final String name;
    private final int x;
    private final int y;
    private final int z;

    public String getName() {
        return this.name;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public int getZ() {
        return this.z;
    }

    public GpsRepository.WayPoint(String name, int x, int y, int z) {
        this.name = name;
        this.x = x;
        this.y = y;
        this.z = z;
    }
}
