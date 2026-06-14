/*
 * Decompiled with https://jar.tools
 */
package fear.dlc.api.events;

public class EventLayer {
    protected boolean canceled = false;

    public void cancel() {
        this.canceled = true;
    }

    public boolean isCanceled() {
        return this.canceled;
    }
}
