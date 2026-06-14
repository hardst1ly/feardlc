/*
 * Decompiled with https://jar.tools
 */
package fear.dlc.utility.render.providers;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.stream.Collectors;
import net.minecraft.class_2960;
import net.minecraft.class_310;
import net.minecraft.class_3300;

public final class ResourceProvider {
    private static final Gson GSON = new Gson();

    private static class_3300 getResourceManager() {
        class_310 client = class_310.method_1551();
        if (client == null) {
            throw new IllegalStateException("MinecraftClient is not initialized yet");
        }
        return client.method_1478();
    }

    public static class_2960 getShaderIdentifier(String name) {
        return class_2960.method_60655("pasxalka", "core/" + name);
    }

    public static JsonObject toJson(class_2960 identifier) {
        return JsonParser.parseString(ResourceProvider.toString(identifier)).getAsJsonObject();
    }

    public static <T> T fromJsonToInstance(class_2960 identifier, Class<T> clazz) {
        return GSON.fromJson(ResourceProvider.toString(identifier), clazz);
    }

    public static String toString(class_2960 identifier) {
        return ResourceProvider.toString(identifier, "\n");
    }

    public static String toString(class_2960 identifier, String delimiter) {
        InputStream inputStream = ResourceProvider.getResourceManager().open(identifier);
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        String var4 = reader.lines().collect(Collectors.joining(delimiter));
        reader.close();
        if (inputStream != null) {
            inputStream.close();
        }
        return var4;
        throw var4;
        throw reader;
    }
}
