/*
 * Decompiled with https://jar.tools
 */
package fear.dlc.utility.render.msdf;

import com.google.gson.annotations.SerializedName;

public static final class FontData.KerningData {
    @SerializedName(value="unicode1")
    private int leftChar;
    @SerializedName(value="unicode2")
    private int rightChar;
    private float advance;

    public int leftChar() {
        return this.leftChar;
    }

    public int rightChar() {
        return this.rightChar;
    }

    public float advance() {
        return this.advance;
    }
}
