package me.zephi.waterguns.register.command;

import com.google.common.collect.Lists;
import me.zephi.waterguns.register.Handler;
import me.zephi.waterguns.register.RegisterHandler;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public abstract class BasicCommand extends Command {
    private RegisterHandler handler;
    private final List<Command> commands = Lists.newArrayList();

    public BasicCommand(String name) {
        this(name, "A basic command");
    }

    public BasicCommand(String name, String description) {
        this(name, description, "/<command>");
    }

    public BasicCommand(String name, String description, String usageMessage) {
        this(name, description, usageMessage, Collections.emptyList());
    }

    public BasicCommand(String name, String description, String usageMessage, List<String> aliases) {
        this(name, description, usageMessage, aliases, null);
    }

    public BasicCommand(String name, String description, String usageMessage, List<String> aliases, String permission) {
        super(name, description, usageMessage, aliases);

        setPermission(permission);
    }

    public void setHandler(RegisterHandler handler) {
        this.handler = handler;
    }

    public Plugin plugin() {
        return handler.getPlugin();
    }

    public<T> T plugin(Class<T> type) {
        return type.cast(plugin());
    }

    public<T extends Handler> T handler(Class<T> type) {
        return handler.getHandler(type);
    }

    protected void registerCommand(Command command) {
        commands.add(command);
    }

    public String getUsage(String label) {
        return usageMessage.replace("<command>", label);
    }

    @Override
    public boolean execute(CommandSender sender, String commandLabel, String[] args) {
        if (!testPermission(sender))
            return true;

        if (args.length > 0) {
            String name = args[0];
            Command sub = null;

            for (Command command : commands) {
                if (!command.getName().equals(name) && !command.getAliases().contains(name))
                    continue;

                sub = command;
                break;
            }

            if (sub != null)
                return sub.execute(sender, name, Arrays.copyOfRange(args, 1, args.length));
        }

        CommandReader reader = new CommandReader(sender, commandLabel, args);
        onCommand(reader).action(this, sender, commandLabel);

        return true;
    }

    public abstract CommandReturn onCommand(CommandReader reader);
}
