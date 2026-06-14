/*
 * Decompiled with https://jar.tools
 */
package fear.dlc;

import com.google.common.eventbus.EventBus;
import fear.dlc.Api;
import fear.dlc.api.commands.CommandsListener;
import fear.dlc.api.configs.ConfigurationService;
import fear.dlc.api.draggable.data.DraggableRepository;
import fear.dlc.modules.more.FriendManager;
import fear.dlc.modules.more.ModuleRepository;
import fear.dlc.ui.clickGui.ClickGuiScreen;
import fear.dlc.utility.ModSounds;
import net.fabricmc.api.ModInitializer;

public class FearDCP
implements ModInitializer,
Api {
    static FearDCP instance;
    private EventBus eventBus = new EventBus();
    private ModuleRepository moduleRepository;
    private DraggableRepository draggableRepository;
    private ConfigurationService configurationService;
    private ClickGuiScreen clickGuiScreen;
    private CommandsListener commandsListener;

    public FearDCP() {
        instance = this;
        this.createObjects();
        this.initObjects();
        this.eventBus.register(this);
    }

    void createObjects() {
        this.moduleRepository = new ModuleRepository();
        this.draggableRepository = new DraggableRepository();
        this.clickGuiScreen = new ClickGuiScreen();
        this.configurationService = new ConfigurationService();
        this.commandsListener = new CommandsListener();
    }

    void initObjects() {
        FriendManager.init();
        this.moduleRepository.init();
        this.draggableRepository.init();
    }

    public void onInitialize() {
        ModSounds.init();
        this.configurationService.save("bot");
        this.configurationService.load("bot");
    }

    public EventBus getEventBus() {
        return this.eventBus;
    }

    public ModuleRepository getModuleRepository() {
        return this.moduleRepository;
    }

    public DraggableRepository getDraggableRepository() {
        return this.draggableRepository;
    }

    public ConfigurationService getConfigurationService() {
        return this.configurationService;
    }

    public ClickGuiScreen getClickGuiScreen() {
        return this.clickGuiScreen;
    }

    public CommandsListener getCommandsListener() {
        return this.commandsListener;
    }

    public static FearDCP getInstance() {
        return instance;
    }
}
