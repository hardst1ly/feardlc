/*
 * Decompiled with https://jar.tools
 */
package fear.dlc.api.commands;

import com.google.common.eventbus.Subscribe;
import fear.dlc.FearDCP;
import fear.dlc.api.commands.CommandsRepository;
import fear.dlc.api.events.impl.PacketEvent;
import fear.dlc.modules.impl.misc.SafeModeModule;
import java.util.Arrays;
import java.util.List;
import net.minecraft.class_2797;

public class CommandsListener {
    public CommandsListener() {
        FearDCP.getInstance().getEventBus().register(this);
    }

    @Subscribe
    public void listener(PacketEvent packetEvent) {
        String message = packetEvent.getPacket();
        if (message instanceof class_2797) {
            class_2797 packet = message;
        } else {
            return;
        }
        message = packet.comp_945().trim();
        if (!message.startsWith(".")) {
            return;
        }
        if (SafeModeModule.isCheatFullyDisabled()) {
            packetEvent.cancel();
            return;
        }
        SafeModeModule safeMode = FearDCP.getInstance().getModuleRepository().find(SafeModeModule.class);
        packetEvent.cancel();
        if (safeMode != null && safeMode.getEnabled().booleanValue()) {
            return;
        }
        packetEvent.cancel();
        List<String> args = Arrays.asList(message.substring(1).split("\\s+"));
        if (args.isEmpty()) {
            return;
        }
        String commandName = args.getFirst();
        CommandsRepository.getCommandLayer().stream().filter(CommandsListener::lambda$listener$1 /* captured: commandName */).findFirst().ifPresent(CommandsListener::lambda$listener$2 /* captured: args */);
    }
}
