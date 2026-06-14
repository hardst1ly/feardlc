/*
 * Decompiled with https://jar.tools
 */
package fear.dlc.ui.menu;

import fear.dlc.Api;
import fear.dlc.invoke.mixins.MinecraftClientAccessor;
import fear.dlc.ui.menu.CustomMenuButton;
import fear.dlc.ui.menu.MenuShaderBackground;
import fear.dlc.utility.color.ColorUtility;
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
import java.util.Optional;
import java.util.Random;
import net.minecraft.class_2561;
import net.minecraft.class_310;
import net.minecraft.class_320;
import net.minecraft.class_332;
import net.minecraft.class_437;
import org.joml.Matrix4f;

public class AccountsScreen
extends class_437 {
    private final class_437 parent;
    private final List<String> accounts = new ArrayList();
    private final List<CustomMenuButton> buttons = new ArrayList();
    private final Path accountsPath;
    private int selectedAccountIndex = -1;
    private int scrollOffset = 0;
    private static final int MAX_VISIBLE_ACCOUNTS = 8;
    private int tickCounter = 0;
    private float animationProgress = 0f;
    private String statusMessage = "";
    private int statusTimer = 0;
    private static final int BACKGROUND_COLOR = -15069394;
    private static final int ACCOUNT_ITEM_COLOR = -13821123;
    private static final int ACCOUNT_HOVER_COLOR = -12768435;
    private static final int ACCOUNT_SELECTED_COLOR = -10863221;
    private static final int ACCOUNT_ACTIVE_COLOR = -9744997;
    private static final int TEXT_COLOR = -2043649;
    private static final int TEXT_SECONDARY_COLOR = -5201713;
    private static final int SUCCESS_COLOR = -11751600;
    private static final int ERROR_COLOR = -44462;

    public AccountsScreen(class_437 parent) {
        super(class_2561.method_43470("Accounts"));
        this.parent = parent;
        String userHome = System.getProperty("user.home");
        this.accountsPath = Paths.get(userHome, "AppData", "LocalLow", "Microsoft", "accounts.txt");
        this.loadAccounts();
    }

    private String getCurrentUsername() {
        class_310 mc = class_310.method_1551();
        return mc.method_1548().method_1676();
    }

    private void loadAccounts() {
        this.accounts.clear();
        try {
            if (Files.exists(this.accountsPath, new LinkOption[0])) {
                List<String> lines = Files.readAllLines(this.accountsPath);
                this.accounts.addAll(lines);
            }
        }
        catch (IOException e) {
            e.printStackTrace();
            return;
        }
    }

    private void saveAccounts() {
        try {
            Files.createDirectories(this.accountsPath.getParent(), new FileAttribute[0]);
            Files.write(this.accountsPath, this.accounts, new OpenOption[0]);
        }
        catch (IOException e) {
            e.printStackTrace();
            return;
        }
    }

    protected void method_25426() {
        this.buttons.clear();
        int centerX = this.field_22789 / 2;
        int bottomY = this.field_22790 - 44;
        int buttonWidth = 60;
        int buttonHeight = 18;
        int spacing = 5;
        CustomMenuButton selectButton = new CustomMenuButton("Select", this::lambda$init$0);
        selectButton.setPosition((float)(centerX - buttonWidth * 2) - (float)spacing * 1.5f, (float)bottomY);
        selectButton.setSize((float)buttonWidth, (float)buttonHeight);
        this.buttons.add(selectButton);
        CustomMenuButton addButton = new CustomMenuButton("Add", this::lambda$init$1);
        addButton.setPosition((float)(centerX - buttonWidth) - (float)spacing / 2f, (float)bottomY);
        addButton.setSize((float)buttonWidth, (float)buttonHeight);
        this.buttons.add(addButton);
        CustomMenuButton removeButton = new CustomMenuButton("Remove", this::lambda$init$2);
        removeButton.setPosition((float)centerX + (float)spacing / 2f, (float)bottomY);
        removeButton.setSize((float)buttonWidth, (float)buttonHeight);
        this.buttons.add(removeButton);
        CustomMenuButton randomButton = new CustomMenuButton("Random", this::lambda$init$3);
        randomButton.setPosition((float)(centerX + buttonWidth) + (float)spacing * 1.5f, (float)bottomY);
        randomButton.setSize((float)buttonWidth, (float)buttonHeight);
        this.buttons.add(randomButton);
        CustomMenuButton backButton = new CustomMenuButton("Back", this::lambda$init$4);
        backButton.setPosition((float)centerX - (float)buttonWidth / 2f, (float)(bottomY + buttonHeight + spacing));
        backButton.setSize((float)buttonWidth, (float)buttonHeight);
        this.buttons.add(backButton);
    }

    private String generateRandomNick() {
        String[] tmp0 = new String[10];
        tmp0[0] = "Pro";
        tmp0[1] = "Epic";
        tmp0[2] = "Dark";
        tmp0[3] = "Shadow";
        tmp0[4] = "Fire";
        tmp0[5] = "Ice";
        tmp0[6] = "Thunder";
        tmp0[7] = "Storm";
        tmp0[8] = "Night";
        tmp0[9] = "Blood";
        String[] prefixes = tmp0;
        String[] tmp1 = new String[10];
        tmp1[0] = "Gamer";
        tmp1[1] = "Player";
        tmp1[2] = "Killer";
        tmp1[3] = "Master";
        tmp1[4] = "Lord";
        tmp1[5] = "King";
        tmp1[6] = "Warrior";
        tmp1[7] = "Hunter";
        tmp1[8] = "Slayer";
        tmp1[9] = "Legend";
        String[] suffixes = tmp1;
        Random random = new Random();
        return prefixes[random.nextInt(prefixes.length)] + suffixes[random.nextInt(suffixes.length)] + random.nextInt(1000);
    }

    public void method_25394(class_332 context, int mouseX, int mouseY, float delta) {
        Matrix4f matrix = context.method_51448().method_23760().method_23761();
        this.animationProgress = this.animationProgress + delta * 0.05f;
        if (this.animationProgress > 1f) {
            this.animationProgress = 0f;
        }
        if (this.statusTimer > 0) {
            this.statusTimer = this.statusTimer - 1;
        }
        this.tickCounter = this.tickCounter + 1;
        if (this.tickCounter >= 200) {
            this.tickCounter = 0;
            int oldSize = this.accounts.size();
            this.loadAccounts();
            if (this.selectedAccountIndex >= this.accounts.size()) {
                this.selectedAccountIndex = -1;
            }
            if (oldSize != this.accounts.size()) {
                this.setStatusMessage("\u0421\u043f\u0438\u0441\u043e\u043a \u043e\u0431\u043d\u043e\u0432\u043b\u0435\u043d", true);
            }
        }
        MenuShaderBackground.render(context.method_51448(), context.method_51421(), context.method_51443(), delta);
        String title = "Accounts Manager";
        float titleWidth = Api.inter().getWidth(title, 14f);
        int titleColor = ColorUtility.lerp((float)Math.sin((double)this.animationProgress * 3.141592653589793 * 2) * 0.2f + 0.8f, -2043649, -10863221);
        ((BuiltText)Api.text().text(title).size(14f).font(Api.inter()).color(titleColor).build()).render(matrix, (float)this.field_22789 / 2f - titleWidth / 2f, 25f);
        String currentUser = this.getCurrentUsername();
        String currentText = "\u0422\u0435\u043a\u0443\u0449\u0438\u0439: " + currentUser;
        float currentWidth = Api.inter().getWidth(currentText, 8f);
        ((BuiltText)Api.text().text(currentText).size(8f).font(Api.inter()).color(-11751600).build()).render(matrix, (float)this.field_22789 / 2f - currentWidth / 2f, 50f);
        if (this.statusTimer <= 0) { /* goto L475; */ }
        float statusAlpha = Math.min(1f, (float)this.statusTimer / 20f);
        int statusColor = ColorUtility.applyOpacity(this.statusMessage.contains("\u0443\u0441\u043f\u0435\u0448\u043d\u043e") || this.statusMessage.contains("\u043e\u0431\u043d\u043e\u0432\u043b\u0435\u043d") ? -11751600 : -44462, (int)(statusAlpha * 255f));
        float statusWidth = Api.inter().getWidth(this.statusMessage, 7f);
        ((BuiltText)Api.text().text(this.statusMessage).size(7f).font(Api.inter()).color(statusColor).build()).render(matrix, (float)this.field_22789 / 2f - statusWidth / 2f, 65f);
        this.renderAccountsList(context, mouseX, mouseY, matrix);
        for (CustomMenuButton button : this.buttons) {
            button.render(context, mouseX, mouseY, delta);
        }
    }

    private void setStatusMessage(String message, boolean success) {
        this.statusMessage = message;
        this.statusTimer = 60;
    }

    private void renderAccountsList(class_332 context, int mouseX, int mouseY, Matrix4f matrix) {
        int listX = this.field_22789 / 2 - 200;
        int listY = 90;
        int listWidth = 400;
        int itemHeight = 40;
        int spacing = 5;
        ((BuiltRectangle)Api.rectangle().size(new SizeState((float)listWidth, (float)(8 * (itemHeight + spacing) + 10))).radius(new QuadRadiusState(10f, 10f, 10f, 10f)).color(new QuadColorState(ColorUtility.applyOpacity(-15069394, 180))).build()).render(matrix, (float)listX, (float)listY);
        String countText = "\u0412\u0441\u0435\u0433\u043e \u0430\u043a\u043a\u0430\u0443\u043d\u0442\u043e\u0432: " + this.accounts.size();
        ((BuiltText)Api.text().text(countText).size(7f).font(Api.inter()).color(-5201713).build()).render(matrix, (float)(listX + 10), (float)(listY + 5));
        int visibleStart = this.scrollOffset;
        int visibleEnd = Math.min(this.accounts.size(), this.scrollOffset + 8);
        String currentUser = this.getCurrentUsername();
        for (int i = visibleStart; i < visibleEnd; i++) {
            int itemY = listY + 20 + (i - this.scrollOffset) * (itemHeight + spacing);
            boolean hovered = mouseX >= listX + 5 && mouseX <= listX + listWidth - 5 && mouseY >= itemY && mouseY <= itemY + itemHeight;
            boolean selected = i == this.selectedAccountIndex;
            boolean isActive = ((String)this.accounts.get(i)).equals(currentUser);
            if (isActive) {
                int itemColor = -9744997;
            } else {
                if (selected) {
                    int itemColor = -10863221;
                } else {
                    if (hovered) {
                        int itemColor = -12768435;
                    } else {
                        int itemColor = -13821123;
                    }
                }
            }
            if (!hovered || selected) continue;
            itemColor = ColorUtility.applyOpacity(itemColor, 220);
            ((BuiltRectangle)Api.rectangle().size(new SizeState((float)(listWidth - 10), (float)itemHeight)).radius(new QuadRadiusState(8f, 8f, 8f, 8f)).color(new QuadColorState(itemColor)).build()).render(matrix, (float)(listX + 5), (float)itemY);
            if (!isActive) continue;
            ((BuiltRectangle)Api.rectangle().size(new SizeState(4f, (float)(itemHeight - 10))).radius(new QuadRadiusState(2f, 2f, 2f, 2f)).color(new QuadColorState(-11751600)).build()).render(matrix, (float)(listX + 10), (float)(itemY + 5));
            String numberText = "#" + i + 1;
            ((BuiltText)Api.text().text(numberText).size(7f).font(Api.inter()).color(-5201713).build()).render(matrix, (float)(listX + (isActive ? 20 : 15)), (float)(itemY + 5));
            String accountName = this.accounts.get(i);
            float textHeight = Api.inter().getHeight(accountName, 9f);
            int textColor = isActive ? -11751600 : -2043649;
            ((BuiltText)Api.text().text(accountName).size(9f).font(Api.inter()).color(textColor).build()).render(matrix, (float)(listX + (isActive ? 20 : 15)), (float)itemY + ((float)itemHeight - textHeight) / 2f + 5f);
            if (isActive) {
                String activeText = "\u0410\u041a\u0422\u0418\u0412\u0415\u041d";
                float activeWidth = Api.inter().getWidth(activeText, 6f);
                ((BuiltText)Api.text().text(activeText).size(6f).font(Api.inter()).color(-11751600).build()).render(matrix, (float)(listX + listWidth) - activeWidth - 15f, (float)(itemY + itemHeight - 12));
            }
        }
        if (this.accounts.size() > 8) {
            int scrollbarHeight = 8 * (itemHeight + spacing);
            int scrollbarThumbHeight = Math.max(20, (int)(8f / (float)this.accounts.size() * (float)scrollbarHeight));
            int scrollbarThumbY = ((float)this.scrollOffset / (float)(this.accounts.size() - 8) * (float)(scrollbarHeight - scrollbarThumbHeight));
            ((BuiltRectangle)Api.rectangle().size(new SizeState(6f, (float)scrollbarHeight)).radius(new QuadRadiusState(3f, 3f, 3f, 3f)).color(new QuadColorState(ColorUtility.applyOpacity(-13821123, 120))).build()).render(matrix, (float)(listX + listWidth + 8), (float)(listY + 20));
            ((BuiltRectangle)Api.rectangle().size(new SizeState(6f, (float)scrollbarThumbHeight)).radius(new QuadRadiusState(3f, 3f, 3f, 3f)).color(new QuadColorState(-10863221)).build()).render(matrix, (float)(listX + listWidth + 8), (float)(listY + 20 + scrollbarThumbY));
        }
    }

    public boolean method_25402(double mouseX, double mouseY, int button) {
        for (CustomMenuButton btn : this.buttons) {
            if (!btn.mouseClicked(mouseX, mouseY, button)) continue;
            return true;
        }
        listX = this.field_22789 / 2 - 200;
        int listY = 90;
        int listWidth = 400;
        int itemHeight = 40;
        int spacing = 5;
        int visibleStart = this.scrollOffset;
        int visibleEnd = Math.min(this.accounts.size(), this.scrollOffset + 8);
        for (int i = visibleStart; i < visibleEnd; i++) {
            int itemY = listY + 20 + (i - this.scrollOffset) * (itemHeight + spacing);
            if (mouseX >= (double)(listX + 5)) {
                if (mouseX <= (double)(listX + listWidth - 5)) {
                    if (mouseY >= (double)itemY) {
                        if (mouseY <= (double)(itemY + itemHeight)) {
                            if (button == 0) {
                                this.selectedAccountIndex = i;
                                return true;
                            }
                            if (button == 1) {
                                this.selectedAccountIndex = i;
                                this.applyAccount((String)this.accounts.get(i));
                                return true;
                            }
                        }
                    }
                }
            }
        }
        return super.method_25402(mouseX, mouseY, button);
    }

    private void applyAccount(String nickname) {
        class_310 mc = class_310.method_1551();
        try {
            MinecraftClientAccessor accessor = mc;
            class_320 oldSession = accessor.getSession();
            class_320 newSession = new class_320(nickname, oldSession.method_44717(), oldSession.method_1674(), Optional.empty(), Optional.empty(), oldSession.method_35718());
            accessor.setSession(newSession);
            String newUsername = mc.method_1548().method_1676();
            if (newUsername.equals(nickname)) {
                this.setStatusMessage("\u0410\u043a\u043a\u0430\u0443\u043d\u0442 \u0443\u0441\u043f\u0435\u0448\u043d\u043e \u0438\u0437\u043c\u0435\u043d\u0435\u043d \u043d\u0430: " + nickname, true);
            } else {
                this.setStatusMessage("\u041f\u0440\u0435\u0434\u0443\u043f\u0440\u0435\u0436\u0434\u0435\u043d\u0438\u0435: \u0438\u043c\u044f \u043d\u0435 \u0438\u0437\u043c\u0435\u043d\u0438\u043b\u043e\u0441\u044c", false);
            }
            new Thread(this::lambda$applyAccount$6 /* captured: mc */).start();
        }
        catch (ClassCastException e) {
            this.setStatusMessage("\u041e\u0448\u0438\u0431\u043a\u0430: \u043d\u0435 \u0443\u0434\u0430\u043b\u043e\u0441\u044c \u043f\u043e\u043b\u0443\u0447\u0438\u0442\u044c \u0434\u043e\u0441\u0442\u0443\u043f \u043a \u0441\u0435\u0441\u0441\u0438\u0438", false);
            e.printStackTrace();
        }
        catch (Exception e) {
            this.setStatusMessage("\u041e\u0448\u0438\u0431\u043a\u0430: " + e.getMessage(), false);
            e.printStackTrace();
            return;
        }
    }

    public boolean method_25401(double mouseX, double mouseY, double horizontalAmount, double verticalAmount) {
        if (this.accounts.size() > 8) {
            this.scrollOffset = Math.max(0, Math.min(this.accounts.size() - 8, this.scrollOffset - (int)verticalAmount));
            return true;
        }
        return super.method_25401(mouseX, mouseY, horizontalAmount, verticalAmount);
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
