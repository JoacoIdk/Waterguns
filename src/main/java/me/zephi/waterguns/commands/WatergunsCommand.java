package me.zephi.waterguns.commands;

import com.google.common.collect.Lists;
import me.zephi.waterguns.register.command.BasicCommand;
import me.zephi.waterguns.register.command.CommandReader;
import me.zephi.waterguns.register.command.CommandReturn;
import me.zephi.waterguns.util.CC;
import org.bukkit.command.CommandSender;

public class WatergunsCommand extends BasicCommand {
    public WatergunsCommand() {
        super("waterguns", "Waterguns default command", "/<command>", Lists.newArrayList("wg"));
    }

    @Override
    public CommandReturn onCommand(CommandReader reader) {
        CommandSender sender = reader.getSender();

        sender.sendMessage(CC.GRAY + CC.LINE);
        sender.sendMessage(CC.GREEN + "This server is using Waterguns.");
        sender.sendMessage(CC.GRAY + CC.LINE);

        return CommandReturn.DEFAULT;
    }
}
