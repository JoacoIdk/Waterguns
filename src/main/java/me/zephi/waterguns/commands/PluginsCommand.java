package me.zephi.waterguns.commands;

import com.google.common.collect.Lists;
import me.zephi.waterguns.register.command.BasicCommand;
import me.zephi.waterguns.register.command.CommandReader;
import me.zephi.waterguns.register.command.CommandReturn;
import me.zephi.waterguns.util.BukkitUtil;
import me.zephi.waterguns.util.CC;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;

public class PluginsCommand extends BasicCommand {
    public PluginsCommand() {
        super("plugins", "List all plugins", "/<command>", Lists.newArrayList("pl"));
    }

    @Override
    public CommandReturn onCommand(CommandReader reader) {
        CommandSender sender = reader.getSender();

        PluginManager pluginManager = Bukkit.getPluginManager();
        Plugin[] plugins = pluginManager.getPlugins();

        BukkitUtil.sendCenteredMessage(sender, CC.GRAY + CC.LINE);
        BukkitUtil.sendCenteredMessage(sender, CC.GREEN + "Plugins (%s):", plugins.length);

        for (Plugin plugin : plugins)
            sender.sendMessage((plugin.isEnabled() ? CC.GREEN : CC.RED) + "- " + plugin.getName());

        BukkitUtil.sendCenteredMessage(sender, CC.GRAY + CC.LINE);

        return CommandReturn.DEFAULT;
    }
}
