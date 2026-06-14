/*
 * Decompiled with https://jar.tools
 */
package fear.dlc.modules.more;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import fear.dlc.Api;
import fear.dlc.FearDCP;
import fear.dlc.api.events.impl.KeyEvent;
import fear.dlc.api.events.impl.ModuleEvent;
import fear.dlc.modules.impl.combat.AimAssistModule;
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
import fear.dlc.modules.more.ModuleLayer;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Predicate;

public final class ModuleRepository
implements Api {
    List<ModuleLayer> moduleLayers = new ArrayList();

    public ModuleRepository() {
        FearDCP.getInstance().getEventBus().register(this);
    }

    public void init() {
        ModuleLayer[] tmp0 = new ModuleLayer[37];
        tmp0[0] = new SprintModule();
        tmp0[1] = new ArrowsModule();
        tmp0[2] = new NoPushModule();
        tmp0[3] = new GuiWalkModule();
        tmp0[4] = new TargetEspModule();
        tmp0[5] = new HudModule();
        tmp0[6] = new ProjectilePredictModule();
        tmp0[7] = new NameTagsModule();
        tmp0[8] = new AimAssistModule();
        tmp0[9] = new AutoTotem();
        tmp0[10] = new Hitboxes();
        tmp0[11] = new TriggerBotModule();
        tmp0[12] = new AutoEat();
        tmp0[13] = new AutoInvisible();
        tmp0[14] = new FakePlayer();
        tmp0[15] = new ServerAssistant();
        tmp0[16] = new Ambience();
        tmp0[17] = new AntiBlind();
        tmp0[18] = new AspectRatio();
        tmp0[19] = new BoxESPModule();
        tmp0[20] = new BlockOutlineModule();
        tmp0[21] = new ESPModule();
        tmp0[22] = new FullBright();
        tmp0[23] = new NoRender();
        tmp0[24] = new SeeInvisible();
        tmp0[25] = new Zoom();
        tmp0[26] = new ChestStealer();
        tmp0[27] = new SpJoinerModule();
        tmp0[28] = new SaturationDisplayModule();
        tmp0[29] = new SafeModeModule();
        tmp0[30] = new FreeCamModule();
        tmp0[31] = new PlayerOutlineModule();
        tmp0[32] = new SwingAnimationModule();
        tmp0[33] = new ViewModelModule();
        tmp0[34] = new NameProtect();
        tmp0[35] = new WorldParticles();
        tmp0[36] = new FastEXPModule();
        this.moduleLayers.addAll(List.of(tmp0));
        EventBus tmp1 = FearDCP.getInstance().getEventBus();
        Objects.requireNonNull(tmp1);
        this.moduleLayers.forEach(tmp1::register);
    }

    public ModuleLayer find(Class<? extends ModuleLayer> clazz) {
        return this.moduleLayers.stream().filter(ModuleRepository::lambda$find$0 /* captured: clazz */).findFirst().orElse(null);
    }

    public List<ModuleLayer> filter(Predicate<ModuleLayer> predicate) {
        return this.moduleLayers.stream().filter(predicate).toList();
    }

    public void forEach(Consumer<ModuleLayer> action) {
        this.moduleLayers.forEach(action);
    }

    @Subscribe
    private void keyEventListener(KeyEvent keyEvent) {
        this.moduleLayers.forEach(ModuleRepository::lambda$keyEventListener$1 /* captured: keyEvent */);
    }

    @Subscribe
    private void toggleEventListener(ModuleEvent.ToggleEvent toggleEvent) {
        this.moduleLayers.forEach(ModuleRepository::lambda$toggleEventListener$2 /* captured: toggleEvent */);
    }

    public List<ModuleLayer> getModuleLayers() {
        return this.moduleLayers;
    }
}
