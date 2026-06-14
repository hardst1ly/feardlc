package fear.dlc.modules.impl.render;

import fear.dlc.modules.more.Category;
import fear.dlc.modules.more.ModuleLayer;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.text.Text;

public class FullBright extends ModuleLayer {
    private boolean applied;

    public FullBright() {
        super(Text.literal("FullBright"), Text.literal("Позволяет видеть в темноте."), Category.Render);
    }

    @Override
    public void activate() {
        this.updateEffect();
    }

    @Override
    public void deactivate() {
        this.removeNightVision();
    }

    public void updateEffect() {
        if (!this.getEnabled() || mc.player == null) {
            return;
        }

        StatusEffectInstance current = mc.player.getStatusEffect(StatusEffects.NIGHT_VISION);
        if (current == null || current.getDuration() < 220) {
            mc.player.addStatusEffect(new StatusEffectInstance(
                    StatusEffects.NIGHT_VISION,
                    400,
                    0,
                    false,
                    false,
                    false
            ));
            this.applied = true;
        }
    }

    private void removeNightVision() {
        if (this.applied && mc.player != null) {
            mc.player.removeStatusEffect(StatusEffects.NIGHT_VISION);
        }
        this.applied = false;
    }
}
