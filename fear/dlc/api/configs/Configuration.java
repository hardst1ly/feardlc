/*
 * Decompiled with https://jar.tools
 */
package fear.dlc.api.configs;

import fear.dlc.Api;
import java.util.List;

public interface Configuration
extends Api {
    public void save(String var1);

    public void load(String var1);

    public void remove(String var1);

    public List<String> asList();
}
