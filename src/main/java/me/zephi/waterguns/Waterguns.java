package me.zephi.waterguns;

import lombok.Getter;
import me.zephi.waterguns.features.commands.ListCommand;
import me.zephi.waterguns.features.commands.WatergunsCommand;
import me.zephi.waterguns.features.listeners.SleepListener;
import me.zephi.waterguns.features.listeners.TreeBreakListener;
import me.zephi.waterguns.features.mod.ModHandler;
import me.zephi.waterguns.register.RegisterHandler;
import me.zephi.waterguns.features.tasks.AutosaveTask;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
public final class Waterguns extends JavaPlugin {
    private RegisterHandler handler;

    @Override
    public void onEnable() {
        handler = new RegisterHandler(this);

        handler.registerHandler(new ModHandler());
        handler.registerCommand(new ListCommand());
        handler.registerCommand(new WatergunsCommand());
        handler.registerListener(new SleepListener());
        handler.registerListener(new TreeBreakListener());
        handler.registerTask(new AutosaveTask());

        handler.activate();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
