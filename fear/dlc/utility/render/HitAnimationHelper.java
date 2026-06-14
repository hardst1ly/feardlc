/*
 * Decompiled with https://jar.tools
 */
package fear.dlc.utility.render;

public class HitAnimationHelper {
    private long startTime;
    private boolean active;
    private final long animationDuration;
    private final float fadeStartPercent;

    public HitAnimationHelper(long animationDuration, float fadeStartPercent) {
        this.animationDuration = animationDuration;
        this.fadeStartPercent = fadeStartPercent;
        this.active = false;
    }

    public void trigger() {
        this.startTime = System.currentTimeMillis();
        this.active = true;
    }

    public void update() {
        if (!this.active) {
            return;
        }
        long elapsed = System.currentTimeMillis() - this.startTime;
        if (elapsed >= this.animationDuration) {
            this.active = false;
        }
    }

    public float getProgress() {
        if (!this.active) {
            return 0f;
        }
        long elapsed = System.currentTimeMillis() - this.startTime;
        return Math.min(1f, (float)elapsed / (float)this.animationDuration);
    }

    public float getIntensity() {
        if (!this.active) {
            return 0f;
        }
        float progress = this.getProgress();
        if (progress < this.fadeStartPercent / 100f) {
            return progress / (this.fadeStartPercent / 100f);
        }
        float fadeProgress = (progress - this.fadeStartPercent / 100f) / (1f - this.fadeStartPercent / 100f);
        return 1f - fadeProgress;
    }

    public boolean isActive() {
        return this.active;
    }

    public void reset() {
        this.active = false;
    }
}
