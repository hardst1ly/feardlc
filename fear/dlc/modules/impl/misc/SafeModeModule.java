/*
 * Decompiled with https://jar.tools
 */
package fear.dlc.modules.impl.misc;

import fear.dlc.FearDCP;
import fear.dlc.modules.more.Category;
import fear.dlc.modules.more.ModuleLayer;
import fear.dlc.modules.settings.impl.BooleanSetting;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import net.minecraft.class_2561;

public class SafeModeModule
extends ModuleLayer {
    private static boolean clickGuiPermanentlyBlocked = false;
    private static boolean cheatFullyDisabled = false;
    public final BooleanSetting cleanLogs;
    public final BooleanSetting deleteFearDLC;
    public final BooleanSetting disableAllModules;
    public final BooleanSetting clearBinds;
    public final BooleanSetting blockClickGui;
    public final BooleanSetting clearChat;
    private static final Pattern[] GLOBAL_PATTERNS = new Pattern[]{Pattern.compile("\\[FearDLC\\]", 2), Pattern.compile("\\[Server Assistant\\]", 2), Pattern.compile("Fear DLC", 2), Pattern.compile("FearDLC", 2), Pattern.compile("FearDCP", 2), Pattern.compile("pasxalka", 2), Pattern.compile("rivalvisual", 2), Pattern.compile("fear\\.dlc", 2)};
    private static final Pattern[] CHAT_ONLY_PATTERNS = new Pattern[]{Pattern.compile("\u043a\u043e\u043d\u0444\u0438\u0433", 2), Pattern.compile("\u043a\u0444\u0433", 2), Pattern.compile("\\bconfig\\b", 2), Pattern.compile("\\bcfg\\b", 2), Pattern.compile("\\.cfg", 2), Pattern.compile("\u0414\u043e\u0431\u0430\u0432\u0438\u0442\u044c \u0434\u0440\u0443\u0433\u0430", 2), Pattern.compile("\u0423\u0434\u0430\u043b\u0438\u0442\u044c \u0434\u0440\u0443\u0433\u0430", 2), Pattern.compile("\u0421\u043f\u0438\u0441\u043e\u043a \u0434\u0440\u0443\u0437\u0435\u0439", 2), Pattern.compile("\u0414\u043e\u0431\u0430\u0432\u0438\u0442\u044c \u0441\u0442\u0430\u0444\u0444\u0430", 2), Pattern.compile("GPS \u043c\u0435\u0442\u043a\u0430", 2), Pattern.compile("\u0421\u043e\u0445\u0440\u0430\u043d\u0438\u0442\u044c \u043a\u043e\u043d\u0444\u0438\u0433\u0443\u0440\u0430\u0446\u0438\u044e", 2), Pattern.compile("\u0417\u0430\u0433\u0440\u0443\u0437\u0438\u0442\u044c \u043a\u043e\u043d\u0444\u0438\u0433\u0443\u0440\u0430\u0446\u0438\u044e", 2), Pattern.compile("\u043d\u0430 \u043a\u0443\u043b\u0434\u0430\u0443\u043d\u0435", 2), Pattern.compile("\u043d\u0435 \u043d\u0430\u0439\u0434\u0435\u043d \u0432 \u0438\u043d\u0432\u0435\u043d\u0442\u0430\u0440\u0435", 2), Pattern.compile("\u041f\u0430\u043f\u043a\u0430 \u0441 \u043a\u043e\u043d\u0444\u0438\u0433\u0430\u043c\u0438 \u043e\u0442\u043a\u0440\u044b\u0442\u0430", 2), Pattern.compile("\u0418\u0441\u043f\u043e\u043b\u044c\u0437\u043e\u0432\u0430\u043d\u0438\u0435 \u043a\u043e\u043c\u0430\u043d\u0434\u044b", 2), Pattern.compile("\u0421\u043f\u0438\u0441\u043e\u043a \u0434\u043e\u0441\u0442\u0443\u043f\u043d\u044b\u0445 \u043a\u043e\u043c\u0430\u043d\u0434", 2), Pattern.compile("\u041a\u043e\u043d\u0444\u0438\u0433.*\u0437\u0430\u0433\u0440\u0443\u0436\u0435\u043d", 2), Pattern.compile("\u041a\u043e\u043d\u0444\u0438\u0433\u0443\u0440\u0430\u0446\u0438\u044f", 2), Pattern.compile("\u0421\u043f\u0438\u0441\u043e\u043a \u0431\u0438\u043d\u0434\u043e\u0432", 2), Pattern.compile("\\b\u0431\u0438\u043d\u0434", 2), Pattern.compile("\\bbind\\b", 2), Pattern.compile("\\[.*\\].*\\(.*\\)", 2), Pattern.compile("\\b\u0432\u043a\u043b\u044e\u0447\u0435\u043d\\b", 2), Pattern.compile("\\b\u0432\u044b\u043a\u043b\u044e\u0447\u0435\u043d\\b", 2), Pattern.compile("\\b\u043c\u043e\u0434\u0443\u043b\u044c\\b", 2), Pattern.compile("\\b\u0447\u0438\u0442\\b", 2)};

    public SafeModeModule() {
        super(class_2561.method_30163("Safe Mode"), class_2561.method_30163("\u041f\u043e\u043b\u043d\u0430\u044f \u043e\u0447\u0438\u0441\u0442\u043a\u0430 \u0441\u043b\u0435\u0434\u043e\u0432 \u0447\u0438\u0442\u0430 \u0438\u0437 \u0441\u0438\u0441\u0442\u0435\u043c\u044b"), Category.Misc);
        this.cleanLogs = new BooleanSetting(class_2561.method_30163("\u041e\u0447\u0438\u0441\u0442\u0438\u0442\u044c \u043b\u043e\u0433\u0438"), class_2561.method_30163("\u0423\u0434\u0430\u043b\u0438\u0442\u044c \u0432\u0441\u0435 \u0443\u043f\u043e\u043c\u0438\u043d\u0430\u043d\u0438\u044f \u0447\u0438\u0442\u0430 \u0438\u0437 latest.log"), SafeModeModule::lambda$new$0).set(true).register(this);
        this.deleteFearDLC = new BooleanSetting(class_2561.method_30163("\u0423\u0434\u0430\u043b\u0438\u0442\u044c \u043f\u0430\u043f\u043a\u0443 FearDLC"), class_2561.method_30163("\u041f\u043e\u043b\u043d\u043e\u0441\u0442\u044c\u044e \u0443\u0434\u0430\u043b\u0438\u0442\u044c \u043f\u0430\u043f\u043a\u0443 FearDLC"), SafeModeModule::lambda$new$1).set(true).register(this);
        this.disableAllModules = new BooleanSetting(class_2561.method_30163("\u041e\u0442\u043a\u043b\u044e\u0447\u0438\u0442\u044c \u0432\u0441\u0435 \u043c\u043e\u0434\u0443\u043b\u0438"), class_2561.method_30163("\u041e\u0442\u043a\u043b\u044e\u0447\u0438\u0442\u044c \u0432\u0441\u0435 \u0430\u043a\u0442\u0438\u0432\u043d\u044b\u0435 \u043c\u043e\u0434\u0443\u043b\u0438"), SafeModeModule::lambda$new$2).set(true).register(this);
        this.clearBinds = new BooleanSetting(class_2561.method_30163("\u041e\u0447\u0438\u0441\u0442\u0438\u0442\u044c \u0431\u0438\u043d\u0434\u044b"), class_2561.method_30163("\u0421\u0431\u0440\u043e\u0441\u0438\u0442\u044c \u0432\u0441\u0435 \u043a\u043b\u0430\u0432\u0438\u0448\u0438 \u043c\u043e\u0434\u0443\u043b\u0435\u0439"), SafeModeModule::lambda$new$3).set(true).register(this);
        this.blockClickGui = new BooleanSetting(class_2561.method_30163("\u0411\u043b\u043e\u043a\u0438\u0440\u043e\u0432\u0430\u0442\u044c ClickGui"), class_2561.method_30163("\u0417\u0430\u043a\u0440\u044b\u0442\u044c \u0438 \u0437\u0430\u0431\u043b\u043e\u043a\u0438\u0440\u043e\u0432\u0430\u0442\u044c \u043e\u0442\u043a\u0440\u044b\u0442\u0438\u0435 ClickGui"), SafeModeModule::lambda$new$4).set(true).register(this);
        this.clearChat = new BooleanSetting(class_2561.method_30163("\u041e\u0447\u0438\u0441\u0442\u0438\u0442\u044c \u0447\u0430\u0442"), class_2561.method_30163("\u0410\u0432\u0442\u043e\u043c\u0430\u0442\u0438\u0447\u0435\u0441\u043a\u0438 \u043e\u0447\u0438\u0441\u0442\u0438\u0442\u044c \u0432\u0435\u0441\u044c \u0447\u0430\u0442"), SafeModeModule::lambda$new$5).set(true).register(this);
    }

    public void activate() {
        super.activate();
        if (this.blockClickGui.getEnabled().booleanValue()) {
            clickGuiPermanentlyBlocked = true;
        }
        if (this.disableAllModules.getEnabled().booleanValue()) {
            cheatFullyDisabled = true;
        }
        try {
            this.executeSafeMode();
        }
        catch (Exception e) {
            e.printStackTrace();
            return;
        }
    }

    public static boolean isClickGuiPermanentlyBlocked() {
        return clickGuiPermanentlyBlocked;
    }

    public static boolean isCheatFullyDisabled() {
        return cheatFullyDisabled;
    }

    private void executeSafeMode() {
        if (this.blockClickGui.getEnabled().booleanValue()) {
            this.closeClickGui();
        }
        if (this.clearChat.getEnabled().booleanValue()) {
            this.clearChatHistory();
        }
        if (this.disableAllModules.getEnabled().booleanValue()) {
            this.disableAllActiveModules();
        }
        if (this.clearBinds.getEnabled().booleanValue()) {
            this.clearAllKeybinds();
        }
        if (this.cleanLogs.getEnabled().booleanValue()) {
            this.cleanMinecraftLogs();
        }
        if (this.cleanLogs.getEnabled().booleanValue()) {
            this.cleanAdditionalTraces();
        }
        if (this.deleteFearDLC.getEnabled().booleanValue()) {
            this.deleteFearDLCFolder();
        }
    }

    private void closeClickGui() {
        mc.execute(SafeModeModule::lambda$closeClickGui$6);
    }

    private void clearChatHistory() {
        try {
            if (mc.field_1705 != null) {
                if (mc.field_1705.method_1743() != null) {
                    mc.execute(SafeModeModule::lambda$clearChatHistory$7);
                }
            }
        }
        catch (Exception e1) {
            return;
        }
    }

    private void disableAllActiveModules() {
        List<ModuleLayer> modules = new ArrayList(FearDCP.getInstance().getModuleRepository().getModuleLayers());
        Field f = modules.iterator();
        while (f.hasNext()) {
            ModuleLayer module = f.next();
            if (!module.getEnabled().booleanValue()) continue;
            module.deactivate();
            Field f = ModuleLayer.class.getDeclaredField("enabled");
            f.setAccessible(true);
            f.set(module, false);
            this.resetModuleState(module);
        }
        f = ModuleLayer.class.getDeclaredField("enabled");
        f.setAccessible(true);
        f.set(this, false);
        this.deactivate();
    }

    private void resetModuleState(ModuleLayer module) {
        if (!module.getClass().getSimpleName().contains("AimAssist")) { /* goto L42; */ }
        Field f = module.getClass().getDeclaredField("lockedTarget");
        f.setAccessible(true);
        f.set(null, null);
    }

    private void clearAllKeybinds() {
        FearDCP.getInstance().getModuleRepository().getModuleLayers().forEach(SafeModeModule::lambda$clearAllKeybinds$8);
    }

    private void cleanMinecraftLogs() {
        try {
            String[] tmp0 = new String[1];
            tmp0[0] = "logs";
            Path logsDir = Paths.get(mc.field_1697.getAbsolutePath(), tmp0);
            if (!Files.exists(logsDir, new LinkOption[0])) {
                return;
            }
        }
        catch (Exception logsDir) {
            return;
        }
        try {
            Path latestLog = logsDir.resolve("latest.log");
            if (Files.exists(latestLog, new LinkOption[0])) {
                this.cleanLogFile(latestLog);
            }
        }
        catch (Exception logsDir) {
            return;
        }
    }

    private void cleanLogFile(Path logFile) throws IOException {
        List<String> originalLines = Files.readAllLines(logFile, StandardCharsets.UTF_8);
        List<String> finalLines = new ArrayList();
        for (int i = 0; i < originalLines.size(); i++) {
            String line = originalLines.get(i);
            if (line != null) {
                line = line.replace("\r", "");
                if (!(line.strip().isEmpty())) {
                    boolean isChatLine = line.contains("[CHAT]");
                    boolean shouldRemove = false;
                    boolean emptyChat = GLOBAL_PATTERNS;
                    boolean orphanedTimestamp = emptyChat.length;
                    boolean garbage = false;
                    while (garbage < orphanedTimestamp) {
                        Pattern p = emptyChat[garbage];
                        if (p.matcher(line).find()) {
                            shouldRemove = true;
                        } else {
                            garbage++;
                        }
                    }
                    if ((shouldRemove) && isChatLine) continue;
                    emptyChat = CHAT_ONLY_PATTERNS;
                    orphanedTimestamp = emptyChat.length;
                    garbage = false;
                    while (garbage < orphanedTimestamp) {
                        Pattern p = emptyChat[garbage];
                        if (p.matcher(line).find()) {
                            shouldRemove = true;
                        } else {
                            garbage++;
                        }
                    }
                    if (!shouldRemove) {
                        if (isChatLine) {
                            if (!this.containsDotCommand(line)) continue;
                            shouldRemove = true;
                        }
                    }
                    if ((shouldRemove)) continue;
                    emptyChat = line.matches("^.*\\[CHAT\\]\\s*$");
                    orphanedTimestamp = line.matches("^\\[\\d{2}:\\d{2}:\\d{2}\\]\\s*\\[.*?\\]:\\s*$");
                    garbage = line.matches(".*\\]\\s*:\\s*$") || line.matches(".*\\\\n\\s*$");
                    if (emptyChat || orphanedTimestamp || garbage) {
                        shouldRemove = true;
                    }
                    if (shouldRemove) {
                        if (isChatLine) {
                        }
                    }
                    if (i + 1 >= originalLines.size()) { /* goto @420; */ }
                    String next = originalLines.get(i + 1);
                    while (next == null) {
                        i++;
                    }
                    next = next.replace("\r", "");
                    if (!next.strip().isEmpty()) {
                        if (!next.startsWith(" ")) {
                            if (next.startsWith("\t")) { /* goto @405; */ }
                        }
                    }
                    i++;
                    /* goto L309; */
                    finalLines.add(line);
                }
            }
        }
        OpenOption[] tmp0 = new OpenOption[2];
        tmp0[0] = StandardOpenOption.TRUNCATE_EXISTING;
        tmp0[1] = StandardOpenOption.WRITE;
        Files.write(logFile, finalLines, StandardCharsets.UTF_8, tmp0);
    }

    private boolean containsDotCommand(String line) {
        if (!line.contains("[CHAT]")) {
            return false;
        }
        String chat = line.replaceAll("^.*\\[CHAT\\]\\s*", "").trim();
        return chat.matches("^\\.[a-zA-Z][a-zA-Z0-9_]*.*");
    }

    private void cleanAdditionalTraces() {
        try {
            this.cleanCrashReports();
            this.cleanOptionsFile();
        }
        catch (Exception e1) {
            return;
        }
    }

    private void cleanCrashReports() {
        String[] tmp0 = new String[1];
        tmp0[0] = "crash-reports";
        Path crashDir = Paths.get(mc.field_1697.getAbsolutePath(), tmp0);
        if (!Files.exists(crashDir, new LinkOption[0])) { /* goto L123; */ }
        DirectoryStream<Path> stream = Files.newDirectoryStream(crashDir, "*.txt");
        for (Path f : stream) {
            if (!this.containsCheatReferences(f)) continue;
            Files.delete(f);
        }
        if (stream != null) {
            stream.close();
        }
    }

    private boolean containsCheatReferences(Path file) {
        BufferedReader reader = Files.newBufferedReader(file);
        String tmp0 = reader.readLine();
        String line = tmp0;
        if (tmp0 != null) {
            for (Pattern p : GLOBAL_PATTERNS) {
                if (p.matcher(line).find()) {
                    int var8 = 1;
                    if (reader == null) continue;
                    reader.close();
                    return var8;
                }
            }
        }
        if (this.containsDotCommand(line)) {
            var4 = 1;
            if (reader != null) {
                reader.close();
            }
            return var4;
        }
        if (reader != null) {
            reader.close();
        }
        return false;
    }

    private void cleanOptionsFile() {
        String[] tmp0 = new String[1];
        tmp0[0] = "options.txt";
        Path optionsFile = Paths.get(mc.field_1697.getAbsolutePath(), tmp0);
        if (!Files.exists(optionsFile, new LinkOption[0])) {
            return;
        }
        List<String> cleaned = new ArrayList();
        boolean modified = false;
        BufferedReader reader = Files.newBufferedReader(optionsFile);
        String tmp1 = reader.readLine();
        String line = tmp1;
        if (tmp1 != null) {
            while (line.startsWith("key_")) {
                if (!line.contains("fear") || !(line.contains("dlc"))) continue;
                modified = true;
            }
            cleaned.add(line);
        }
        if (reader != null) {
            reader.close();
        }
        if (!modified) { /* goto L262; */ }
        BufferedWriter writer = Files.newBufferedWriter(optionsFile, new OpenOption[0]);
        for (String line : cleaned) {
            writer.write(line);
            writer.newLine();
        }
        if (writer != null) {
            writer.close();
        }
    }

    private void deleteFearDLCFolder() {
        try {
            String[] tmp0 = new String[1];
            tmp0[0] = "rivalvisual";
            Path dir = Paths.get(mc.field_1697.getAbsolutePath(), tmp0);
            if (Files.exists(dir, new LinkOption[0])) {
                this.deleteDirectoryRecursively(dir);
            }
        }
        catch (Exception dir) {
            return;
        }
    }

    private void deleteDirectoryRecursively(Path directory) throws IOException {
        if (Files.isDirectory(directory, new LinkOption[0])) {
            DirectoryStream<Path> entries = Files.newDirectoryStream(directory);
        }
        try {
            for (Path entry : entries) {
                this.deleteDirectoryRecursively(entry);
            }
        }
        catch (Throwable e3) {
            if (entries != null) {
                entries.close();
            }
            throw var3;
        }
        try {
            entries.close();
        }
        catch (Throwable entry) {
            var3.addSuppressed(entry);
            throw var3;
        }
        throw var3;
        Files.delete(directory);
    }
}
