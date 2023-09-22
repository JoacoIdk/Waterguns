package me.zephi.waterguns.features.tasks;

import me.zephi.waterguns.register.RunTask;
import me.zephi.waterguns.util.CC;
import org.bukkit.Bukkit;

public class AutosaveTask implements RunTask {
    @Override
    public boolean repeat() {
        return true;
    }

    @Override
    public int ticks() {
        return 20 * 60 * 5; // 5 Minutes
    }

    @Override
    public void task() {
        Bukkit.broadcastMessage(CC.ORANGE + "Saved all player data.");
        Bukkit.savePlayers();
    }
}
