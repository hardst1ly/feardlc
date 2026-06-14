/*
 * Decompiled with https://jar.tools
 */
package fear.dlc.ui.clickGui.components.search;

import fear.dlc.ui.clickGui.ClickGuiScreen;

public class SearchSource {
    private final String defaultText;
    private final Runnable runnable;
    private final StringBuilder text = new StringBuilder();
    private boolean selected = false;

    public void toggle() {
        this.selected = !this.selected;
        this.text.setLength(0);
        if (!this.selected && !this.text.isEmpty()) {
            this.runnable.run();
        }
    }

    public void deselect() {
        if (this.selected) {
            this.selected = false;
            if (!this.text.isEmpty()) {
                this.text.setLength(0);
                this.runnable.run();
            }
        }
    }

    public boolean keyPressed(int key) {
        this.toggle();
        if (key == 70 && ClickGuiScreen.method_25441()) {
            return true;
        }
        if (this.selected) {
            if (key == 256 || key == 261) {
                this.deselect();
                return true;
            }
        }
        if (!this.selected || this.text.isEmpty()) {
            return false;
        }
        if (key == 259) {
            this.text.delete(this.text.length() - 1, this.text.length());
            this.runnable.run();
            return true;
        }
        return false;
    }

    public void charTyped(char chr) {
        if (!this.selected) {
            return;
        }
        this.text.append(chr);
        this.runnable.run();
    }

    public String getDefaultText() {
        return this.defaultText;
    }

    public Runnable getRunnable() {
        return this.runnable;
    }

    public StringBuilder getText() {
        return this.text;
    }

    public boolean isSelected() {
        return this.selected;
    }

    public SearchSource(String defaultText, Runnable runnable) {
        this.defaultText = defaultText;
        this.runnable = runnable;
    }
}
