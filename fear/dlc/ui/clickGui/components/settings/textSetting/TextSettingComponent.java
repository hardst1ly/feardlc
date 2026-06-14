/*
 * Decompiled with https://jar.tools
 */
package fear.dlc.ui.clickGui.components.settings.textSetting;

import fear.dlc.Api;
import fear.dlc.modules.settings.impl.TextSetting;
import fear.dlc.ui.clickGui.components.settings.SettingComponent;
import fear.dlc.utility.color.ColorUtility;
import fear.dlc.utility.math.Math;
import fear.dlc.utility.render.builders.states.QuadColorState;
import fear.dlc.utility.render.builders.states.QuadRadiusState;
import fear.dlc.utility.render.builders.states.SizeState;
import fear.dlc.utility.render.renderers.impl.BuiltBorder;
import fear.dlc.utility.render.renderers.impl.BuiltRectangle;
import fear.dlc.utility.render.renderers.impl.BuiltText;
import fear.dlc.utility.render.utility.Scissors;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.class_332;

public class TextSettingComponent
extends SettingComponent {
    private static final float NAME_FONT_SIZE = 7f;
    private static final float TEXT_FONT_SIZE = 7f;
    private static final float FIELD_PADDING = 4f;
    private static final float MIN_FIELD_HEIGHT = 12f;
    private static final float LINE_GAP = 1f;
    private long lastBlinkMs = System.currentTimeMillis();

    public TextSettingComponent(TextSetting textSetting) {
        super(textSetting);
    }

    private TextSetting setting() {
        return this.getSettingLayer();
    }

    public void init() {
        this.size(110f, this.computeHeight());
    }

    private float computeHeight() {
        TextSetting s = this.setting();
        String shown = s.getText().toString().isEmpty() && !s.isSelected() ? s.getPlaceholder() : s.getText().toString();
        float maxLineWidth = this.getWidth() <= 0f ? 102f : this.getWidth() - 8f;
        List<String> lines = this.wrap(shown, maxLineWidth);
        if (lines.isEmpty()) {
            lines.add("");
        }
        float lineHeight = Api.inter().getHeight("Wg", 7f);
        float fieldHeight = 12f + (float)(lines.size() - 1) * (lineHeight + 1f);
        float nameHeight = Api.inter().getHeight(s.getName().getString(), 7f);
        return nameHeight + 4f + fieldHeight;
    }

    public TextSettingComponent render(class_332 context, int mouseX, int mouseY, float delta) {
        this.size(this.getWidth(), this.computeHeight());
        TextSetting s = this.setting();
        ((BuiltText)Api.text().size(7f).color(ColorUtility.applyOpacity(-1, 95)).text(s.getName().getString()).font(Api.inter()).build()).render(context.method_51448().method_23760().method_23761(), this.getX(), this.getY());
        float nameHeight = Api.inter().getHeight(s.getName().getString(), 7f);
        float fieldY = this.getY() + nameHeight + 4f;
        float fieldH = this.getHeight() - nameHeight - 4f;
        ((BuiltRectangle)Api.rectangle().size(new SizeState(this.getWidth(), fieldH)).radius(new QuadRadiusState(3f)).color(new QuadColorState(ColorUtility.applyOpacity(-16777216, s.isSelected() ? 35 : 18))).build()).render(context.method_51448().method_23760().method_23761(), this.getX(), fieldY);
        ((BuiltBorder)Api.border().size(new SizeState(this.getWidth(), fieldH)).radius(new QuadRadiusState(3f)).color(new QuadColorState(ColorUtility.applyOpacity(-1, s.isSelected() ? 30 : 12))).thickness(-0.5f).build()).render(context.method_51448().method_23760().method_23761(), this.getX(), fieldY);
        String shown = s.getPlaceholder();
        if (s.getText().toString().isEmpty() && !s.isSelected()) {
            int textColor = ColorUtility.applyOpacity(-1, 35);
        } else {
            String shown = s.getText().toString();
            int textColor = ColorUtility.applyOpacity(-1, 95);
        }
        float maxLineWidth = this.getWidth() - 8f;
        List<String> lines = this.wrap(shown, maxLineWidth);
        if (lines.isEmpty()) {
            lines.add("");
        }
        float lineHeight = Api.inter().getHeight("Wg", 7f);
        Scissors.push(this.getX(), fieldY, this.getWidth(), fieldH);
        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);
            ((BuiltText)Api.text().size(7f).color(textColor).text(line).font(Api.inter()).build()).render(context.method_51448().method_23760().method_23761(), this.getX() + 4f, fieldY + 4f + (float)i * (lineHeight + 1f));
        }
        if (!s.isSelected()) return this;
        long now = System.currentTimeMillis();
        boolean show = now / 500L % 2L == 0L;
        if (show) {
            String lastLine = lines.get(lines.size() - 1);
            float lastWidth = Api.inter().getWidth(lastLine, 7f);
            float caretX = this.getX() + 4f + lastWidth + 0.5f;
            float caretY = fieldY + 4f + (float)(lines.size() - 1) * (lineHeight + 1f);
            ((BuiltRectangle)Api.rectangle().size(new SizeState(0.7f, lineHeight)).radius(new QuadRadiusState(0f)).color(new QuadColorState(ColorUtility.applyOpacity(-1, 80))).build()).render(context.method_51448().method_23760().method_23761(), caretX, caretY);
        }
        Scissors.pop();
        return this;
    }

    private List<String> wrap(String text, float maxWidth) {
        List<String> result = new ArrayList();
        if (text == null || text.isEmpty()) {
            return result;
        }
        StringBuilder current = new StringBuilder();
        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            current.append(c);
            float w = Api.inter().getWidth(current.toString(), 7f);
            if (!(w <= maxWidth)) {
                if (current.length() > 1) {
                    current.deleteCharAt(current.length() - 1);
                    result.add(current.toString());
                    current = new StringBuilder();
                    current.append(c);
                } else {
                    result.add(current.toString());
                    current = new StringBuilder();
                }
            }
        }
        if (current.length() > 0) {
            result.add(current.toString());
        }
        return result;
    }

    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        TextSetting s = this.setting();
        boolean inside = Math.isHover(mouseX, mouseY, this.getX(), this.getY(), this.getWidth(), this.getHeight());
        if (inside) {
            s.setSelected(true);
            return true;
        }
        if (s.isSelected()) {
            s.setSelected(false);
        }
        return false;
    }

    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        TextSetting s = this.setting();
        if (!s.isSelected()) {
            return false;
        }
        switch (keyCode) {
            case 259:
                s.backspace();
                break;
            case 261:
                s.clear();
                break;
            case 256:
            case 257:
            case 335:
                s.setSelected(false);
                return true;
            default:
                if (keyCode == 86 && modifiers & 2 != 0) {
                    String clip = mc.field_1774.method_1460();
                    if (clip != null) {
                        for (char c : clip.toCharArray()) {
                            if (c < ' ') continue;
                            s.append(c);
                        }
                    }
                    return true;
                }
                mc.field_1774.method_1455(s.getValue());
                if (keyCode == 67 && modifiers & 2 != 0) {
                    return true;
                }
                mc.field_1774.method_1455(s.getValue());
                if (keyCode == 88 && modifiers & 2 != 0) {
                    s.clear();
                    return true;
                }
                return false;
        }
        return true;
    }

    public boolean charTyped(char chr, int modifiers) {
        TextSetting s = this.setting();
        if (!s.isSelected()) {
            return false;
        }
        s.append(chr);
        if (chr >= ' ' && chr != '\u007f') {
            return true;
        }
        return false;
    }
}
