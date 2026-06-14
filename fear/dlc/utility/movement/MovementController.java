/*
 * Decompiled with https://jar.tools
 */
package fear.dlc.utility.movement;

import fear.dlc.Api;
import fear.dlc.utility.keyboard.KeyBoardUtil;

public final class MovementController
implements Api {
    private boolean forward;
    private boolean back;
    private boolean left;
    private boolean right;
    private boolean jump;
    private boolean saved;
    private boolean blocked;

    public void block() {
        if (mc.field_1724 == null) {
            return;
        }
        mc.field_1690.field_1894.method_23481(false);
        mc.field_1690.field_1881.method_23481(false);
        mc.field_1690.field_1913.method_23481(false);
        mc.field_1690.field_1849.method_23481(false);
        mc.field_1690.field_1903.method_23481(false);
        mc.field_1690.field_1867.method_23481(false);
        this.blocked = true;
    }

    public void stopSprint() {
        if (mc.field_1724 != null) {
            mc.field_1724.method_5728(false);
            mc.field_1690.field_1867.method_23481(false);
        }
    }

    public void restoreFromCurrent() {
        mc.field_1690.field_1894.method_23481(KeyBoardUtil.isKeyPressed(mc.field_1690.field_1894));
        mc.field_1690.field_1881.method_23481(KeyBoardUtil.isKeyPressed(mc.field_1690.field_1881));
        mc.field_1690.field_1913.method_23481(KeyBoardUtil.isKeyPressed(mc.field_1690.field_1913));
        mc.field_1690.field_1849.method_23481(KeyBoardUtil.isKeyPressed(mc.field_1690.field_1849));
        mc.field_1690.field_1903.method_23481(KeyBoardUtil.isKeyPressed(mc.field_1690.field_1903));
        mc.field_1690.field_1867.method_23481(KeyBoardUtil.isKeyPressed(mc.field_1690.field_1867));
        this.blocked = false;
    }

    public boolean isBlocked() {
        return this.blocked;
    }

    public void reset() {
        this.blocked = false;
    }

    public void unblock() {
        this.blocked = false;
    }
}
