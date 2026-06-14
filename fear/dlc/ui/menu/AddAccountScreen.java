/*
 * Decompiled with https://jar.tools
 */
package fear.dlc.ui.menu;

import fear.dlc.Api;
import fear.dlc.ui.menu.AccountsScreen;
import fear.dlc.ui.menu.CustomMenuButton;
import fear.dlc.ui.menu.MenuShaderBackground;
import fear.dlc.utility.render.builders.states.QuadColorState;
import fear.dlc.utility.render.builders.states.QuadRadiusState;
import fear.dlc.utility.render.builders.states.SizeState;
import fear.dlc.utility.render.renderers.impl.BuiltRectangle;
import fear.dlc.utility.render.renderers.impl.BuiltText;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.FileAttribute;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.class_2561;
import net.minecraft.class_310;
import net.minecraft.class_332;
import net.minecraft.class_342;
import net.minecraft.class_437;
import org.joml.Matrix4f;

public class AddAccountScreen
extends class_437 {
    private final AccountsScreen parent;
    private class_342 nicknameField;
    private final List<CustomMenuButton> buttons = new ArrayList();
    private static final int TEXT_COLOR = -2043649;
    private static final int INPUT_BG_COLOR = -13821123;

    public AddAccountScreen(AccountsScreen parent) {
        super(class_2561.method_43470("Add Account"));
        this.parent = parent;
    }

    protected void method_25426() {
        this.buttons.clear();
        int centerX = this.field_22789 / 2;
        int centerY = this.field_22790 / 2;
        this.nicknameField = new class_342(this.field_22793, centerX - 50, centerY - 5, 100, 12, class_2561.method_43470("Nickname"));
        this.nicknameField.method_1880(16);
        this.nicknameField.method_47404(class_2561.method_43470("Enter nickname..."));
        this.method_25429(this.nicknameField);
        this.method_48265(this.nicknameField);
        int buttonWidth = 70;
        int buttonHeight = 22;
        int spacing = 8;
        CustomMenuButton addButton = new CustomMenuButton("Add", this::lambda$init$0);
        addButton.setPosition((float)(centerX - buttonWidth) - (float)spacing / 2f, (float)(centerY + 25));
        addButton.setSize((float)buttonWidth, (float)buttonHeight);
        this.buttons.add(addButton);
        CustomMenuButton cancelButton = new CustomMenuButton("Cancel", this::lambda$init$1);
        cancelButton.setPosition((float)centerX + (float)spacing / 2f, (float)(centerY + 25));
        cancelButton.setSize((float)buttonWidth, (float)buttonHeight);
        this.buttons.add(cancelButton);
    }

    private void addAccount(String nickname) {
        try {
            String userHome = System.getProperty("user.home");
            String[] tmp0 = new String[4];
            tmp0[0] = "AppData";
            tmp0[1] = "LocalLow";
            tmp0[2] = "Microsoft";
            tmp0[3] = "accounts.txt";
            Path accountsPath = Paths.get(userHome, tmp0);
            Files.createDirectories(accountsPath.getParent(), new FileAttribute[0]);
            List<String> accounts = new ArrayList();
            if (Files.exists(accountsPath, new LinkOption[0])) {
                accounts.addAll(Files.readAllLines(accountsPath));
            }
            if (!accounts.contains(nickname)) {
                accounts.add(nickname);
                Files.write(accountsPath, accounts, new OpenOption[0]);
            }
        }
        catch (IOException e) {
            e.printStackTrace();
            return;
        }
    }

    public void method_25394(class_332 context, int mouseX, int mouseY, float delta) {
        Matrix4f matrix = context.method_51448().method_23760().method_23761();
        MenuShaderBackground.render(context.method_51448(), context.method_51421(), context.method_51443(), delta);
        String title = "Add Account";
        float titleWidth = Api.inter().getWidth(title, 8f);
        ((BuiltText)Api.text().text(title).size(8f).font(Api.inter()).color(-2043649).build()).render(matrix, (float)this.field_22789 / 2f - titleWidth / 2f, (float)this.field_22790 / 2f - 50f);
        String hint = "Enter nickname:";
        float hintWidth = Api.inter().getWidth(hint, 6f);
        ((BuiltText)Api.text().text(hint).size(6f).font(Api.inter()).color(-2043649).build()).render(matrix, (float)this.field_22789 / 2f - hintWidth / 2f, (float)this.field_22790 / 2f - 25f);
        ((BuiltRectangle)Api.rectangle().size(new SizeState(104f, 14f)).radius(new QuadRadiusState(3f, 3f, 3f, 3f)).color(new QuadColorState(-13821123)).build()).render(matrix, (float)this.field_22789 / 2f - 52f, (float)this.field_22790 / 2f - 7f);
        this.nicknameField.method_25394(context, mouseX, mouseY, delta);
        for (CustomMenuButton button : this.buttons) {
            button.render(context, mouseX, mouseY, delta);
        }
    }

    public boolean method_25402(double mouseX, double mouseY, int button) {
        for (CustomMenuButton btn : this.buttons) {
            if (!btn.mouseClicked(mouseX, mouseY, button)) continue;
            return true;
        }
        return super.method_25402(mouseX, mouseY, button);
    }

    public boolean method_25404(int keyCode, int scanCode, int modifiers) {
        if (keyCode == 257) {
            String nickname = this.nicknameField.method_1882().trim();
            if (!nickname.isEmpty()) {
                this.addAccount(nickname);
                class_310.method_1551().method_1507(this.parent);
                return true;
            }
        }
        if (keyCode == 256) {
            class_310.method_1551().method_1507(this.parent);
            return true;
        }
        return super.method_25404(keyCode, scanCode, modifiers);
    }

    public boolean method_25421() {
        return false;
    }

    public boolean method_25422() {
        return false;
    }

    public void method_25419() {
    }
}
