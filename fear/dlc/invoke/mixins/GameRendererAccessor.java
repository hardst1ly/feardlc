/*
 * Decompiled with https://jar.tools
 */
package fear.dlc.invoke.mixins;

import net.minecraft.class_4184;
import net.minecraft.class_757;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(value={class_757.class})
public interface GameRendererAccessor {
    @Invoker(value="getFov")
    public float invokeGetFov(class_4184 var1, float var2, boolean var3);
}
