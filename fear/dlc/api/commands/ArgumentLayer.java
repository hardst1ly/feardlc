/*
 * Decompiled with https://jar.tools
 */
package fear.dlc.api.commands;

import fear.dlc.Api;
import java.util.ArrayList;
import java.util.List;

public abstract class ArgumentLayer
implements Api {
    private final String argument;
    private final Integer index;
    private final List<ArgumentLayer> childs = new ArrayList();

    public abstract void execute(List<String> var1);

    public String getArgument() {
        return this.argument;
    }

    public Integer getIndex() {
        return this.index;
    }

    public List<ArgumentLayer> getChilds() {
        return this.childs;
    }

    public ArgumentLayer(String argument, Integer index) {
        this.argument = argument;
        this.index = index;
    }
}
