/*
 * Decompiled with https://jar.tools
 */
package fear.dlc.ui.clickGui;

import com.google.common.eventbus.Subscribe;
import fear.dlc.Api;
import fear.dlc.FearDCP;
import fear.dlc.api.animations.Animation;
import fear.dlc.api.animations.Direction;
import fear.dlc.api.animations.implement.DecelerateAnimation;
import fear.dlc.api.events.impl.KeyEvent;
import fear.dlc.modules.impl.misc.SafeModeModule;
import fear.dlc.ui.clickGui.Component;
import fear.dlc.ui.clickGui.components.panel.PanelsLayer;
import fear.dlc.ui.clickGui.components.search.SearchComponent;
import fear.dlc.ui.clickGui.components.search.SearchSource;
import fear.dlc.utility.ModSounds;
import fear.dlc.utility.math.Math;
import fear.dlc.utility.windows.WindowRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;
import net.minecraft.class_2561;
import net.minecraft.class_332;
import net.minecraft.class_437;

public class ClickGuiScreen
extends class_437
implements Api {
    private final List<Component> componentsList = new ArrayList();
    private final WindowRepository windowRepository = new WindowRepository();
    private final PanelsLayer panelsLayer = new PanelsLayer();
    private final SearchComponent searchComponent = new SearchComponent();
    private final Animation animation = new DecelerateAnimation().setMs(150).setValue(1);
    private final float width = 720f;
    private final float height = 380f;
    private final Supplier<Float> x;
    private final Supplier<Float> y;

    public ClickGuiScreen() {
        super(class_2561.method_30163("pasxalka.click_gui"));
        this.width = 720f;
        this.height = 380f;
        this.x = this::lambda$new$0;
        this.y = this::lambda$new$1;
        this.componentsList.addAll(List.of(this.panelsLayer, this.searchComponent));
        FearDCP.getInstance().getEventBus().register(this);
    }

    private boolean isSafeModeActive() {
        SafeModeModule safeMode = FearDCP.getInstance().getModuleRepository().find(SafeModeModule.class);
        return safeMode != null && safeMode.getEnabled().booleanValue() && safeMode.blockClickGui.getEnabled().booleanValue();
    }

    protected void method_25426() {
        this.field_22787.field_1724.method_5783(ModSounds.OPEN_GUI, 0.4f, 1f);
        this.componentsList.forEach(Component::init);
        this.animation.setDirection(Direction.FORWARDS);
        this.animation.reset();
        super.method_25426();
    }

    public void method_25419() {
        this.animation.setDirection(Direction.BACKWARDS);
        this.animation.reset();
        this.windowRepository.close();
    }

    public boolean method_25421() {
        return false;
    }

    public void method_25394(class_332 context, int mouseX, int mouseY, float delta) {
        if (this.isSafeModeActive()) {
            mc.execute(ClickGuiScreen::lambda$render$2);
            return;
        }
        if (this.animation.isFinished(Direction.BACKWARDS)) {
            mc.execute(ClickGuiScreen::lambda$render$3);
        }
        this.panelsLayer.position(((Float)this.x.get()).floatValue(), ((Float)this.y.get()).floatValue()).size(720f, 380f);
        this.searchComponent.position(((Float)this.x.get()).floatValue() + 360f - 60f, ((Float)this.y.get()).floatValue() + 380f + 8f).size(120f, 18f);
        Math.scale(context.method_51448(), ((Float)this.x.get()).floatValue() + 360f, ((Float)this.y.get()).floatValue() + 190f, this.animation.getOutput().floatValue(), this::lambda$render$5 /* captured: context, mouseX, mouseY, delta */);
        super.method_25394(context, mouseX, mouseY, delta);
    }

    @Subscribe
    public void keyListener(KeyEvent keyEvent) {
        if (SafeModeModule.isClickGuiPermanentlyBlocked()) {
            return;
        }
        if (Objects.isNull(mc.field_1755)) {
            if (keyEvent.getKey() == 344) {
                if (this.isSafeModeActive()) {
                    return;
                }
                mc.execute(ClickGuiScreen::lambda$keyListener$6);
            }
        }
    }

    public boolean method_25402(double mouseX, double mouseY, int button) {
        if (!this.windowRepository.mouseClicked(mouseX, mouseY, button)) {
            this.componentsList.forEach(ClickGuiScreen::lambda$mouseClicked$7 /* captured: mouseX, mouseY, button */);
        }
        return super.method_25402(mouseX, mouseY, button);
    }

    public boolean method_25406(double mouseX, double mouseY, int button) {
        if (!this.windowRepository.mouseReleased(mouseX, mouseY, button)) {
            this.componentsList.forEach(ClickGuiScreen::lambda$mouseReleased$8 /* captured: mouseX, mouseY, button */);
        }
        return super.method_25406(mouseX, mouseY, button);
    }

    public boolean method_25404(int keyCode, int scanCode, int modifiers) {
        ((SearchSource)SearchComponent.getSearchSource().get()).deselect();
        if (keyCode == 256 && ((SearchSource)SearchComponent.getSearchSource().get()).isSelected()) {
            return true;
        }
        if (!this.windowRepository.keyPressed(keyCode, scanCode, modifiers)) {
            this.componentsList.forEach(ClickGuiScreen::lambda$keyPressed$9 /* captured: keyCode, scanCode, modifiers */);
        }
        return super.method_25404(keyCode, scanCode, modifiers);
    }

    public boolean method_16803(int keyCode, int scanCode, int modifiers) {
        if (!this.windowRepository.keyPressed(keyCode, scanCode, modifiers)) {
            this.componentsList.forEach(ClickGuiScreen::lambda$keyReleased$10 /* captured: keyCode, scanCode, modifiers */);
        }
        return super.method_16803(keyCode, scanCode, modifiers);
    }

    public boolean method_25400(char chr, int modifiers) {
        this.componentsList.forEach(ClickGuiScreen::lambda$charTyped$11 /* captured: chr, modifiers */);
        return super.method_25400(chr, modifiers);
    }

    public boolean method_25401(double mouseX, double mouseY, double horizontalAmount, double verticalAmount) {
        if (!this.windowRepository.mouseScrolled(mouseX, mouseY, horizontalAmount, verticalAmount)) {
            this.componentsList.forEach(ClickGuiScreen::lambda$mouseScrolled$12 /* captured: mouseX, mouseY, horizontalAmount, verticalAmount */);
        }
        return super.method_25401(mouseX, mouseY, horizontalAmount, verticalAmount);
    }

    public List<Component> getComponentsList() {
        return this.componentsList;
    }

    public PanelsLayer getPanelsLayer() {
        return this.panelsLayer;
    }

    public SearchComponent getSearchComponent() {
        return this.searchComponent;
    }

    public Animation getAnimation() {
        return this.animation;
    }

    public float getWidth() {
        Objects.requireNonNull(this);
        return 720f;
    }

    public float getHeight() {
        Objects.requireNonNull(this);
        return 380f;
    }

    public Supplier<Float> getX() {
        return this.x;
    }

    public Supplier<Float> getY() {
        return this.y;
    }

    public WindowRepository getWindowRepository() {
        return this.windowRepository;
    }
}
