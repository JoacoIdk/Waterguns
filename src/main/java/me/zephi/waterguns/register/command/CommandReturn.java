package me.zephi.waterguns.register.command;

import me.zephi.waterguns.util.CC;
import org.bukkit.command.CommandSender;

public interface CommandReturn {
    CommandReturn NOT_PLAYER = (command, sender, label) -> sender.sendMessage(CC.RED + "This command can only be executed by players.");
    CommandReturn USAGE = (command, sender, label) -> sender.sendMessage(CC.RED + "Usage: " + command.getUsage(label));
    CommandReturn DEFAULT = (command, sender, label) -> {};

    void action(BasicCommand command, CommandSender sender, String label);
}
