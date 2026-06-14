/*
 * Decompiled with https://jar.tools
 */
package fear.dlc.utility.math;

public class MathTime {
    private long lastMS = System.currentTimeMillis();

    public static MathTime create() {
        return new MathTime();
    }

    public void resetCounter() {
        this.lastMS = System.currentTimeMillis();
    }

    public boolean isReached(long time) {
        return System.currentTimeMillis() - this.lastMS > time;
    }

    public void setTime(long time) {
        this.lastMS = time;
    }

    public void addTime(long time) {
        this.lastMS = this.lastMS + time;
    }

    public long getTime() {
        return System.currentTimeMillis() - this.lastMS;
    }

    public boolean isRunning() {
        return System.currentTimeMillis() - this.lastMS <= 0L;
    }

    public boolean hasTimeElapsed() {
        return this.lastMS < System.currentTimeMillis();
    }

    public long getLastMS() {
        return this.lastMS;
    }
}
