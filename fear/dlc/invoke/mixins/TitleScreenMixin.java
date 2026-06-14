/*
 * Decompiled with https://jar.tools
 */
package fear.dlc.invoke.mixins;

import fear.dlc.ui.menu.CustomMenuButton;
import fear.dlc.ui.menu.MenuShaderBackground;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.class_2561;
import net.minecraft.class_310;
import net.minecraft.class_332;
import net.minecraft.class_437;
import net.minecraft.class_442;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value={class_442.class})
public abstract class TitleScreenMixin
extends class_437 {
    @Unique
    private final List<CustomMenuButton> customButtons = new ArrayList();
    @Unique
    private boolean customButtonsInitialized = false;

    protected TitleScreenMixin(class_2561 title) {
        super(title);
    }

    @Inject(method={"render"}, at={@At(value="HEAD")}, cancellable=true)
    private void replaceRender(class_332 context, int mouseX, int mouseY, float delta, CallbackInfo ci) {
        MenuShaderBackground.render(context.method_51448(), context.method_51421(), context.method_51443(), delta);
        for (CustomMenuButton button : this.customButtons) {
            button.render(context, mouseX, mouseY, delta);
        }
        ci.cancel();
    }

    @Inject(method={"init"}, at={@At(value="RETURN")})
    private void initCustomButtons(CallbackInfo ci) {
        this.customButtons.clear();
        class_310 mc = class_310.method_1551();
        int centerX = this.field_22789 / 2;
        int buttonWidth = 150;
        int buttonHeight = 25;
        int spacing = 8;
        int totalHeight = buttonHeight * 4 + spacing * 3;
        int startY = (this.field_22790 - totalHeight) / 2;
        CustomMenuButton singleplayerButton = new CustomMenuButton("Singleplayer", TitleScreenMixin::lambda$initCustomButtons$0 /* captured: mc */);
        singleplayerButton.setPosition((float)centerX - (float)buttonWidth / 2f, (float)startY);
        singleplayerButton.setSize((float)buttonWidth, (float)buttonHeight);
        this.customButtons.add(singleplayerButton);
        CustomMenuButton multiplayerButton = new CustomMenuButton("Multiplayer", TitleScreenMixin::lambda$initCustomButtons$1 /* captured: mc */);
        multiplayerButton.setPosition((float)centerX - (float)buttonWidth / 2f, (float)(startY + buttonHeight + spacing));
        multiplayerButton.setSize((float)buttonWidth, (float)buttonHeight);
        this.customButtons.add(multiplayerButton);
        CustomMenuButton accountsButton = new CustomMenuButton("Accounts", TitleScreenMixin::lambda$initCustomButtons$2 /* captured: mc */);
        accountsButton.setPosition((float)centerX - (float)buttonWidth / 2f, (float)(startY + (buttonHeight + spacing) * 2));
        accountsButton.setSize((float)buttonWidth, (float)buttonHeight);
        this.customButtons.add(accountsButton);
        int smallButtonWidth = (buttonWidth - spacing) / 2;
        CustomMenuButton optionsButton = new CustomMenuButton("Options", TitleScreenMixin::lambda$initCustomButtons$3 /* captured: mc */);
        optionsButton.setPosition((float)centerX - (float)buttonWidth / 2f, (float)(startY + (buttonHeight + spacing) * 3));
        optionsButton.setSize((float)smallButtonWidth, (float)buttonHeight);
        this.customButtons.add(optionsButton);
        CustomMenuButton exitButton = new CustomMenuButton("Exit", TitleScreenMixin::lambda$initCustomButtons$4 /* captured: mc */);
        exitButton.setPosition((float)centerX - (float)buttonWidth / 2f + (float)smallButtonWidth + (float)spacing, (float)(startY + (buttonHeight + spacing) * 3));
        exitButton.setSize((float)smallButtonWidth, (float)buttonHeight);
        this.customButtons.add(exitButton);
        this.method_37067();
    }

    @Inject(method={"mouseClicked"}, at={@At(value="HEAD")}, cancellable=true)
    private void onMouseClicked(double mouseX, double mouseY, int button, CallbackInfoReturnable<Boolean> cir) {
        for (CustomMenuButton customButton : this.customButtons) {
            if (customButton.mouseClicked(mouseX, mouseY, button)) {
                cir.setReturnValue(true);
                cir.cancel();
                return;
            }
        }
    }
}
