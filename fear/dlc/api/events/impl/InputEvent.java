/*
 * Decompiled with https://jar.tools
 */
package fear.dlc.api.events.impl;

import fear.dlc.api.events.EventLayer;
import net.minecraft.class_10185;

public class InputEvent
extends EventLayer {
    private class_10185 input;

    public InputEvent(class_10185 input) {
        this.input = input;
    }

    public void setDirectionalLow(boolean forward, boolean backward, boolean left, boolean right) {
        this.input = new class_10185(forward, backward, left, right, this.input.comp_3163(), this.input.comp_3164(), this.input.comp_3165());
    }

    public void setJumping(boolean jump) {
        this.input = new class_10185(this.input.comp_3159(), this.input.comp_3160(), this.input.comp_3161(), this.input.comp_3162(), jump, this.input.comp_3164(), this.input.comp_3165());
    }

    public class_10185 getInput() {
        return this.input;
    }

    public void setInput(class_10185 input) {
        this.input = input;
    }
}
