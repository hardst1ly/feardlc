/*
 * Decompiled with https://jar.tools
 */
package fear.dlc.modules.more;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.FileAttribute;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import net.minecraft.class_310;

public class FriendManager {
    private static final String DIR_NAME = "rivalvisual";
    private static final String FILE_NAME = "friends.txt";
    private static final List<String> friends = new ArrayList();
    private static Path filePath;
    private static boolean initialized = false;

    public static synchronized void init() {
        if (initialized) {
            return;
        }
        filePath = FriendManager.resolveFilePath();
        try {
            Files.createDirectories(filePath.getParent(), new FileAttribute[0]);
            if (!Files.exists(filePath, new LinkOption[0])) {
                Files.createFile(filePath, new FileAttribute[0]);
            }
        }
        catch (IOException e) {
            System.err.println("[FriendManager] \u041d\u0435 \u0443\u0434\u0430\u043b\u043e\u0441\u044c \u0441\u043e\u0437\u0434\u0430\u0442\u044c " + String.valueOf(filePath) + ": " + e.getMessage());
            FriendManager.loadFriends();
            initialized = true;
            return;
        }
        FriendManager.loadFriends();
        initialized = true;
    }

    private static Path resolveFilePath() {
        class_310 mc = class_310.method_1551();
        File runDir = mc != null ? mc.field_1697 : null;
        if (runDir != null) {
            String[] tmp0 = new String[2];
            tmp0[0] = "rivalvisual";
            tmp0[1] = "friends.txt";
            return Paths.get(runDir.getAbsolutePath(), tmp0);
        }
        String[] tmp1 = new String[1];
        tmp1[0] = "friends.txt";
        return Paths.get("rivalvisual", tmp1);
    }

    private static void ensureInit() {
        if (!initialized) {
            FriendManager.init();
        }
    }

    public static synchronized void loadFriends() {
        if (filePath == null) {
            filePath = FriendManager.resolveFilePath();
        }
        friends.clear();
        File file = filePath.toFile();
        if (!file.exists()) {
            return;
        }
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String tmp0 = reader.readLine();
        String line = tmp0;
        if (tmp0 != null) {
            String name = line.trim();
            if (name.isEmpty()) continue;
            while (name.startsWith("#")) {
            }
            String normalized = name.toLowerCase(Locale.ROOT);
            if (friends.contains(normalized)) continue;
            friends.add(normalized);
        }
        reader.close();
    }

    public static synchronized void saveFriends() {
        if (filePath == null) {
            filePath = FriendManager.resolveFilePath();
        }
        Files.createDirectories(filePath.getParent(), new FileAttribute[0]);
        BufferedWriter writer = new BufferedWriter(new FileWriter(filePath.toFile()));
        for (String friend : friends) {
            writer.write(friend);
            writer.newLine();
        }
        writer.close();
    }

    public static synchronized void addFriend(String name) {
        if (name == null) {
            return;
        }
        FriendManager.ensureInit();
        String normalized = name.trim().toLowerCase(Locale.ROOT);
        if (normalized.isEmpty()) {
            return;
        }
        if (friends.contains(normalized)) {
            return;
        }
        friends.add(normalized);
        FriendManager.saveFriends();
    }

    public static synchronized void deleteFriend(String name) {
        if (name == null) {
            return;
        }
        FriendManager.ensureInit();
        String normalized = name.trim().toLowerCase(Locale.ROOT);
        if (friends.remove(normalized)) {
            FriendManager.saveFriends();
        }
    }

    public static synchronized void clearFriends() {
        FriendManager.ensureInit();
        friends.clear();
        FriendManager.saveFriends();
    }

    public static List<String> getFriends() {
        FriendManager.ensureInit();
        return Collections.unmodifiableList(new ArrayList(friends));
    }

    public static boolean isFriend(String playerName) {
        if (playerName == null) {
            return false;
        }
        FriendManager.ensureInit();
        return friends.contains(playerName.trim().toLowerCase(Locale.ROOT));
    }

    public static int getFriendCount() {
        FriendManager.ensureInit();
        return friends.size();
    }
}
