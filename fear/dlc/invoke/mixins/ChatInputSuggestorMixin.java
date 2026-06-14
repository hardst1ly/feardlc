/*
 * Decompiled with https://jar.tools
 */
package fear.dlc.invoke.mixins;

import com.mojang.brigadier.ParseResults;
import com.mojang.brigadier.context.StringRange;
import com.mojang.brigadier.suggestion.Suggestion;
import com.mojang.brigadier.suggestion.Suggestions;
import fear.dlc.api.commands.ArgumentLayer;
import fear.dlc.api.commands.CommandLayer;
import fear.dlc.api.commands.CommandsRepository;
import fear.dlc.modules.impl.misc.SafeModeModule;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.CompletableFuture;
import net.minecraft.class_342;
import net.minecraft.class_4717;
import net.minecraft.class_5481;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value={class_4717.class})
public abstract class ChatInputSuggestorMixin {
    @Shadow
    @Final
    class_342 field_21599;
    @Shadow
    @Final
    private List<class_5481> field_21607;
    @Shadow
    private ParseResults<?> field_21610;
    @Shadow
    private CompletableFuture<Suggestions> field_21611;
    @Shadow
    private class_4717.class_464 field_21612;
    @Shadow
    boolean field_21614;

    @Shadow
    public abstract void method_23920(boolean var1);

    @Inject(method={"refresh"}, at={@At(value="HEAD")}, cancellable=true)
    private void fearDlc$refresh(CallbackInfo ci) {
        if (SafeModeModule.isCheatFullyDisabled()) {
            return;
        }
        String fullText = this.field_21599.method_1882();
        int cursor = this.field_21599.method_1881();
        if (cursor < 0 || cursor > fullText.length()) {
            return;
        }
        String prefix = fullText.substring(0, cursor);
        if (!prefix.startsWith(".")) {
            return;
        }
        String afterDot = prefix.substring(1);
        if (this.field_21614) {
            return;
        }
        int firstSpace = afterDot.indexOf(32);
        if (firstSpace < 0) {
            String currentToken = afterDot;
            int start = 1;
            int end = prefix.length();
            StringRange replaceRange = StringRange.between(start, end);
            List<Suggestion> suggestionList = this.buildCommandSuggestions(currentToken, replaceRange);
        } else {
            String commandName = afterDot.substring(0, firstSpace);
            String argsPart = afterDot.substring(firstSpace + 1);
            int lastSpace = argsPart.lastIndexOf(32);
            String currentArg = lastSpace < 0 ? argsPart : argsPart.substring(lastSpace + 1);
            int argStartInPrefix = prefix.length() - currentArg.length();
            StringRange replaceRange = StringRange.between(argStartInPrefix, prefix.length());
            List<Suggestion> suggestionList = this.buildArgumentSuggestions(commandName, currentArg, replaceRange);
        }
        if (suggestionList.isEmpty()) {
            return;
        }
        ci.cancel();
        this.field_21610 = null;
        this.field_21599.method_1887(null);
        this.field_21612 = null;
        this.field_21607.clear();
        Suggestions suggestions = new Suggestions(replaceRange, suggestionList);
        this.field_21611 = CompletableFuture.completedFuture(suggestions);
        this.method_23920(true);
    }

    private List<Suggestion> buildCommandSuggestions(String currentToken, StringRange range) {
        String tokenLower = currentToken.toLowerCase(Locale.ROOT);
        List<Suggestion> result = new ArrayList();
        for (CommandLayer layer : CommandsRepository.getCommandLayer()) {
            for (String name : layer.getCommands()) {
                if (!name.toLowerCase(Locale.ROOT).startsWith(tokenLower)) continue;
                result.add(new Suggestion(range, name));
            }
        }
        return result;
    }

    private List<Suggestion> buildArgumentSuggestions(String commandName, String currentArg, StringRange range) {
        String argLower = currentArg.toLowerCase(Locale.ROOT);
        List<Suggestion> result = new ArrayList();
        Iterator var6 = CommandsRepository.getCommandLayer().iterator();
        if (!var6.hasNext()) return result;
        CommandLayer layer = var6.next();
        boolean match = false;
        for (String alias : layer.getCommands()) {
            if (!alias.equalsIgnoreCase(commandName)) continue;
            match = true;
            break;
        }
        while (!match) {
        }
        for (ArgumentLayer arg : layer.getArguments()) {
            String name = arg.getArgument();
            if (name != null) {
                if (!name.toLowerCase(Locale.ROOT).startsWith(argLower)) continue;
                result.add(new Suggestion(range, name));
            }
        }
        return result;
    }
}
