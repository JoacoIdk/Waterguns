package me.zephi.waterguns.util;

import com.google.common.collect.Lists;
import org.bukkit.command.CommandSender;

import java.util.List;

public class BukkitMessage {
    private final StringBuilder message;
    private final List<CommandSender> receivers;

    public BukkitMessage() {
        this.message = new StringBuilder();
        this.receivers = Lists.newArrayList();
    }

    public BukkitMessage(String text) {
        this.message = new StringBuilder(text);
        this.receivers = Lists.newArrayList();
    }

    public BukkitMessage(String text, List<CommandSender> receivers) {
        this.message = new StringBuilder(text);
        this.receivers = receivers;
    }

    public void addLine(String text, Object... format) {
        message.append('\n').append(String.format(text, format));
    }

    public void addText(String text, Object... format) {
        message.append(String.format(text, format));
    }

    public void addReceiver(CommandSender sender) {
        receivers.add(sender);
    }

    public void send() {
        String builtMessage = message.toString();

        for (CommandSender receiver : receivers)
            receiver.sendMessage(builtMessage);
    }
}
