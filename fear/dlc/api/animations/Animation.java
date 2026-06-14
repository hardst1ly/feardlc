/*
 * Decompiled with https://jar.tools
 */
package fear.dlc.api.animations;

import fear.dlc.api.animations.AnimationCalculation;
import fear.dlc.api.animations.Direction;
import fear.dlc.utility.math.MathTime;

public abstract class Animation
implements AnimationCalculation {
    private final MathTime counter = new MathTime();
    protected int ms;
    protected double value;
    protected Direction direction = Direction.FORWARDS;

    public void reset() {
        this.counter.resetCounter();
    }

    public boolean isDone() {
        return this.counter.isReached((long)this.ms);
    }

    public boolean isFinished(Direction direction) {
        return this.direction == direction && this.isDone();
    }

    public void setDirection(Direction direction) {
        if (this.direction != direction) {
            this.direction = direction;
            this.adjustTimer();
        }
    }

    private void adjustTimer() {
        this.counter.setTime(System.currentTimeMillis() - ((long)this.ms - Math.min((long)this.ms, this.counter.getTime())));
    }

    public Double getOutput() {
        double time = (1 - this.calculation((double)this.counter.getTime())) * this.value;
        return this.direction == Direction.FORWARDS ? this.endValue() : (this.isDone() ? 0 : time);
    }

    private double endValue() {
        return this.isDone() ? this.value : this.calculation((double)this.counter.getTime()) * this.value;
    }

    public MathTime getCounter() {
        return this.counter;
    }

    public int getMs() {
        return this.ms;
    }

    public double getValue() {
        return this.value;
    }

    public Direction getDirection() {
        return this.direction;
    }

    public Animation setMs(int ms) {
        this.ms = ms;
        return this;
    }

    public Animation setValue(double value) {
        this.value = value;
        return this;
    }
}
