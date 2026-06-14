/*
 * Decompiled with https://jar.tools
 */
package fear.dlc.invoke.mixins;

import net.minecraft.class_310;
import net.minecraft.class_320;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(value={class_310.class})
public interface MinecraftClientAccessor {
    @Accessor(value="session")
    public class_320 getSession();

    @Mutable
    @Accessor(value="session")
    public void setSession(class_320 var1);
}
