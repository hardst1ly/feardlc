/*
 * Decompiled with https://jar.tools
 */
package fear.dlc.api.configs;

import fear.dlc.api.configs.Configuration;
import fear.dlc.api.configs.ConfigurationController;
import java.util.List;

public class ConfigurationService
implements Configuration {
    final ConfigurationController configurationController = new ConfigurationController();

    public void save(String name) {
        this.configurationController.save(name + ".json");
    }

    public void load(String name) {
        this.configurationController.load(name + ".json");
    }

    public void remove(String name) {
        this.configurationController.remove(name);
    }

    public List<String> asList() {
        return this.configurationController.asList();
    }
}
