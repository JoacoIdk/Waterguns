package me.zephi.waterguns.features.commands;

import me.zephi.waterguns.register.command.BasicCommand;
import me.zephi.waterguns.register.command.CommandReader;
import me.zephi.waterguns.register.command.CommandReturn;
import me.zephi.waterguns.util.BukkitMessage;
import me.zephi.waterguns.util.CC;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.stream.Collectors;

public class ListCommand extends BasicCommand {
    public ListCommand() {
        super("list", "Lists all players");
    }

    @Override
    public CommandReturn onCommand(CommandReader reader) {
        CommandSender sender = reader.getSender();
        BukkitMessage message = new BukkitMessage();
        message.addReceiver(sender);

        List<String> players = Bukkit.getOnlinePlayers().stream().map(Player::getDisplayName).collect(Collectors.toList());
        message.addText(CC.GREEN + "Online Players [%s]: ", players.size());
        message.addText(CC.RESET + String.join(", ", players));

        message.send();

        return CommandReturn.DEFAULT;
    }
}
