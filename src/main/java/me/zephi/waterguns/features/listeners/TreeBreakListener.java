package me.zephi.waterguns.features.listeners;

import me.zephi.waterguns.util.BukkitUtil;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class TreeBreakListener implements Listener {
    @EventHandler
    public void onBlockBreak(BlockBreakEvent e) {
        Player p = e.getPlayer();

        if (!p.isSneaking())
            return;

        if (!p.getItemInHand().getType().name().contains("AXE") || !e.getBlock().getType().name().contains("LOG"))
            return;

        for (Block b : BukkitUtil.getTreeBlocks(e.getBlock()))
            b.breakNaturally(p.getItemInHand());
    }
}
