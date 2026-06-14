/*
 * Decompiled with https://jar.tools
 */
package fear.dlc.ui.clickGui.components;

import fear.dlc.Api;
import fear.dlc.modules.more.Category;
import fear.dlc.ui.clickGui.Component;
import fear.dlc.utility.color.ColorUtility;
import fear.dlc.utility.render.builders.states.QuadColorState;
import fear.dlc.utility.render.builders.states.QuadRadiusState;
import fear.dlc.utility.render.builders.states.SizeState;
import fear.dlc.utility.render.renderers.impl.BuiltBlur;
import fear.dlc.utility.render.renderers.impl.BuiltBorder;
import fear.dlc.utility.render.renderers.impl.BuiltRectangle;
import fear.dlc.utility.render.renderers.impl.BuiltText;
import net.minecraft.class_332;

public class BackgroundComponent
extends Component {
    public static final float HEADER_HEIGHT = 26f;
    private static final float RADIUS = 4f;
    private final Category category;

    public BackgroundComponent render(class_332 context, int mouseX, int mouseY, float delta) {
        ((BuiltBlur)Api.blur().size(new SizeState(this.getWidth(), this.getHeight())).radius(new QuadRadiusState(4f)).blurRadius(20f).build()).render(context.method_51448().method_23760().method_23761(), this.getX(), this.getY());
        ((BuiltRectangle)Api.rectangle().size(new SizeState(this.getWidth(), this.getHeight())).radius(new QuadRadiusState(4f)).color(new QuadColorState(ColorUtility.applyOpacity(-15855596, 88))).build()).render(context.method_51448().method_23760().method_23761(), this.getX(), this.getY());
        ((BuiltRectangle)Api.rectangle().size(new SizeState(this.getWidth(), 26f)).radius(new QuadRadiusState(4f, 0f, 0f, 4f)).color(new QuadColorState(ColorUtility.applyOpacity(-15065820, 92))).build()).render(context.method_51448().method_23760().method_23761(), this.getX(), this.getY());
        ((BuiltRectangle)Api.rectangle().size(new SizeState(this.getWidth(), 0.5f)).radius(new QuadRadiusState(0f)).color(new QuadColorState(ColorUtility.applyOpacity(-1, 8))).build()).render(context.method_51448().method_23760().method_23761(), this.getX(), this.getY() + 26f);
        ((BuiltBorder)Api.border().size(new SizeState(this.getWidth(), this.getHeight())).radius(new QuadRadiusState(4f)).color(new QuadColorState(ColorUtility.applyOpacity(-1, 6))).thickness(-0.5f).build()).render(context.method_51448().method_23760().method_23761(), this.getX(), this.getY());
        String title = this.category.name();
        float fontSize = 9f;
        float titleWidth = Api.inter().getWidth(title, fontSize);
        float titleHeight = Api.inter().getHeight(title, fontSize);
        ((BuiltText)Api.text().size(fontSize).font(Api.inter()).text(title).color(ColorUtility.applyOpacity(-1, 90)).build()).render(context.method_51448().method_23760().method_23761(), this.getX() + (this.getWidth() - titleWidth) / 2f, this.getY() + (26f - titleHeight) / 2f);
        return null;
    }

    public BackgroundComponent(Category category) {
        this.category = category;
    }
}
