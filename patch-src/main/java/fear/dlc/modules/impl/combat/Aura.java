package fear.dlc.modules.impl.combat;

import com.google.common.eventbus.Subscribe;
import fear.dlc.api.events.impl.TickEvent;
import fear.dlc.modules.more.Category;
import fear.dlc.modules.more.FriendManager;
import fear.dlc.modules.more.ModuleLayer;
import fear.dlc.modules.settings.impl.BooleanSetting;
import fear.dlc.modules.settings.impl.SliderSetting;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

public class Aura extends ModuleLayer {
    public final SliderSetting attackDistance;
    public final BooleanSetting targetPlayers;
    public final BooleanSetting targetMobs;
    public final BooleanSetting targetAnimals;
    public final BooleanSetting targetInvisible;
    public final BooleanSetting throughWalls;
    public final BooleanSetting rotate;
    public final BooleanSetting onlyCriticals;
    public final BooleanSetting pauseWhileUsing;

    public Aura() {
        super(Text.literal("Aura"), Text.literal("Автоматически атакует ближайшую подходящую цель."), Category.Combat);
        this.attackDistance = new SliderSetting(
                Text.literal("Дистанция"),
                Text.literal("Максимальная дистанция атаки"),
                () -> true
        ).set(2.0f, 6.0f, 0.1f).set(3.2f).register(this);
        this.targetPlayers = new BooleanSetting(Text.literal("Игроки"), null, () -> true).set(true).register(this);
        this.targetMobs = new BooleanSetting(Text.literal("Мобы"), null, () -> true).set(true).register(this);
        this.targetAnimals = new BooleanSetting(Text.literal("Животные"), null, () -> true).set(true).register(this);
        this.targetInvisible = new BooleanSetting(Text.literal("Невидимые"), null, () -> true).set(false).register(this);
        this.throughWalls = new BooleanSetting(Text.literal("Сквозь стены"), null, () -> true).set(false).register(this);
        this.rotate = new BooleanSetting(Text.literal("Поворачивать камеру"), null, () -> true).set(true).register(this);
        this.onlyCriticals = new BooleanSetting(Text.literal("Только криты"), null, () -> true).set(false).register(this);
        this.pauseWhileUsing = new BooleanSetting(Text.literal("Пауза при использовании"), null, () -> true).set(true).register(this);
    }

    @Subscribe
    private void onTick(TickEvent event) {
        if (!this.getEnabled() || mc.player == null || mc.world == null || mc.interactionManager == null) {
            return;
        }
        if (mc.currentScreen != null || (this.pauseWhileUsing.getEnabled() && mc.player.isUsingItem())) {
            return;
        }
        if (mc.player.getAttackCooldownProgress(0.5f) < 0.92f) {
            return;
        }
        if (this.onlyCriticals.getEnabled()
                && (mc.player.isOnGround() || mc.player.fallDistance <= 0.0f || mc.player.isTouchingWater())) {
            return;
        }

        LivingEntity target = this.findTarget();
        if (target == null) {
            return;
        }
        if (this.rotate.getEnabled()) {
            this.lookAt(target);
        }

        mc.interactionManager.attackEntity(mc.player, target);
        mc.player.swingHand(Hand.MAIN_HAND);
    }

    private LivingEntity findTarget() {
        LivingEntity best = null;
        double bestDistance = this.attackDistance.getValue() * this.attackDistance.getValue();

        for (Entity entity : mc.world.getEntities()) {
            if (!(entity instanceof LivingEntity living) || !this.isValidTarget(living)) {
                continue;
            }
            double distance = mc.player.squaredDistanceTo(living);
            if (distance <= bestDistance) {
                best = living;
                bestDistance = distance;
            }
        }
        return best;
    }

    private boolean isValidTarget(LivingEntity target) {
        if (target == mc.player || !target.isAlive() || target.isSpectator()) {
            return false;
        }
        if (!this.targetInvisible.getEnabled() && target.isInvisible()) {
            return false;
        }
        if (!this.throughWalls.getEnabled() && !mc.player.canSee(target)) {
            return false;
        }
        if (target instanceof PlayerEntity player) {
            return this.targetPlayers.getEnabled() && !FriendManager.isFriend(player.getName().getString());
        }
        if (target instanceof HostileEntity) {
            return this.targetMobs.getEnabled();
        }
        return target instanceof AnimalEntity && this.targetAnimals.getEnabled();
    }

    private void lookAt(LivingEntity target) {
        Vec3d eyes = mc.player.getEyePos();
        Vec3d point = target.getBoundingBox().getCenter();
        double x = point.x - eyes.x;
        double y = point.y - eyes.y;
        double z = point.z - eyes.z;
        double horizontal = Math.sqrt(x * x + z * z);
        float yaw = (float) Math.toDegrees(Math.atan2(z, x)) - 90.0f;
        float pitch = (float) -Math.toDegrees(Math.atan2(y, horizontal));
        mc.player.setYaw(mc.player.getYaw() + MathHelper.wrapDegrees(yaw - mc.player.getYaw()));
        mc.player.setPitch(MathHelper.clamp(pitch, -90.0f, 90.0f));
    }
}
