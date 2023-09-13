package me.zephi.waterguns;

import lombok.Getter;
import me.zephi.waterguns.commands.PluginsCommand;
import me.zephi.waterguns.register.RegisterHandler;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
public final class Waterguns extends JavaPlugin {
    private RegisterHandler handler;

    @Override
    public void onEnable() {
        handler = new RegisterHandler(this);

        handler.registerCommand(new PluginsCommand());

        handler.activate();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
