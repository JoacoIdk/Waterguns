package me.zephi.waterguns.register;

import com.google.common.collect.Lists;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.command.Command;
import org.bukkit.command.CommandMap;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.scheduler.BukkitScheduler;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

// Simple class to register commands & listeners
public class RegisterHandler {
    private final Plugin plugin;
    private final List<Command> commands = Lists.newArrayList();
    private final List<Listener> listeners = Lists.newArrayList();
    private final List<RunTask> tasks = Lists.newArrayList();

    public RegisterHandler(Plugin plugin) {
        this.plugin = plugin;
    }

    // Slooooooow
    public void registerAll(Object object) {
        if (object instanceof Command)
            registerCommand((Command) object);

        if (object instanceof Listener)
            registerListener((Listener) object);

        if (object instanceof RunTask)
            registerTask((RunTask) object);
    }

    public void registerCommand(Command command) {
        commands.add(command);
    }

    public void registerListener(Listener listener) {
        listeners.add(listener);
    }

    public void registerTask(RunTask task) {
        tasks.add(task);
    }

    public void activate() {
        try {
            // Register commands

            PluginManager pluginManager = Bukkit.getPluginManager();
            Class<?> pluginManagerClass = pluginManager.getClass();
            Field field = pluginManagerClass.getDeclaredField("commandMap");

            field.setAccessible(true);

            CommandMap commandMap = (CommandMap) field.get(pluginManager);

            for (Command command : commands) {
                Command check = commandMap.getCommand(command.getName());

                if (check != null)
                    check.unregister(commandMap);

                commandMap.register(plugin.getName(), command);
            }

            // Make commands tab-completable

            Server server = Bukkit.getServer();
            Class<?> serverClass = server.getClass();

            Method method = serverClass.getDeclaredMethod("syncCommands");

            method.setAccessible(true);
            method.invoke(server);

            // Register listeners

            for (Listener listener : listeners)
                pluginManager.registerEvents(listener, plugin);

            // Register tasks

            BukkitScheduler scheduler = Bukkit.getScheduler();

            for (RunTask task : tasks) {
                if (task.repeat())
                    scheduler.runTaskTimer(plugin, task::task, 0, task.ticks());
                else
                    scheduler.runTaskLater(plugin, task::task, task.ticks());
            }
        } catch (NoSuchFieldException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }
}
