/*
 * Decompiled with https://jar.tools
 */
package fear.dlc.api.commands.list.help;

import com.google.common.collect.Lists;
import fear.dlc.FearDCP;
import fear.dlc.api.commands.CommandLayer;
import fear.dlc.modules.more.ModuleLayer;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import net.minecraft.class_2561;
import net.minecraft.class_2583;
import net.minecraft.class_5250;

public class HelpLayerCommand
extends CommandLayer {
    public HelpLayerCommand() {
        String[] tmp0 = new String[1];
        tmp0[0] = "help";
        super(Lists.newArrayList(tmp0));
    }

    public void execute(List<String> arguments) {
        this.printHeader();
        this.printCommand(".help", "\u041f\u043e\u043a\u0430\u0437\u0430\u0442\u044c \u044d\u0442\u043e \u0441\u043e\u043e\u0431\u0449\u0435\u043d\u0438\u0435", 0);
        this.printCommand(".bind list", "\u041f\u043e\u043a\u0430\u0437\u0430\u0442\u044c \u0432\u0441\u0435 \u0431\u0438\u043d\u0434\u044b \u043c\u043e\u0434\u0443\u043b\u0435\u0439", 30);
        this.printCommand(".gps add <name> <x> <y> <z>", "\u0414\u043e\u0431\u0430\u0432\u0438\u0442\u044c GPS \u043c\u0435\u0442\u043a\u0443", 60);
        this.printCommand(".gps remove <name>", "\u0423\u0434\u0430\u043b\u0438\u0442\u044c GPS \u043c\u0435\u0442\u043a\u0443", 90);
        this.printCommand(".cfg save <name>", "\u0421\u043e\u0445\u0440\u0430\u043d\u0438\u0442\u044c \u043a\u043e\u043d\u0444\u0438\u0433\u0443\u0440\u0430\u0446\u0438\u044e", 120);
        this.printCommand(".cfg load <name>", "\u0417\u0430\u0433\u0440\u0443\u0437\u0438\u0442\u044c \u043a\u043e\u043d\u0444\u0438\u0433\u0443\u0440\u0430\u0446\u0438\u044e", 150);
        this.printCommand(".cfg list", "\u0421\u043f\u0438\u0441\u043e\u043a \u0432\u0441\u0435\u0445 \u043a\u043e\u043d\u0444\u0438\u0433\u043e\u0432", 180);
        this.printCommand(".cfg dir", "\u041e\u0442\u043a\u0440\u044b\u0442\u044c \u043f\u0430\u043f\u043a\u0443 \u0441 \u043a\u043e\u043d\u0444\u0438\u0433\u0430\u043c\u0438", 210);
        this.printCommand(".friend add <name>", "\u0414\u043e\u0431\u0430\u0432\u0438\u0442\u044c \u0434\u0440\u0443\u0433\u0430", 240);
        this.printCommand(".friend remove <name>", "\u0423\u0434\u0430\u043b\u0438\u0442\u044c \u0434\u0440\u0443\u0433\u0430", 270);
        this.printCommand(".friend list", "\u041f\u043e\u043a\u0430\u0437\u0430\u0442\u044c \u0441\u043f\u0438\u0441\u043e\u043a \u0434\u0440\u0443\u0437\u0435\u0439", 300);
        this.printCommand(".record start", "\u041d\u0430\u0447\u0430\u0442\u044c \u0437\u0430\u043f\u0438\u0441\u044c \u043c\u044b\u0448\u043a\u0438", 330);
        this.printCommand(".record stop", "\u041e\u0441\u0442\u0430\u043d\u043e\u0432\u0438\u0442\u044c \u0438 \u0441\u043e\u0445\u0440\u0430\u043d\u0438\u0442\u044c", 360);
        this.printCommand(".record list", "\u0421\u043f\u0438\u0441\u043e\u043a \u0432\u0441\u0435\u0445 \u0437\u0430\u043f\u0438\u0441\u0435\u0439", 390);
        this.printCommand(".record load <name>", "\u0417\u0430\u0433\u0440\u0443\u0437\u0438\u0442\u044c \u0437\u0430\u043f\u0438\u0441\u044c \u0432 \u043d\u0435\u0439\u0440\u043e-\u0440\u0435\u0436\u0438\u043c", 420);
        this.printCommand(".record dir", "\u041e\u0442\u043a\u0440\u044b\u0442\u044c \u043f\u0430\u043f\u043a\u0443 \u0441 \u0437\u0430\u043f\u0438\u0441\u044f\u043c\u0438", 450);
        this.printModulesSection();
    }

    private void printModulesSection() {
        class_5250 separator = class_2561.method_43470("\n");
        mc.field_1705.method_1743().method_1812(separator);
        class_5250 modulesHeader = class_2561.method_43470("[FearDLC] ").method_10862(class_2583.field_24360.method_36139(this.getGradientColor(0)));
        class_5250 modulesMsg = class_2561.method_43470("\u0410\u043a\u0442\u0438\u0432\u043d\u044b\u0435 \u043c\u043e\u0434\u0443\u043b\u0438:\n").method_10862(class_2583.field_24360.method_36139(-1));
        mc.field_1705.method_1743().method_1812(modulesHeader.method_10852(modulesMsg));
        List<ModuleLayer> modules = new ArrayList(FearDCP.getInstance().getModuleRepository().getModuleLayers());
        modules.sort(Comparator.comparing(HelpLayerCommand::lambda$printModulesSection$0));
        String currentCategory = null;
        int offset = 480;
        for (ModuleLayer module : modules) {
            String category = module.getCategory().name();
            if (!category.equals(currentCategory)) {
                currentCategory = category;
                class_5250 catText = class_2561.method_43470("\n  " + category + ":\n").method_10862(class_2583.field_24360.method_36139(-7829368));
                mc.field_1705.method_1743().method_1812(catText);
            }
            String state = module.getEnabled().booleanValue() ? "ON " : "OFF";
            int color = module.getEnabled().booleanValue() ? -11141291 : -11184811;
            class_5250 moduleText = class_2561.method_43470("    " + module.getModuleName().getString() + " ").method_10862(class_2583.field_24360.method_36139(this.getGradientColor(offset)));
            class_5250 stateText = class_2561.method_43470("[" + state + "]").method_10862(class_2583.field_24360.method_36139(color));
            class_5250 descText = class_2561.method_43470(" - " + this.getModuleDescription(module)).method_10862(class_2583.field_24360.method_36139(-5592406));
            mc.field_1705.method_1743().method_1812(moduleText.method_10852(stateText).method_10852(descText));
            offset += 15;
        }
    }

    private String getModuleDescription(ModuleLayer module) {
        String name = module.getClass().getSimpleName().toLowerCase();
        if (name.contains("sprint")) {
            return "\u0410\u0432\u0442\u043e\u043c\u0430\u0442\u0438\u0447\u0435\u0441\u043a\u0438\u0439 \u0441\u043f\u0440\u0438\u043d\u0442";
        }
        if (name.contains("aimassist")) {
            return "\u041f\u043e\u043c\u043e\u0449\u044c \u0432 \u043f\u0440\u0438\u0446\u0435\u043b\u0438\u0432\u0430\u043d\u0438\u0438";
        }
        if (name.contains("triggerbot")) {
            return "\u0410\u0432\u0442\u043e\u043c\u0430\u0442\u0438\u0447\u0435\u0441\u043a\u0438\u0439 \u043a\u043b\u0438\u043a \u043f\u043e \u0446\u0435\u043b\u0438";
        }
        if (name.contains("autototem")) {
            return "\u0410\u0432\u0442\u043e-\u043f\u043e\u0434\u043c\u0435\u043d\u0430 \u0442\u043e\u0442\u0435\u043c\u0430";
        }
        if (name.contains("hitbox")) {
            return "\u0423\u0432\u0435\u043b\u0438\u0447\u0435\u043d\u0438\u0435 \u0445\u0438\u0442\u0431\u043e\u043a\u0441\u043e\u0432";
        }
        if (name.contains("autoeat")) {
            return "\u0410\u0432\u0442\u043e\u043c\u0430\u0442\u0438\u0447\u0435\u0441\u043a\u043e\u0435 \u043f\u043e\u0435\u0434\u0430\u043d\u0438\u0435 \u0435\u0434\u044b";
        }
        if (name.contains("autoinvisible")) {
            return "\u0410\u0432\u0442\u043e\u043c\u0430\u0442\u0438\u0447\u0435\u0441\u043a\u0430\u044f \u043d\u0435\u0432\u0438\u0434\u0438\u043c\u043e\u0441\u0442\u044c";
        }
        if (name.contains("fakplayer")) {
            return "\u0424\u0435\u0439\u043a-\u043f\u043b\u0435\u0435\u0440";
        }
        if (name.contains("serverassistant")) {
            return "\u041f\u043e\u043c\u043e\u0449\u043d\u0438\u043a \u0441\u0435\u0440\u0432\u0435\u0440\u0430";
        }
        if (name.contains("ambience")) {
            return "\u0410\u0442\u043c\u043e\u0441\u0444\u0435\u0440\u043d\u044b\u0435 \u044d\u0444\u0444\u0435\u043a\u0442\u044b";
        }
        if (name.contains("antiblind")) {
            return "\u0423\u0431\u0440\u0430\u0442\u044c \u0441\u043b\u0435\u043f\u043e\u0442\u0443";
        }
        if (name.contains("aspectratio")) {
            return "\u0418\u0437\u043c\u0435\u043d\u0435\u043d\u0438\u0435 \u0441\u043e\u043e\u0442\u043d\u043e\u0448\u0435\u043d\u0438\u044f \u0441\u0442\u043e\u0440\u043e\u043d";
        }
        if (name.contains("boxesp")) {
            return "3D \u0431\u043e\u043a\u0441 \u043d\u0430 \u0438\u0433\u0440\u043e\u043a\u0430\u0445";
        }
        if (name.contains("blockoutline")) {
            return "\u041a\u043e\u043d\u0442\u0443\u0440 \u0431\u043b\u043e\u043a\u043e\u0432";
        }
        if (name.contains("esp")) {
            return "ESP \u043d\u0430 \u0438\u0433\u0440\u043e\u043a\u0430\u0445";
        }
        if (name.contains("fullbright")) {
            return "\u041c\u0430\u043a\u0441\u0438\u043c\u0430\u043b\u044c\u043d\u0430\u044f \u044f\u0440\u043a\u043e\u0441\u0442\u044c";
        }
        if (name.contains("norender")) {
            return "\u0423\u0431\u0440\u0430\u0442\u044c \u0440\u0435\u043d\u0434\u0435\u0440 \u044d\u0444\u0444\u0435\u043a\u0442\u043e\u0432";
        }
        if (name.contains("seeinvisible")) {
            return "\u0412\u0438\u0434\u0435\u0442\u044c \u043d\u0435\u0432\u0438\u0434\u0438\u043c\u044b\u0445";
        }
        if (name.contains("zoom")) {
            return "\u041f\u0440\u0438\u0431\u043b\u0438\u0436\u0435\u043d\u0438\u0435";
        }
        if (name.contains("cheststealer")) {
            return "\u0410\u0432\u0442\u043e\u043c\u0430\u0442\u0438\u0447\u0435\u0441\u043a\u0438\u0439 \u0432\u043e\u0440";
        }
        if (name.contains("spjoiner")) {
            return "\u0410\u0432\u0442\u043e-\u0432\u0445\u043e\u0434 \u043d\u0430 \u0441\u0435\u0440\u0432\u0435\u0440";
        }
        if (name.contains("saturation")) {
            return "\u041f\u043e\u043a\u0430\u0437 \u043d\u0430\u0441\u044b\u0449\u0435\u043d\u043d\u043e\u0441\u0442\u0438";
        }
        if (name.contains("safemode")) {
            return "\u041e\u0447\u0438\u0441\u0442\u043a\u0430 \u0441\u043b\u0435\u0434\u043e\u0432 \u0447\u0438\u0442\u0430";
        }
        if (name.contains("freecam")) {
            return "\u0421\u0432\u043e\u0431\u043e\u0434\u043d\u0430\u044f \u043a\u0430\u043c\u0435\u0440\u0430";
        }
        if (name.contains("playeroutline")) {
            return "\u041a\u043e\u043d\u0442\u0443\u0440 \u0438\u0433\u0440\u043e\u043a\u043e\u0432";
        }
        if (name.contains("swinganimation")) {
            return "\u0410\u043d\u0438\u043c\u0430\u0446\u0438\u044f \u0440\u0443\u043a\u0438";
        }
        if (name.contains("viewmodel")) {
            return "\u041c\u043e\u0434\u0435\u043b\u044c\u043a\u0430 \u0440\u0443\u043a\u0438";
        }
        if (name.contains("nameprotect")) {
            return "\u0417\u0430\u0449\u0438\u0442\u0430 \u043d\u0438\u043a\u0430";
        }
        if (name.contains("worldparticles")) {
            return "\u0427\u0430\u0441\u0442\u0438\u0446\u044b \u043c\u0438\u0440\u0430";
        }
        if (name.contains("arrows")) {
            return "\u0421\u0442\u0440\u0435\u043b\u043a\u0438 \u043d\u0430\u043f\u0440\u0430\u0432\u043b\u0435\u043d\u0438\u044f";
        }
        if (name.contains("nopush")) {
            return "\u041e\u0442\u0442\u0430\u043b\u043a\u0438\u0432\u0430\u043d\u0438\u0435 \u043e\u0442 \u0438\u0433\u0440\u043e\u043a\u043e\u0432";
        }
        if (name.contains("guiwalk")) {
            return "\u0423\u043f\u0440\u0430\u0432\u043b\u0435\u043d\u0438\u0435 \u0432 \u043c\u0435\u043d\u044e";
        }
        if (name.contains("targetesp")) {
            return "ESP \u043d\u0430 \u0432\u044b\u0431\u0440\u0430\u043d\u043d\u043e\u0439 \u0446\u0435\u043b\u0438";
        }
        if (name.contains("hud")) {
            return "HUD \u0438\u043d\u0442\u0435\u0440\u0444\u0435\u0439\u0441";
        }
        if (name.contains("projectilepredict")) {
            return "\u041f\u0440\u0435\u0434\u0441\u043a\u0430\u0437\u0430\u043d\u0438\u0435 \u0441\u043d\u0430\u0440\u044f\u0434\u043e\u0432";
        }
        if (name.contains("nametags")) {
            return "\u0418\u043c\u0435\u043d\u0430 \u043d\u0430\u0434 \u0438\u0433\u0440\u043e\u043a\u0430\u043c\u0438";
        }
        if (name.contains("spider")) {
            return "\u041f\u043e\u043b\u0437\u0430\u043d\u044c\u0435 \u043f\u043e \u0441\u0442\u0435\u043d\u0430\u043c";
        }
        return module.getModuleDescription().getString();
    }

    private void printHeader() {
        class_5250 prefix = class_2561.method_43470("[FearDLC] ").method_10862(class_2583.field_24360.method_36139(this.getGradientColor(0)));
        class_5250 msg = class_2561.method_43470("\u0421\u043f\u0438\u0441\u043e\u043a \u0434\u043e\u0441\u0442\u0443\u043f\u043d\u044b\u0445 \u043a\u043e\u043c\u0430\u043d\u0434:\n").method_10862(class_2583.field_24360.method_36139(-1));
        mc.field_1705.method_1743().method_1812(prefix.method_10852(msg));
    }

    private void printCommand(String command, String description, int offset) {
        class_5250 cmd = class_2561.method_43470("  " + command + " ").method_10862(class_2583.field_24360.method_36139(this.getGradientColor(offset)));
        class_5250 desc = class_2561.method_43470("- " + description).method_10862(class_2583.field_24360.method_36139(-5592406));
        mc.field_1705.method_1743().method_1812(cmd.method_10852(desc));
    }
}
