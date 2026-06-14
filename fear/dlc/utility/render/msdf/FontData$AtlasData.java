/*
 * Decompiled with https://jar.tools
 */
package fear.dlc.utility.render.msdf;

import com.google.gson.annotations.SerializedName;

public static final class FontData.AtlasData {
    @SerializedName(value="distanceRange")
    private float range;
    private float width;
    private float height;

    public float range() {
        return this.range;
    }

    public float width() {
        return this.width;
    }

    public float height() {
        return this.height;
    }
}
