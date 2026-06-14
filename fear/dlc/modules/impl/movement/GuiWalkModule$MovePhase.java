/*
 * Decompiled with https://jar.tools
 */
package fear.dlc.modules.impl.movement;

static enum GuiWalkModule.MovePhase {
    public static final GuiWalkModule.MovePhase READY = new GuiWalkModule.MovePhase("READY", 0);
    public static final GuiWalkModule.MovePhase ALLOW_MOVEMENT = new GuiWalkModule.MovePhase("ALLOW_MOVEMENT", 1);
    public static final GuiWalkModule.MovePhase STOPPING = new GuiWalkModule.MovePhase("STOPPING", 2);
    public static final GuiWalkModule.MovePhase WAIT_STOP = new GuiWalkModule.MovePhase("WAIT_STOP", 3);
    public static final GuiWalkModule.MovePhase SLOWING_DOWN = new GuiWalkModule.MovePhase("SLOWING_DOWN", 4);
    public static final GuiWalkModule.MovePhase SEND_PACKETS = new GuiWalkModule.MovePhase("SEND_PACKETS", 5);
    public static final GuiWalkModule.MovePhase SPEEDING_UP = new GuiWalkModule.MovePhase("SPEEDING_UP", 6);
    public static final GuiWalkModule.MovePhase RESUMING = new GuiWalkModule.MovePhase("RESUMING", 7);
    public static final GuiWalkModule.MovePhase CLOSE_INVENTORY = new GuiWalkModule.MovePhase("CLOSE_INVENTORY", 8);
    public static final GuiWalkModule.MovePhase FINISHED = new GuiWalkModule.MovePhase("FINISHED", 9);
    private static final /* synthetic */ GuiWalkModule.MovePhase[] $VALUES;

    public static GuiWalkModule.MovePhase[] values() {
        return (GuiWalkModule.MovePhase[])$VALUES.clone();
    }

    public static GuiWalkModule.MovePhase valueOf(String name) {
        return (GuiWalkModule.MovePhase)Enum.valueOf(GuiWalkModule.MovePhase.class, name);
    }

    private GuiWalkModule.MovePhase() {
        super(var1, var2);
    }

    static {
        $VALUES = GuiWalkModule.MovePhase.$values();
    }
}
