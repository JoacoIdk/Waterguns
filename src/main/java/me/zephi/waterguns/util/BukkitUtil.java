package me.zephi.waterguns.util;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class BukkitUtil {
    private final static int CENTER_PX = 154;

    public static String getCenteredMessage(String message, Object... format) {
        if (message == null || message.isEmpty())
            return "";

        message = ChatColor.translateAlternateColorCodes('&', message);
        message = String.format(message, format);

        int messagePxSize = 0;
        boolean previousCode = false;
        boolean isBold = false;

        for (char c : message.toCharArray()){
            if (c == '§'){
                previousCode = true;
            } else if (previousCode) {
                previousCode = false;
                isBold = c == 'l' || c == 'L';
            } else {
                DefaultFontInfo dFI = DefaultFontInfo.getDefaultFontInfo(c);
                messagePxSize += isBold ? dFI.getBoldLength() : dFI.getLength();
                messagePxSize++;
            }
        }

        int halvedMessageSize = messagePxSize / 2;
        int toCompensate = CENTER_PX - halvedMessageSize;
        int spaceLength = DefaultFontInfo.SPACE.getLength() + 1;
        int compensated = 0;

        StringBuilder sb = new StringBuilder();

        while(compensated < toCompensate){
            sb.append(" ");
            compensated += spaceLength;
        }

        return sb + message;
    }

    public static void broadcastCenteredMessage(String message, Object... format){
        Bukkit.broadcastMessage(getCenteredMessage(message, format));
    }

    public static void sendCenteredMessage(CommandSender sender, String message, Object... format){
        sender.sendMessage(getCenteredMessage(message, format));
    }

    public static ConsoleCommandSender getConsole() {
        return Bukkit.getConsoleSender();
    }

    public static boolean locationEquals(Location loc1, Location loc2) {
        if (loc1 == null || loc2 == null)
            return false;

        return loc1.getBlockX() == loc2.getBlockX() &&
               loc1.getBlockY() == loc2.getBlockY() &&
               loc1.getBlockZ() == loc2.getBlockZ();
    }

    public static void kickAll(String message) {
        for (Player t : Bukkit.getOnlinePlayers())
            t.kickPlayer(message);
    }

    public static List<Block> getSurroundingBlocks(Block start, int radius) {
        if (radius < 0)
            return new ArrayList<>(0);

        int iterations = 2 * radius + 1;
        List<Block> blocks = new ArrayList<>(iterations * iterations * iterations);

        for (int x = -radius; x <= radius; x++) {
            for (int y = -radius; y <= radius; y++) {
                for (int z = -radius; z <= radius; z++) {
                    blocks.add(start.getRelative(x, y, z));
                }
            }
        }

        return blocks;
    }

    public static int getOnlinePlayerCount(String permission) {
        return (int) Bukkit.getOnlinePlayers()
                .stream()
                .filter((p) -> p.hasPermission(permission))
                .count();
    }

    public static List<Block> getTreeBlocks(Block base) {
        List<Block> blocks = new ArrayList<>();

        getTreeBlocks(base.getType(), blocks, base, true);

        return blocks;
    }

    public static void getTreeBlocks(Material type, List<Block> logs, Block base, boolean prevFound) {
        boolean found = false;

        if (base.getType() == type && !logs.contains(base)) {
            logs.add(base);
            found = true;
        }

        if (found)
            for (BlockFace dir : BlockFace.values())
                getTreeBlocks(type, logs, base.getRelative(dir), true);

        if (found || prevFound) {
            getTreeBlocks(type, logs, base.getRelative(BlockFace.UP), found);
            getTreeBlocks(type, logs, base.getRelative(BlockFace.DOWN), found);
        }
    }

    // All code from here was taken from EssentialsX

    // Calculate amount of EXP needed to level up
    public static int getExpToLevelUp(int level){
        if (level <= 15)
            return 2 * level + 7;

        if (level <= 30)
            return 5 * level - 38;

        return 9 * level - 158;
    }

    // Calculate total experience up to a level
    public static int getExpAtLevel(int level){
        if (level <= 16)
            return (int) (Math.pow(level, 2) + 6 * level);

        if (level <= 31)
            return (int) (2.5 * Math.pow(level, 2) - 40.5 * level + 360.0);

        return (int) (4.5 * Math.pow(level, 2) - 162.5 * level + 2220.0);
    }

    // Calculate player's current EXP amount
    public static int getPlayerExp(Player player){
        int exp = 0;
        int level = player.getLevel();

        // Get the amount of XP in past levels
        exp += getExpAtLevel(level);

        // Get amount of XP towards next level
        exp += Math.round(getExpToLevelUp(level) * player.getExp());

        return exp;
    }

    // Give or take EXP
    public static int changePlayerExp(Player player, int exp){
        // Get player's current exp
        int currentExp = getPlayerExp(player);

        // Reset player's current exp to 0
        player.setExp(0);
        player.setLevel(0);

        // Give the player their exp back, with the difference
        int newExp = currentExp + exp;
        player.giveExp(newExp);

        // Return the player's new exp amount
        return newExp;
    }
}
