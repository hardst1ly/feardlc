/*
 * Decompiled with https://jar.tools
 */
package fear.dlc.api.events.impl;

import fear.dlc.api.events.EventLayer;

public class TextFactoryEvent
extends EventLayer {
    private String text;

    public void replaceText(String protect, String replaced) {
        if (this.text == null || this.text.isEmpty() || protect == null || replaced == null) {
            return;
        }
        if (this.text.contains(protect)) {
            if (this.text.equalsIgnoreCase(protect) || this.text.contains(protect + " ") || this.text.contains(" " + protect) || this.text.contains("\u23cf" + protect) || this.text.contains(protect + "\u00a7")) {
                this.text = this.text.replace(protect, replaced);
            }
        }
    }

    public String getText() {
        return this.text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public TextFactoryEvent(String text) {
        this.text = text;
    }
}
