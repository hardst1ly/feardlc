/*
 * Decompiled with https://jar.tools
 */
package fear.dlc.utility.render.builders;

import com.google.common.base.Suppliers;
import fear.dlc.utility.render.builders.impl.BlurBuilder;
import fear.dlc.utility.render.builders.impl.BorderBuilder;
import fear.dlc.utility.render.builders.impl.RectangleBuilder;
import fear.dlc.utility.render.builders.impl.ShadowBuilder;
import fear.dlc.utility.render.builders.impl.TextBuilder;
import fear.dlc.utility.render.builders.impl.TextureBuilder;
import fear.dlc.utility.render.msdf.MsdfFont;
import java.util.function.Supplier;

public final class Builder {
    public static final RectangleBuilder RECTANGLE_BUILDER = new RectangleBuilder();
    public static final BorderBuilder BORDER_BUILDER = new BorderBuilder();
    public static final TextureBuilder TEXTURE_BUILDER = new TextureBuilder();
    public static final ShadowBuilder SHADOW_BUILDER = new ShadowBuilder();
    public static final TextBuilder TEXT_BUILDER = new TextBuilder();
    public static final BlurBuilder BLUR_BUILDER = new BlurBuilder();
    public static final Supplier<MsdfFont> INTER = Suppliers.memoize(Builder::lambda$static$0);
    public static final Supplier<MsdfFont> ICONS = Suppliers.memoize(Builder::lambda$static$1);
    public static final Supplier<MsdfFont> HUD_ICONS = Suppliers.memoize(Builder::lambda$static$2);
}
