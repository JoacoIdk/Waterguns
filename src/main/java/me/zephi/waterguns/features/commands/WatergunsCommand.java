package me.zephi.waterguns.features.commands;

import com.google.common.collect.Lists;
import me.zephi.waterguns.register.command.BasicCommand;
import me.zephi.waterguns.register.command.CommandReader;
import me.zephi.waterguns.register.command.CommandReturn;
import me.zephi.waterguns.util.BukkitMessage;
import me.zephi.waterguns.util.CC;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.PluginDescriptionFile;

public class WatergunsCommand extends BasicCommand {
    public WatergunsCommand() {
        super("waterguns", "Waterguns default command", "/<command>", Lists.newArrayList("wg"));
    }

    @Override
    public CommandReturn onCommand(CommandReader reader) {
        CommandSender sender = reader.getSender();

        BukkitMessage message = new BukkitMessage();
        message.addReceiver(sender);

        PluginDescriptionFile description = plugin().getDescription();
        String name = description.getName();
        String version = description.getVersion();
        String website = description.getWebsite();
        String authors = String.join(", ", description.getAuthors());

        message.addLine(CC.GRAY + CC.LINE);
        message.addLine(CC.GREEN + "Waterguns plugin");

        if (!name.equals("Waterguns"))
            message.addLine(CC.RED + "(Plugin Description File has been altered, version info may not be accurate)");

        message.addLine(CC.EMPTY);
        message.addLine(CC.GREEN + "Version: %s", version);
        message.addLine(CC.GREEN + "Website: %s", website);
        message.addLine(CC.GREEN + "Author(s): %s", authors);
        message.addLine(CC.GRAY + CC.LINE);

        message.send();

        return CommandReturn.DEFAULT;
    }
}
