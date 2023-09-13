package me.zephi.waterguns.register.command;

import lombok.Getter;
import me.zephi.waterguns.util.TimeFormat;
import org.bukkit.*;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CommandReader {
    @Getter
    private final CommandSender sender;
    @Getter
    private final String label;
    private final String[] args;
    private int cursor = 0;

    public CommandReader(CommandSender sender, String label, String[] args) {
        this.sender = sender;
        this.label = label;
        this.args = args;
    }

    public boolean isConsoleSender() {
        return sender instanceof ConsoleCommandSender;
    }

    public Player getPlayer() {
        if (sender instanceof Player)
            return (Player) sender;

        return null;
    }

    public int size() {
        return args.length;
    }

    public Boolean readBoolean() {
        if (cannotRead())
            return null;

        return Boolean.parseBoolean(args[cursor++]);
    }

    public Integer readInt() {
        if (cannotRead())
            return null;

        try {
            return Integer.parseInt(args[cursor++]);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    public Long readLong() {
        if (cannotRead())
            return null;

        try {
            return Long.parseLong(args[cursor++]);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    public Short readShort() {
        if (cannotRead())
            return null;

        try {
            return Short.parseShort(args[cursor++]);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    public Character readChar() {
        if (cannotRead())
            return null;

        try {
            return args[cursor++].charAt(0);
        } catch (IndexOutOfBoundsException e) {
            return null;
        }
    }

    public Double readDouble() {
        if (cannotRead())
            return null;

        try {
            return Double.parseDouble(args[cursor++]);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    public Float readFloat() {
        if (cannotRead())
            return null;

        try {
            return Float.parseFloat(args[cursor++]);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    public UUID readUUID() {
        try {
            return UUID.fromString(readString());
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    public String readString() {
        if (cannotRead())
            return null;

        return args[cursor++];
    }

    public Byte readByte() {
        if (cannotRead())
            return null;

        try {
            return Byte.parseByte(args[cursor++]);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    public boolean cannotRead() {
        return cursor >= args.length;
    }

    public boolean cannotRead(int x) {
        return cursor + x - 1 >= args.length;
    }

    // External

    public String readLowercaseString() {
        if (cannotRead())
            return null;

        return readString().toLowerCase();
    }

    public String[] readStringArray() {
        if (cannotRead())
            return null;

        List<String> list = new ArrayList<>();
        String read;

        while ((read = readString()) != null)
            list.add(read);

        return list.toArray(new String[]{});
    }

    public String readJoinedStringArray() {
        if (cannotRead())
            return null;

        return String.join(" ", readStringArray());
    }

    public Player readPlayerStringExact() {
        if (cannotRead())
            return null;

        return Bukkit.getPlayerExact(readString());
    }

    public Player readPlayerString() {
        if (cannotRead())
            return null;

        return Bukkit.getPlayer(readString());
    }

    public OfflinePlayer readOfflinePlayerString() {
        if (cannotRead())
            return null;

        return Bukkit.getOfflinePlayer(readString());
    }

    public Player readPlayerID() {
        if (cannotRead())
            return null;

        return Bukkit.getPlayer(readUUID());
    }

    public OfflinePlayer readOfflinePlayerID() {
        if (cannotRead())
            return null;

        return Bukkit.getOfflinePlayer(readUUID());
    }

    public Material readMaterial() {
        if (cannotRead())
            return null;

        return Material.getMaterial(readString());
    }

    public Location readLocation() {
        Player p = getPlayer();

        if (cannotRead(3) || p == null)
            return null;

        return new Location(p.getWorld(), readDouble(), readDouble(), readDouble());
    }

    public Location readExactLocation() {
        if (cannotRead(4))
            return null;

        World w = Bukkit.getWorld(readString());

        return w == null ? null : new Location(w, readDouble(), readDouble(), readDouble());
    }

    public Long readTime() {
        if (cannotRead())
            return null;

        long time = TimeFormat.formatTimeToMillis(readString());

        if (time == -1)
            return null;

        return time;
    }
}
