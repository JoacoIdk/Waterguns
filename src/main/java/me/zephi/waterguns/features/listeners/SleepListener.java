package me.zephi.waterguns.features.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerBedEnterEvent;
import org.bukkit.util.Vector;

public class SleepListener implements Listener {
    @EventHandler
    public void onPlayerBedEnter(PlayerBedEnterEvent e) {
        e.getPlayer().setVelocity(new Vector(0, 0, 0));
    }
}
