/*
 * Decompiled with https://jar.tools
 */
package fear.dlc.utility.inventory;

import net.minecraft.class_1294;
import net.minecraft.class_1309;
import net.minecraft.class_1657;
import net.minecraft.class_1799;
import net.minecraft.class_5134;
import net.minecraft.class_9285;
import net.minecraft.class_9334;

public class InventoryUtility {
    public static float getHitDamage(class_1799 stack, class_1309 target) {
        if (stack == null || stack.method_7960()) {
            return 1f;
        }
        float damage = 1f;
        class_9285 modifiers = stack.method_57825(class_9334.field_49636, class_9285.field_49326);
        for (class_9287 entry : modifiers.comp_2393()) {
            if (!entry.comp_2395().equals(class_5134.field_23721)) continue;
            damage = damage + (float)entry.comp_2396().comp_2449();
        }
        return damage;
    }

    public static float getCriticalDamage(class_1799 stack, class_1309 target, class_1657 attacker) {
        float baseDamage = InventoryUtility.getHitDamage(stack, target);
        boolean canCrit = !attacker.method_24828() && attacker.field_6017 > 0f && !attacker.method_5624() && !attacker.method_5765();
        if (canCrit) {
            return baseDamage * 1.5f;
        }
        return baseDamage;
    }

    public static float applyArmorAndResistance(float damage, class_1309 target) {
        float armor = target.method_45325(class_5134.field_23724);
        float armorToughness = target.method_45325(class_5134.field_23725);
        float armorReduction = Math.min(20f, Math.max(armor / 5f, armor - damage / (2f + armorToughness / 4f))) / 25f;
        damage = damage * (1f - armorReduction);
        int resistanceLevel = 0;
        if (target.method_6059(class_1294.field_5907)) {
            resistanceLevel = target.method_6112(class_1294.field_5907).method_5578() + 1;
        }
        if (resistanceLevel > 0) {
            float resistanceReduction = resistanceLevel * 0.2f;
            damage = damage * (1f - Math.min(resistanceReduction, 1f));
        }
        return Math.max(0f, damage);
    }
}
