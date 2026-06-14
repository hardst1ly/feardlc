/*
 * Decompiled with https://jar.tools
 */
package fear.dlc.invoke.mixins;

import net.minecraft.class_1306;
import net.minecraft.class_4587;
import net.minecraft.class_759;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(value={class_759.class})
public interface HeldItemRendererAccessor {
    @Invoker(value="applySwingOffset")
    public void fearDlc$applySwingOffset(class_4587 var1, class_1306 var2, float var3);
}
