/*
 * Decompiled with https://jar.tools
 */
package fear.dlc.modules.impl.render;

import fear.dlc.modules.more.Category;
import fear.dlc.modules.more.ModuleLayer;
import fear.dlc.utility.render.ShaderManager;
import net.minecraft.class_2561;

public abstract class ShaderBasedModule
extends ModuleLayer {
    protected boolean shadersInitialized = false;

    public ShaderBasedModule(class_2561 name, class_2561 description, Category category) {
        super(name, description, category);
    }

    public void activate() {
        super.activate();
        this.initializeShaders();
    }

    public void deactivate() {
        super.deactivate();
        this.cleanupShaders();
    }

    protected void initializeShaders() {
        if (!this.shadersInitialized) {
            if (!(ShaderManager.areShadersLoaded())) {
            }
            this.shadersInitialized = true;
        }
    }

    protected void cleanupShaders() {
    }

    public boolean areShadersAvailable() {
        return this.shadersInitialized && ShaderManager.areShadersLoaded();
    }

    protected String getShaderIdentifier(String shaderName) {
        return "minecraft:shaders/post/" + shaderName;
    }
}
