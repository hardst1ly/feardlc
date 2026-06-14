/*
 * Decompiled with https://jar.tools
 */
package fear.dlc.ui.clickGui.components.search;

import com.google.common.base.Suppliers;
import fear.dlc.Api;
import fear.dlc.ui.clickGui.Component;
import fear.dlc.ui.clickGui.components.search.SearchSource;
import fear.dlc.utility.color.ColorUtility;
import fear.dlc.utility.math.Math;
import fear.dlc.utility.render.builders.states.QuadColorState;
import fear.dlc.utility.render.builders.states.QuadRadiusState;
import fear.dlc.utility.render.builders.states.SizeState;
import fear.dlc.utility.render.renderers.impl.BuiltBlur;
import fear.dlc.utility.render.renderers.impl.BuiltBorder;
import fear.dlc.utility.render.renderers.impl.BuiltRectangle;
import fear.dlc.utility.render.renderers.impl.BuiltText;
import fear.dlc.utility.render.utility.Scissors;
import java.util.function.Supplier;
import net.minecraft.class_332;

public class SearchComponent
extends Component {
    static Supplier<SearchSource> searchSource = Suppliers.memoize(SearchComponent::lambda$static$0);

    public SearchComponent render(class_332 context, int mouseX, int mouseY, float delta) {
        ((BuiltBlur)Api.blur().size(new SizeState(this.getWidth(), this.getHeight())).radius(new QuadRadiusState(5f)).blurRadius(16f).build()).render(context.method_51448().method_23760().method_23761(), this.getX(), this.getY());
        ((BuiltRectangle)Api.rectangle().radius(new QuadRadiusState(5f)).color(new QuadColorState(ColorUtility.applyOpacity(-16777216, ((SearchSource)searchSource.get()).isSelected() ? 40 : 20))).size(new SizeState(this.getWidth(), this.getHeight())).build()).render(context.method_51448().method_23760().method_23761(), this.getX(), this.getY());
        ((BuiltBorder)Api.border().radius(new QuadRadiusState(5f)).color(new QuadColorState(ColorUtility.applyOpacity(-1, ((SearchSource)searchSource.get()).isSelected() ? 30 : 10))).thickness(-0.5f).size(new SizeState(this.getWidth(), this.getHeight())).build()).render(context.method_51448().method_23760().method_23761(), this.getX(), this.getY());
        String text = ((SearchSource)searchSource.get()).isSelected() && !((SearchSource)searchSource.get()).getText().isEmpty() ? ((SearchSource)searchSource.get()).getText().toString() : ((SearchSource)searchSource.get()).getDefaultText();
        Scissors.push(this.getX(), this.getY(), this.getWidth(), this.getHeight());
        ((BuiltText)Api.text().color(-1).font(Api.inter()).text(text).size(8f).build()).render(context.method_51448().method_23760().method_23761(), this.getX() + 5f, this.getY() + this.getHeight() / 4f);
        Scissors.pop();
        return this;
    }

    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (Math.isHover(mouseX, mouseY, this.getX(), this.getY(), this.getWidth(), this.getHeight())) {
            ((SearchSource)searchSource.get()).toggle();
        } else {
            ((SearchSource)searchSource.get()).deselect();
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }

    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (((SearchSource)searchSource.get()).keyPressed(keyCode)) {
            return true;
        }
        return super.keyPressed(keyCode, scanCode, modifiers);
    }

    public boolean charTyped(char chr, int modifiers) {
        ((SearchSource)searchSource.get()).charTyped(chr);
        return super.charTyped(chr, modifiers);
    }

    public static Supplier<SearchSource> getSearchSource() {
        return searchSource;
    }
}
