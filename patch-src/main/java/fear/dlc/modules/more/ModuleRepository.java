package fear.dlc.modules.more;

import com.google.common.eventbus.Subscribe;
import fear.dlc.Api;
import fear.dlc.FearDCP;
import fear.dlc.api.events.impl.KeyEvent;
import fear.dlc.api.events.impl.ModuleEvent;
import fear.dlc.modules.impl.combat.AimAssistModule;
import fear.dlc.modules.impl.combat.Aura;
import fear.dlc.modules.impl.combat.AutoTotem;
import fear.dlc.modules.impl.combat.Hitboxes;
import fear.dlc.modules.impl.combat.TriggerBotModule;
import fear.dlc.modules.impl.misc.SafeModeModule;
import fear.dlc.modules.impl.misc.SpJoinerModule;
import fear.dlc.modules.impl.movement.FreeCamModule;
import fear.dlc.modules.impl.movement.GuiWalkModule;
import fear.dlc.modules.impl.movement.SprintModule;
import fear.dlc.modules.impl.player.AutoEat;
import fear.dlc.modules.impl.player.AutoInvisible;
import fear.dlc.modules.impl.player.ChestStealer;
import fear.dlc.modules.impl.player.FakePlayer;
import fear.dlc.modules.impl.player.FastEXPModule;
import fear.dlc.modules.impl.player.MiddleClick;
import fear.dlc.modules.impl.player.NameProtect;
import fear.dlc.modules.impl.player.NoPushModule;
import fear.dlc.modules.impl.player.ServerAssistant;
import fear.dlc.modules.impl.render.Ambience;
import fear.dlc.modules.impl.render.AntiBlind;
import fear.dlc.modules.impl.render.ArrowsModule;
import fear.dlc.modules.impl.render.AspectRatio;
import fear.dlc.modules.impl.render.BlockOutlineModule;
import fear.dlc.modules.impl.render.BoxESPModule;
import fear.dlc.modules.impl.render.ESPModule;
import fear.dlc.modules.impl.render.FullBright;
import fear.dlc.modules.impl.render.HudModule;
import fear.dlc.modules.impl.render.NameTagsModule;
import fear.dlc.modules.impl.render.NoRender;
import fear.dlc.modules.impl.render.PlayerOutlineModule;
import fear.dlc.modules.impl.render.ProjectilePredictModule;
import fear.dlc.modules.impl.render.SaturationDisplayModule;
import fear.dlc.modules.impl.render.SeeInvisible;
import fear.dlc.modules.impl.render.SwingAnimationModule;
import fear.dlc.modules.impl.render.TargetEspModule;
import fear.dlc.modules.impl.render.ViewModelModule;
import fear.dlc.modules.impl.render.WorldParticles;
import fear.dlc.modules.impl.render.Zoom;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

public final class ModuleRepository implements Api {
    private final List<ModuleLayer> moduleLayers = new ArrayList<>();

    public ModuleRepository() {
        FearDCP.getInstance().getEventBus().register(this);
    }

    public void init() {
        this.moduleLayers.addAll(List.of(
                new SprintModule(),
                new ArrowsModule(),
                new NoPushModule(),
                new GuiWalkModule(),
                new TargetEspModule(),
                new HudModule(),
                new ProjectilePredictModule(),
                new NameTagsModule(),
                new AimAssistModule(),
                new Aura(),
                new AutoTotem(),
                new Hitboxes(),
                new TriggerBotModule(),
                new AutoEat(),
                new AutoInvisible(),
                new FakePlayer(),
                new ServerAssistant(),
                new MiddleClick(),
                new Ambience(),
                new AntiBlind(),
                new AspectRatio(),
                new BoxESPModule(),
                new BlockOutlineModule(),
                new ESPModule(),
                new FullBright(),
                new NoRender(),
                new SeeInvisible(),
                new Zoom(),
                new ChestStealer(),
                new SpJoinerModule(),
                new SaturationDisplayModule(),
                new SafeModeModule(),
                new FreeCamModule(),
                new PlayerOutlineModule(),
                new SwingAnimationModule(),
                new ViewModelModule(),
                new NameProtect(),
                new WorldParticles(),
                new FastEXPModule()
        ));
        this.moduleLayers.forEach(FearDCP.getInstance().getEventBus()::register);
    }

    public ModuleLayer find(Class<? extends ModuleLayer> clazz) {
        return this.moduleLayers.stream().filter(module -> module.getClass().equals(clazz)).findFirst().orElse(null);
    }

    public List<ModuleLayer> filter(Predicate<ModuleLayer> predicate) {
        return this.moduleLayers.stream().filter(predicate).toList();
    }

    public void forEach(Consumer<ModuleLayer> action) {
        this.moduleLayers.forEach(action);
    }

    @Subscribe
    private void keyEventListener(KeyEvent event) {
        this.moduleLayers.forEach(module -> {
            if (event.getKey() == module.getKey() && event.getAction() == 1 && mc.currentScreen == null) {
                module.toggleEnabled();
            }
        });
    }

    @Subscribe
    private void toggleEventListener(ModuleEvent.ToggleEvent event) {
        this.moduleLayers.forEach(module -> {
            if (event.getModuleLayer().equals(module)) {
                module.toggleEnabled();
            }
        });
    }

    public List<ModuleLayer> getModuleLayers() {
        return this.moduleLayers;
    }
}
