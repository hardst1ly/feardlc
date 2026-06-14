/*
 * Decompiled with https://jar.tools
 */
package fear.dlc.invoke.mixins;

import net.minecraft.class_276;
import net.minecraft.class_761;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(value={class_761.class})
public interface WorldRendererAccessor {
    @Accessor(value="entityOutlineFramebuffer")
    public class_276 getEntityOutlineFramebuffer();

    @Accessor(value="entityOutlineFramebuffer")
    public void setEntityOutlineFramebuffer(class_276 var1);
}
