/*
 * Decompiled with https://jar.tools
 */
package fear.dlc.api.commands;

import fear.dlc.Api;
import fear.dlc.api.commands.ArgumentLayer;
import java.util.ArrayList;
import java.util.List;

public abstract class CommandLayer
implements Api {
    private final List<String> commands;
    private final List<ArgumentLayer> arguments = new ArrayList();

    public abstract void execute(List<String> var1);

    public List<String> getCommands() {
        return this.commands;
    }

    public List<ArgumentLayer> getArguments() {
        return this.arguments;
    }

    public CommandLayer(List<String> commands) {
        this.commands = commands;
    }
}
