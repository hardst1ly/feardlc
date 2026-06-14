/*
 * Decompiled with https://jar.tools
 */
package fear.dlc.utility.render;

import net.minecraft.class_2960;
import net.minecraft.class_5912;
import net.minecraft.class_5944;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ShaderManager {
    private static final Logger LOGGER = LoggerFactory.getLogger(ShaderManager.class);
    public static final class_2960 ENTITY_OUTLINE_BOX_BLUR = class_2960.method_60655("minecraft", "shaders/post/entity_outline_box_blur.json");
    public static final class_2960 ENTITY_OUTLINE_COMPOSITE = class_2960.method_60655("minecraft", "shaders/post/entity_outline_composite.json");
    public static final class_2960 BLUR_SHADER = class_2960.method_60655("minecraft", "shaders/post/blur.vsh");
    private static class_5944 entityOutlineBoxBlurShader;
    private static class_5944 entityOutlineCompositeShader;
    private static class_5944 blurShader;
    private static boolean initialized = false;

    public static void initShaders(class_5912 resourceFactory) {
        if (initialized) {
            return;
        }
        try {
            LOGGER.info("Initializing SystemDLC shaders...");
            LOGGER.info("SystemDLC shaders initialized (stub implementation)");
            initialized = true;
        }
        catch (Exception e) {
            LOGGER.error("Failed to initialize shaders", e);
            return;
        }
    }

    public static void loadOutlineShaders(class_5912 resourceFactory) {
        try {
            LOGGER.info("Loading outline shaders for PlayerOutlineModule...");
        }
        catch (Exception e) {
            LOGGER.error("Failed to load outline shaders", e);
            return;
        }
    }

    public static class_5944 getEntityOutlineBoxBlurShader() {
        return entityOutlineBoxBlurShader;
    }

    public static class_5944 getEntityOutlineCompositeShader() {
        return entityOutlineCompositeShader;
    }

    public static class_5944 getBlurShader() {
        return blurShader;
    }

    public static void cleanup() {
        if (entityOutlineBoxBlurShader != null) {
            entityOutlineBoxBlurShader.close();
            entityOutlineBoxBlurShader = null;
        }
        if (entityOutlineCompositeShader != null) {
            entityOutlineCompositeShader.close();
            entityOutlineCompositeShader = null;
        }
        if (blurShader != null) {
            blurShader.close();
            blurShader = null;
        }
        initialized = false;
    }

    public static boolean areShadersLoaded() {
        return entityOutlineBoxBlurShader != null && entityOutlineCompositeShader != null && blurShader != null;
    }

    public static boolean isInitialized() {
        return initialized;
    }
}
