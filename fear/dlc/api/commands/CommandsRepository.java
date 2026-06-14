/*
 * Decompiled with https://jar.tools
 */
package fear.dlc.api.commands;

import fear.dlc.api.commands.CommandLayer;
import fear.dlc.api.commands.list.bind.BindLayerCommand;
import fear.dlc.api.commands.list.config.ConfigLayerCommand;
import fear.dlc.api.commands.list.friend.FriendLayerCommand;
import fear.dlc.api.commands.list.gps.GpsLayerCommand;
import fear.dlc.api.commands.list.help.HelpLayerCommand;
import fear.dlc.api.commands.list.record.RecordLayerCommand;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class CommandsRepository {
    private static final List<CommandLayer> commandLayer = new CopyOnWriteArrayList();

    public static List<CommandLayer> getCommandLayer() {
        return commandLayer;
    }

    static {
        commandLayer.addAll(List.of(new ConfigLayerCommand(), new HelpLayerCommand(), new FriendLayerCommand(), new GpsLayerCommand(), new RecordLayerCommand(), new BindLayerCommand()));
    }
}
